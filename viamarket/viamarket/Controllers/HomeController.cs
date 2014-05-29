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
        //ApplicationDbContext db = new ApplicationDbContext();
        HomeViewModel model = new HomeViewModel();
        public ActionResult Index()
        {
            //var model = db.Items.ToList();
            return View();
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