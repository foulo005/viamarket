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
    [Authorize]
    public class HomeController : Controller
    {
        private ViaMarket.ApiControllers.ItemController ws = new ViaMarket.ApiControllers.ItemController();
        public ActionResult Index(int? id)
        {
            if (id == null)
                id = 1;
            int numberByPage = 20;
            int items = ws.GetTotalCount();
            HomeViewModel model = new HomeViewModel();
            model.Items = ws.GetLatest(numberByPage, (int)id-1).ToList<ItemDto>();
            model.Pages = new List<Page>();
            model.MaxPages = items % numberByPage == 0 ? items / numberByPage : items / numberByPage + 1;
            int interval = 5;
            int half = interval / 2 + 1;
            int startIndex = 1;
            int endIndex = model.MaxPages;
            if(model.MaxPages >= interval)
            {
                startIndex = id <= half ? 1 : id >= model.MaxPages - half ? model.MaxPages - (interval - 1) : (int)id - (interval / 2);
                endIndex = startIndex + interval-1;
            }
            for (int i=startIndex; i <= endIndex; i++)
            {
                model.Pages.Add(new Page(i, i==(int)id));
            }
            return View(model);
        }

        [AllowAnonymous]
        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";
            return View();
        }

        [AllowAnonymous]
        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";
            return View();
        }
    }
}