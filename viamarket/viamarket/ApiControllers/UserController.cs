using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using ViaMarket.DataAccess;
using System.Net.Mail;
using System.Web.Http;
using ViaMarket.App_Start;
using ViaMarket.ApiControllers.Dto;
using System.Data.Entity;

namespace ViaMarket.ApiControllers
{
    [RoutePrefix("api/user")]
    public class UserController : ApiController
    {
        public UserManager<ApplicationUser> UserManager { get; private set; }
        private ApplicationDbContext db;

        public UserController()
        {
            db = new ApplicationDbContext();
            UserManager = new UserManager<ApplicationUser>(new UserStore<ApplicationUser>(new ApplicationDbContext()));
            Mapper.CreateMap<UserDto, ApplicationUser>();
            Mapper.CreateMap<ApplicationUser, UserDto>();
            Mapper.CreateMap<Contact, ContactDto>();
            Mapper.CreateMap<ContactDto, Contact>();

        }


        // finds a user with the username and password
        [HttpGet]
        [Route("")]
        public UserDto CheckUser([FromUri] string username, [FromUri] string password)
        {
            var user = UserManager.Find(username, password);
            UserDto account;
            if (user == null)
            {
                account = new UserDto();
                account.ErrorList = new List<string>();
                account.ErrorList.Add("Your credentials provided are not valid!");
            }
            else if (user.Active == false)
            {
                account = new UserDto();
                account.ErrorList = new List<string>();
                account.ErrorList.Add("Your account is not validated yet. Please activate your account with the activation link that was sent to you.");
            }
            else
            {
                account = Mapper.Map<UserDto>(user);
            }
            return account;
        }

        [HttpPost]
        [Route("contacts")]
        public void UpdateContactList(string userId, List<ContactDto> contacts)
        {
            ApplicationUser user = UserManager.FindById(userId);
            if (user != null)
            {
                var toDelete = from c in db.Contacts
                               where c.IdAspNetUsers == userId
                               && !contacts.Any(cont => c.Id.Equals(cont.Id))
                               select c;

                var toUpdate = from c in db.Contacts
                               where c.IdAspNetUsers == userId
                               && contacts.Any(cont => c.Id.Equals(cont.Id))
                               select c;

                var toCreate = from c in contacts
                               where c.Id <= 0
                               select c;


                foreach (var contact in toDelete)
                {
                    db.Contacts.Remove(contact);
                }

                foreach (var contact in toUpdate)
                {
                    ContactDto dto = (from c in contacts
                                     where c.Id == contact.Id
                                     select c).SingleOrDefault();
                    contact.Value = dto.Value;
                    contact.ContactType = dto.ContactType;

                    db.Contacts.Attach(contact);
                    db.Entry(contact).State = EntityState.Modified;
                }

                foreach (var contact in toCreate)
                {
                    Contact entity = new Contact();

                    entity.Value = contact.Value;
                    entity.ContactType = contact.ContactType;

                    db.Contacts.Attach(contact);
                    db.Entry(contact).State = EntityState.Modified;
                }
                
                    db.SaveChanges();   
            }
        }

        // registers a user and returns status 201 (created) if ok, otherwise 404 (not found)
        [HttpPost]
        [Route("")]
        public UserDto UpdateUser(UserDto account)
        {
            //create user
            if (account.Id == null || account.Id.Length == 0)
            {
                var user = new ApplicationUser()
                {
                    UserName = account.UserName,
                    FirstName = account.FirstName,
                    LastName = account.LastName
                };
                var result = UserManager.Create(user, account.Password);
                if (result.Succeeded)
                {
                    sendVerificationEmail(user);

                    return Mapper.Map<UserDto>(user);

                }
                else
                {
                    UserDto accountFailed = Mapper.Map<UserDto>(user);
                    accountFailed.ErrorList = result.Errors.ToArray().ToList<string>();
                    return accountFailed;
                }
            }
            //update user
            else
            {
                ApplicationUser user = UserManager.FindById(account.Id);
                if (user != null)
                {
                    user.FirstName = account.FirstName;
                    user.LastName = account.LastName;
                    UserManager.Update(user);
                    return Mapper.Map<UserDto>(user);
                }
                else
                {
                    throw new HttpResponseException(HttpStatusCode.NotFound);
                }
            }
        }

        private void sendVerificationEmail(ApplicationUser user)
        {
            using (MailMessage mail = new MailMessage())
            {
                mail.From = new MailAddress(ApplicationConfig.ActivationFromAddress);
                mail.To.Add(string.Format(ApplicationConfig.ActivationToAddress, user.UserName));
                mail.Subject = ApplicationConfig.ActivationSubject;
                mail.Body = string.Format(ApplicationConfig.ActivationMessage, user.FirstName);
                mail.IsBodyHtml = true;

                using (SmtpClient smtp = new SmtpClient(ApplicationConfig.ActivationSmtp, ApplicationConfig.ActivationSmtpPort))
                {
                    smtp.Credentials = new NetworkCredential(ApplicationConfig.ActivationFromAddress, ApplicationConfig.ActivationSmtpPassword);
                    smtp.EnableSsl = ApplicationConfig.ActivationSmtpEnableSsl;
                    smtp.Send(mail);
                }
            }
        }

        [HttpGet]
        [Route("contact/types")]
        public ICollection<String> GetContactTypes()
        {
            return Enum.GetValues(typeof(ContactType))
                .Cast<ContactType>()
                .Select(v => v.ToString())
                .ToList();
        }
    }
}