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

namespace ViaMarket.Controllers
{
    public class ItemController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: /Item/
        public ActionResult Index()
        {

            return View(db.Items.ToList());
        }

        // GET: /Item/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Item item = db.Items.Find(id);
            if (item == null)
            {
                return HttpNotFound();
            }
            return View(item);
        }

        // GET: /Item/Create
        public ActionResult Create()
        {
            //CategoryList
            List<SelectListItem> ctgs = new List<SelectListItem>();
            foreach (Category c in db.Categories.ToList<Category>())
            {
                ctgs.Add(new SelectListItem { Text = c.Name, Value = c.Id.ToString() });
            }
            ViewBag.CategoriesType = ctgs;

            List<SelectListItem> curr = new List<SelectListItem>();
            foreach (Currency c in db.Currencies.ToList<Currency>())
            {
                curr.Add(new SelectListItem { Text = c.Name+" ("+c.Code+")", Value = c.Id.ToString() });
            }
            ViewBag.CurrenciesType = curr;
            return View();
        }

        // POST: /Item/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include="Id,Title,Description,Price,IdCurrency,IdCategory")] Item item, Files files)
        {
            if (ModelState.IsValid)
            {
                item.IdAspNetUsers = User.Identity.GetUserId();
                item.Created = DateTime.Now;
                db.Items.Add(item);
                db.SaveChanges();
                Directory.CreateDirectory(Server.MapPath("~/ItemsPictures/"+item.Id.ToString()));
                /*  foreach(File f in files.List){
                 *     file.SaveAs(Path.Combine(Server.MapPath("~/ItemsPictures/"+item.Id.ToString()), Path.GetFileName(f.FileName)));
                 *  }
                 *  We need to pass as a Parameter the pictures
                 *  Checkout this : @using (Html.BeginForm(null, null, FormMethod.Post, new { enctype = "multipart/form-data" }))
                 *  We use the Id of the Item as the name of the folder where the pictures we'll be stored
                 */
                return RedirectToAction("Index");
            }

            return View(item);
        }

        // GET: /Item/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Item item = db.Items.Find(id);
            if (item == null)
            {
                return HttpNotFound();
            }
            return View(item);
        }

        // POST: /Item/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include="Id,Title,Description,IdAspNetUsers,Price,IdCurrency,IdCategory")] Item item)
        {
            if (ModelState.IsValid)
            {
                db.Entry(item).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(item);
        }

        // GET: /Item/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Item item = db.Items.Find(id);
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
            Item item = db.Items.Find(id);
            db.Items.Remove(item);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}