
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
        public ContactType ContactType { get; set; }
        public string Value { get; set; }

        [ForeignKey("IdAspNetUsers")]
        public virtual ApplicationUser ApplicationUser { get; set; }
       
        public string IdAspNetUsers { get; set; }
    }

    public enum ContactType
    {
        MOBILE,
        HOME,
        EMAIL,
        SKYPE,
        FACEBOOK
    }
}
