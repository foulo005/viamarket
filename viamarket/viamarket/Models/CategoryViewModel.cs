using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ViaMarket.DataAccess;
using ViaMarket.ApiControllers.Dto;

namespace ViaMarket.Models
{
    public class CategoryViewModel : ViewModelBase
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public List<ItemDto> Items { get; set; }
        public List<Page> Pages { get; set; }
        public int MaxPages { get; set; }
    }
}