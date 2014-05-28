using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;

namespace ViaMarket.Controllers
{
    public class SharedController : Controller
    {
        ApplicationDbContext db = new ApplicationDbContext();
        //
        // GET: /Shared/
        public ActionResult Categories()
        {
            var model = db.Categories;
            return PartialView("~/Views/Shared/_CategoriesPartial.cshtml", model);
        }
	}
}