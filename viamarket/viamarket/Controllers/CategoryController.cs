using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using viamarket.DataAccess;

namespace viamarket.Controllers
{
    public class CategoryController : ApiController
    {
        MarketEntities db = new MarketEntities();

        public IEnumerable<Category> GetAll()
        {
            return db.Category.AsEnumerable();
        }


        public Category GetById(int id)
        {
            return db.Category.Find(id);
        }


        public bool Delete(int id)
        {
            Category category = db.Category.Find(id);
            if (category != null)
            {
                db.Entry(category).State = EntityState.Deleted;
                db.SaveChanges();
                return true;
            }
            return false;
        }


        public Category Edit(Category category)
        {
            if (db.Category.Find(category.Id) != null)
            {
                db.Entry(category).State = EntityState.Modified;
                db.SaveChanges();
            }
            return category;
        }


        public Category Create(Category category)
        {
            db.Entry(category).State = EntityState.Added;
            db.SaveChanges();
            return category;
        }

    }
}