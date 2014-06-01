<<<<<<< HEAD
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;

namespace ViaMarket.Models
{
    public class ItemViewModel : ViewModelBase
    {
        public int Id { get; set; }
        public List<Category> ListCategories;
        public List<Currency> ListCurrencies;
        public string Title { get; set; }
        public string Description { get; set; }
        public double Price { get; set; }
        public int IdCategory { get; set; }
        public int IdCurrency { get; set; }
        public string Category { get; set; }
        public string Currency { get; set; }
        public IEnumerable<SelectListItem> Categories {
            get { return new SelectList(ListCategories, "Id", "Name"); }
        }
        public IEnumerable<SelectListItem> Currencies
        {
            get { return new SelectList(ListCurrencies, "Id", "Name"); }
        }

    }
=======
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;

namespace ViaMarket.Models
{
    public class ItemViewModel : ViewModelBase
    {
        public List<Category> ListCategories;
        public List<Currency> ListCurrencies;
        public string Title { get; set; }
        public string Description { get; set; }
        public double Price { get; set; }
        public int IdCategory { get; set; }
        public int IdCurrency { get; set; }
        public IEnumerable<SelectListItem> Categories {
            get { return new SelectList(ListCategories, "Id", "Name"); }
        }
        public IEnumerable<SelectListItem> Currencies
        {
            get { return new SelectList(ListCurrencies, "Id", "Name"); }
        }

    }
>>>>>>> 0e689439e4605513c30131fd03add865d8735a56
}