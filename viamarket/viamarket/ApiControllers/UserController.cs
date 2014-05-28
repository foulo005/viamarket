using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.Models;
using ViaMarket.DataAccess;
using System.Net.Mail;

namespace ViaMarket.ApiControllers
{
    public class UserController : ApiController
    {
        public UserManager<ApplicationUser> UserManager { get; private set; }

        public UserController()
        {
            UserManager = new UserManager<ApplicationUser>(new UserStore<ApplicationUser>(new ApplicationDbContext()));
        }


        // finds a user with the username and password
        public Account Get([FromUri] string username, [FromUri] string password)
        {
            var user = UserManager.Find(username, password);
            if (user != null)
            {
                Mapper.CreateMap<ApplicationUser, Account>();
                return Mapper.Map<Account>(user);
            }
            return null;
        }

        // registers a user and returns status 201 (created) if ok, otherwise 404 (not found)
        public HttpResponseMessage PostUser([FromBody]Register register)
        {
            var user = new ApplicationUser()
            {
                UserName = register.UserName,
                FirstName = register.FirstName,
                LastName = register.LastName
            };
            var result = UserManager.Create(user, register.Password);
            HttpResponseMessage response;
            if (result.Succeeded)
            {
                Mapper.CreateMap<ApplicationUser, Account>();

                response = Request.CreateResponse<Account>(HttpStatusCode.Created, Mapper.Map<Account>(user));

                sendVerificationEmail(user);
            }
            else
            {
                Mapper.CreateMap<ApplicationUser, Account>();
                Account accountFailed = Mapper.Map<Account>(user);
                accountFailed.ErrorList = result.Errors.ToArray().ToList<string>();
                response = Request.CreateResponse<Account>(HttpStatusCode.NotFound, accountFailed);
            }
            string uri = Url.Link("DefaultApi", new { id = user.Id });
            response.Headers.Location = new Uri(uri);
            return response;
        }


        private void sendVerificationEmail(ApplicationUser user)
        {
            MailMessage MyMailMessage = new MailMessage();

            MyMailMessage.From = new MailAddress("viamarket001@gmail.com");

            MyMailMessage.To.Add("215535@via.dk");

            MyMailMessage.Subject = "Welcome to viamarket!";

            MyMailMessage.Body = "Activation link: yourSite/controller/action/" + user.Id;

            MyMailMessage.IsBodyHtml = true;

            SmtpClient SMTPServer = new SmtpClient("smtp.gmail.com");

            SMTPServer.Port = 587;

            SMTPServer.Credentials = new System.Net.NetworkCredential("viamarket001", "csWPMeWfJZj4");

            SMTPServer.EnableSsl = true;

            try
            {

                SMTPServer.Send(MyMailMessage);

                //Response.Redirect("Thankyou.aspx");

            }

            catch (Exception ex)
            {



            }


        }

    }
}