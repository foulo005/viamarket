using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace viamarket.DataAccess
{
    public class MarketContext : DbContext
    {

        public MarketContext()
            : base("Entities")
        {

        }
    }
}