using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ViaMarket.DataAccess
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        public ApplicationUser()
        {
            Active = false;
        }

        public string FirstName { get; set; }
        public string LastName { get; set; }
        [Required] 
        public bool Active { get; set; }

        public virtual ICollection<Contact> Contacts { get; set; }
    }
}