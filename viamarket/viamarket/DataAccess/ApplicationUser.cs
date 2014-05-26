using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.DataAccess
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
    }
<<<<<<< HEAD:viamarket/viamarket/DataAccess/ApplicationUser.cs
=======

    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection")
        {
        }

        public System.Data.Entity.DbSet<ViaMarket.DataAccess.Category> Categories { get; set; }
    }
>>>>>>> 1472dfb7ba33736029f64118d2a9ebb57429d71d:viamarket/viamarket/Models/IdentityModels.cs
}