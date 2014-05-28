using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.Models
{
    public class Files
    {
        public IEnumerable<HttpPostedFileBase> List { get; set; }
    }
}