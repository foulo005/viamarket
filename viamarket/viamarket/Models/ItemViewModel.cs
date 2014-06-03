using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;
using ViaMarket.ApiControllers.Dto;

namespace ViaMarket.Models
{
    public class ItemViewModel : ViewModelBase
    {
        public int Id { get; set; }
        public List<CategoryDto> ListCategories;
        public List<CurrencyDto> ListCurrencies;
        public string Title { get; set; }
        public string Description { get; set; }
        public double Price { get; set; }
        public int IdCategory { get; set; }
        public int IdCurrency { get; set; }
        public string Category { get; set; }
        public string Currency { get; set; }
        public IEnumerable<SelectListItem> Categories
        {
            get { return new SelectList(ListCategories, "Id", "Name"); }
        }
        public IEnumerable<SelectListItem> Currencies
        {
            get { return new SelectList(ListCurrencies, "Id", "Name"); }
        }

    }
}