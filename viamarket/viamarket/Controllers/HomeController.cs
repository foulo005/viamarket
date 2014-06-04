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
            if (id == null)
                id = 0;
            int numberByPage = 20;
            int items = 100;//ws.GetTotalCount();
            HomeViewModel model = new HomeViewModel();
            model.Items = ws.GetLatest(numberByPage, (int)id).ToList<ItemDto>();
            model.Pages = new List<Page>();
            model.MaxPages = items % numberByPage == 0 ? items / numberByPage : items / numberByPage + 1;
            

            
            for (int i=1; i <= 5; i++)
            {
                model.Pages.Add(new Page(i, i==(int)id));
            }
            
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