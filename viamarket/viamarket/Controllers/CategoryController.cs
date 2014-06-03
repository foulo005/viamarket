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

namespace ViaMarket.Controllers
{
    public class CategoryController : Controller
    {
        private ViaMarket.ApiControllers.CategoryController ws = new ViaMarket.ApiControllers.CategoryController();
        // GET: /Category/
        public ActionResult Index()
        {
            return View(ws.CategoryList());
        }

        // GET: /Category/Details/5
        public ActionResult Details(int? id)
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
            ViaMarket.ApiControllers.ItemController wsItem = new ViaMarket.ApiControllers.ItemController();
            model.Items = wsItem.GetItemsForCategory((int)id).ToList<ItemDto>();
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
                ws.UpdateCategory(category);
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
                ws.UpdateCategory(category);
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