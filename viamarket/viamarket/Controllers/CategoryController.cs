using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using ViaMarket.DataAccess;
using System.Linq.Expressions;
using Microsoft.AspNet.Identity;
using ViaMarket.Models;
using ViaMarket.ApiControllers;
using ViaMarket.ApiControllers.Dto;
using System.Net.Http;

namespace ViaMarket.Controllers
{
    [Authorize]
    public class CategoryController : Controller
    {
        private ViaMarket.ApiControllers.CategoryController ws = new ViaMarket.ApiControllers.CategoryController();
        // GET: /Category/
        public ActionResult Index()
        {
            return View(ws.CategoryList());
        }

        // GET: /Category/Details/5
        public ActionResult Details(int? id, int? idPage)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            if (idPage == null)
                idPage = 1;
            CategoryDto category = ws.CategoryById((int)id);
            if (category == null)
            {
                return HttpNotFound();
            }
            CategoryViewModel model = new CategoryViewModel();
            model.Id = category.Id;
            model.Name = category.Name;
            int numberByPage = 20;
            
            int items = ws.GetCountByCategory((int)(id));
            model.MaxPages = items % numberByPage == 0 ? items / numberByPage : items / numberByPage + 1;
            model.Items = ws.GetLatest((int)id, numberByPage, (int)idPage - 1).ToList<ItemDto>();
            model.Pages = new List<Page>();

            int interval = 5;
            int half = interval / 2 + 1;            
            int startIndex = 1;
            int endIndex = model.MaxPages;          
            if(model.MaxPages >= interval)
            {
                startIndex = idPage <= half ? 1 :
                    idPage >= model.MaxPages - half ? model.MaxPages - (interval - 1) : (int)idPage - (interval / 2);
                endIndex = startIndex + interval-1;
            }
            for (int i=startIndex; i <= endIndex; i++)
            {
                model.Pages.Add(new Page(i, i==(int)idPage));
            }

            return View(model);
        }

        // GET: /Category/Create
        public ActionResult Create()
        {
            CategoryViewModel model = new CategoryViewModel();
            return View(model);
        }

        // POST: /Category/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(CategoryViewModel model)
        {
            if (ModelState.IsValid)
            {
                CategoryDto category = new CategoryDto();
                category.Name = model.Name;
                try
                {
                    HttpResponseMessage response = ws.UpdateCategory(category);
                    if (response.StatusCode == HttpStatusCode.Created)
                    {
                        return RedirectToAction("Index");
                    }
                }
                catch { }
                return RedirectToAction("Index");
            }

            return View(model);
        }

        // GET: /Category/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CategoryDto category = ws.CategoryById((int)id);
            if (category == null)
            {
                return HttpNotFound();
            }
            CategoryViewModel model = new CategoryViewModel();
            model.Id = category.Id;
            model.Name = category.Name;
            return View(model);
        }

        // POST: /Category/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(CategoryViewModel model)
        {
            if (ModelState.IsValid)
            {
                CategoryDto category = new CategoryDto();
                category.Id = model.Id;
                category.Name = model.Name;
                try
                {
                    HttpResponseMessage response = ws.UpdateCategory(category);
                }
                catch { }
                return RedirectToAction("Index");
            }
            return View(model);
        }

        // GET: /Category/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CategoryDto category = ws.CategoryById((int)id);
            CategoryViewModel model = new CategoryViewModel();
            model.Id = category.Id;
            model.Name = category.Name;
            if (category == null)
            {
                return HttpNotFound();
            }
            return View(model);
        }

        // POST: /Category/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            ws.DeleteCategory(id);
            return RedirectToAction("Index");
        }
    }
}