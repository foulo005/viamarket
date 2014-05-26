using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.Models
{
    public class Account
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Id { get; set; }
        public string UserName { get; set; }
        public List<string> ErrorList { get; set; }
    }
}