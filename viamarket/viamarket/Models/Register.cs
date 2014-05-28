using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.Models
{
    public class Register : ViewModelBase
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
    }
}