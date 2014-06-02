using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ViaMarket.DataAccess;

namespace ViaMarket.Models
{
    public class CategoryViewModel : ViewModelBase
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public List<Item> Items { get; set; }
    }
}