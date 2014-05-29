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
            Mapper.CreateMap<AccountDto, ApplicationUser>();
            Mapper.CreateMap<ApplicationUser, AccountDto>();
        }


        // finds a user with the username and password
        [HttpGet]
        [Route("")]
        public AccountDto CheckUser([FromUri] string username, [FromUri] string password)
        {
            var user = UserManager.Find(username, password);
            if (user != null)
            {
                Mapper.CreateMap<ApplicationUser, AccountDto>();
                return Mapper.Map<AccountDto>(user);
            }
            return null;
        }

        // registers a user and returns status 201 (created) if ok, otherwise 404 (not found)
        [HttpPost]
        [Route("")]
        public HttpResponseMessage UpdateUser(AccountDto account)
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
                    Mapper.CreateMap<ApplicationUser, AccountDto>();
                    sendVerificationEmail(user);

                    return Request.CreateResponse<AccountDto>(HttpStatusCode.Created, Mapper.Map<AccountDto>(user));

                }
                else
                {
                    Mapper.CreateMap<ApplicationUser, AccountDto>();
                    AccountDto accountFailed = Mapper.Map<AccountDto>(user);
                    accountFailed.ErrorList = result.Errors.ToArray().ToList<string>();
                    return Request.CreateResponse<AccountDto>(HttpStatusCode.NotFound, accountFailed);
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
                    return Request.CreateResponse<AccountDto>(HttpStatusCode.OK, Mapper.Map<AccountDto>(user));
                }
                else
                {
                    throw new HttpResponseException(HttpStatusCode.NotFound);
                }
            }
        }

        private void sendVerificationEmail(ApplicationUser user)
        {

            string smtpAddress = "smtp.mail.yahoo.com";
            int portNumber = 587;
            bool enableSSL = true;

            string emailFrom = "viamarket001@yahoo.com";
            string password = "";
            string emailTo = user.UserName + "@via.dk";
            string subject = "Welcome to viamarket";
            string body = "Hello, <br />You registered recently for the viamarket. <br />To confirm your email please visit the following link:<a href=\"\"></a>";

            using (MailMessage mail = new MailMessage())
            {
                mail.From = new MailAddress(emailFrom);
                mail.To.Add(emailTo);
                mail.Subject = subject;
                mail.Body = body;
                mail.IsBodyHtml = true;
                // Can set to false, if you are sending pure text.

                //mail.Attachments.Add(new Attachment("C:\\SomeFile.txt"));
                //mail.Attachments.Add(new Attachment("C:\\SomeZip.zip"));

                using (SmtpClient smtp = new SmtpClient(smtpAddress, portNumber))
                {
                    smtp.Credentials = new NetworkCredential(emailFrom, password);
                    smtp.EnableSsl = enableSSL;
                    smtp.Send(mail);
                }
            }


        }
    }

    public class AccountDto
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Id { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public List<string> ErrorList { get; set; }
    }
}