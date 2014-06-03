using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ViaMarket.DataAccess;

namespace ViaMarket.Models
{
    public class HomeViewModel : ViewModelBase
    {
        public List<Item> Items { get; set; }
    }
}