using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;
using ViaMarket.Models;
using ViaMarket.ApiControllers;
using ViaMarket.ApiControllers.Dto;

namespace ViaMarket.Controllers
{
    public class HomeController : Controller
    {
        private ViaMarket.ApiControllers.ItemController ws = new ViaMarket.ApiControllers.ItemController();
        public ActionResult Index(int? id)
        {
            HomeViewModel model = new HomeViewModel();
            if(id == null)
                id = 0;
            model.Items = ws.GetLatest(20, (int) id).ToList<ItemDto>();
            return View(model);
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