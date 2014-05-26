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

    }
}