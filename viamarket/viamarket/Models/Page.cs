using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.Models
{
    public class Page
    {
        public Page(int i, bool c)
        {
            this.id = i;
            this.isCurrent = c;
        }
        public int id { get; set;}
        public bool isCurrent { get; set; }
    }
}