using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.ApiControllers.Dto
{
    public class UserDto
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Id { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public ICollection<ContactDto> Contacts { get; set; }
        public List<string> ErrorList { get; set; }
    }
}