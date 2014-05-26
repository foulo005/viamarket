
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    
    public class Contact
    {
        public int Id { get; set; }
        public string IdAspNetUsers { get; set; }
        public string ContactType { get; set; }
        public string Value { get; set; }
    
        public virtual ApplicationUser ApplicationUser { get; set; }
    }
}
