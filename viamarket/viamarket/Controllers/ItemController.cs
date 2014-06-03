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
using System.IO;
using ViaMarket.Models;
using ViaMarket.ApiControllers.Dto;
using System.Net.Http;

namespace ViaMarket.Controllers
{
    public class ItemController : Controller
    {
        private ViaMarket.ApiControllers.ItemController ws = new ViaMarket.ApiControllers.ItemController();
        private ViaMarket.ApiControllers.CategoryController wsCategory = new ViaMarket.ApiControllers.CategoryController();
        private ViaMarket.ApiControllers.CurrencyController wsCurrency = new ViaMarket.ApiControllers.CurrencyController();

        // GET: /Item/
        public ActionResult Index()
        {
            return View(ws.GetAll());
        }

        // GET: /Item/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ItemDto item = ws.GetById((int)id);
            if (item == null)
            {
                return HttpNotFound();
            }
            ItemViewModel model = new ItemViewModel();
            model.Id = (int)id;
            model.Title = item.Title;
            model.Description = item.Description;
            model.IdCategory = (int)item.Category.Id;
            model.IdCurrency = (int)item.Currency.Id;
            model.Price = (double)item.Price;
            model.Currency = item.Currency.Name;
            model.Category = item.Category.Name;
            return View(model);
        }

        // GET: /Item/Create
        public ActionResult Create()
        {
            ItemViewModel model = new ItemViewModel();
            //CategoryList
            model.ListCategories = wsCategory.CategoryList().ToList<CategoryDto>();
            model.ListCurrencies = wsCurrency.GetAllCurrencies().ToList<CurrencyDto>();

            return View(model);
        }

        // POST: /Item/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(ItemViewModel model, IEnumerable<HttpPostedFileBase> files)
        {
            if (ModelState.IsValid)
            {
                ItemUpdateDto item = new ItemUpdateDto();
                item.Title = model.Title;
                item.Description = model.Description;
                item.IdCategory = model.IdCategory;
                item.IdCurrency = model.IdCurrency;
                item.Price = model.Price;
                item.IdAspNetUsers = User.Identity.GetUserId();
                item.Ongoing = true;
                item.Created = DateTime.Now;
                try
                {
                    HttpResponseMessage response = ws.UpdateItem(item);
                }catch{}
                return RedirectToAction("Index");
                /*ViaMarket.ApiControllers.ItemController wsItem = new ViaMarket.ApiControllers.ItemController();
                HttpRequest request = (HttpRequest)WebRequest.Create();
                request.Method = "POST";
                request.KeepAlive = true;
                request.ContentLength = data.Length;
                request.ContentType = "application/x-www-form-urlencoded";*/
            }
            return View(model);
        }

        // GET: /Item/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ItemDto item = ws.GetById((int)id);
            if (item == null)
            {
                return HttpNotFound();
            }
            ItemViewModel model = new ItemViewModel();
            model.Id = (int)id;
            model.Title = item.Title;
            model.Description = item.Description;
            model.IdCategory = (int)item.Category.Id;
            model.IdCurrency = (int)item.Currency.Id;
            model.Price = (double)item.Price;
            model.ListCategories = wsCategory.CategoryList().ToList<CategoryDto>();
            model.ListCurrencies = wsCurrency.GetAllCurrencies().ToList<CurrencyDto>();
            return View(model);
        }

        // POST: /Item/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(ItemViewModel model)
        {
            if (ModelState.IsValid)
            {
                ItemUpdateDto item = new ItemUpdateDto();
                item.Id = model.Id;
                item.Title = model.Title;
                item.Description = model.Description;
                item.IdCategory = model.IdCategory;
                item.IdCurrency = model.IdCurrency;
                item.Price = model.Price;
                item.IdAspNetUsers = User.Identity.GetUserId();
                item.Ongoing = true;
                item.Created = DateTime.Now;
                try
                {
                    HttpResponseMessage response = ws.UpdateItem(item);
                }
                catch { }
                return RedirectToAction("Index");
            }
            return View(model);
        }

        // GET: /Item/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ItemDto item = ws.GetById((int) id);
            if (item == null)
            {
                return HttpNotFound();
            }
            return View(item);
        }

        // POST: /Item/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            ws.DeleteItem(id);
            return RedirectToAction("Index");
        }
    }
}