using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ViaMarket.DataAccess;

namespace ViaMarket.Models
{
    public class ViewModelBase
    {
        public IEnumerable<Category> CategoryList { get; set; }

        public ViewModelBase()
        {
            ApplicationDbContext db = new ApplicationDbContext();
            this.CategoryList = db.Categories.AsEnumerable();
        }
    }
}