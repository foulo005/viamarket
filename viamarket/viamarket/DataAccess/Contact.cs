
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    
    public class Contact
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }
        public string IdAspNetUsers { get; set; }
        public string ContactType { get; set; }
        public string Value { get; set; }
    
        public virtual ApplicationUser ApplicationUser { get; set; }
    }
}
