using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;
using ViaMarket.Models;

namespace ViaMarket.Controllers
{
    public class HomeController : Controller
    {
        ApplicationDbContext db = new ApplicationDbContext();
        public ActionResult Index()
        {
<<<<<<< HEAD
            return View();
=======
            //var model = db.Items.ToList();
            HomeViewModel model = new HomeViewModel();
            model.CategoryList =  db.Categories.AsEnumerable();
            return View(model);
>>>>>>> 5ec2ba907e8b44e968e9598425f7124aca790c6b
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}