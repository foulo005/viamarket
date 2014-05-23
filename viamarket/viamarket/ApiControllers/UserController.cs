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
        public ApplicationUser Get(string username, string password)
        {
            var user = UserManager.Find(username, password);
            return user;
        }

        // registers a user and returns status 201 (created) if ok, otherwise 404 (not found)
        public HttpResponseMessage Post(string username, string password, string firstname, string lastname)
        {
            var user = new ApplicationUser()
            {
                UserName = username,
                FirstName = firstname,
                LastName = lastname
            };
            var result = UserManager.Create(user, password);
            HttpResponseMessage response;
            if (result.Succeeded)
            {
                response = Request.CreateResponse<ApplicationUser>(HttpStatusCode.Created, user);
            }
            else
            {
                response = Request.CreateResponse<ApplicationUser>(HttpStatusCode.NotFound, user);
            }
            string uri = Url.Link("DefaultApi", new { id = user.Id });
            response.Headers.Location = new Uri(uri);
            return response;
        }

    }
}