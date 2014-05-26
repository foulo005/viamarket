
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    
    public class University
    {
        public University()
        {
            this.ApplicationUsers = new HashSet<ApplicationUser>();
        }

        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }
        public string Domain { get; set; }
        public string Name { get; set; }
    
        public virtual ICollection<ApplicationUser> ApplicationUsers { get; set; }
    }
}
