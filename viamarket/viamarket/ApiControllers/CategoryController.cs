using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.DataAccess;

namespace ViaMarket.Controllers
{
    public class CategoryController : ApiController
    {
        ApplicationDbContext db = new ApplicationDbContext();

        // returns a list with all categories available
        public IEnumerable<Category> GetAll()
        {
            return db.Categories.AsEnumerable();
        }

        // Returns a category by id, throws exception when not found
        public Category GetById(int id)
        {
            Category category = db.Categories.Find(id);
            if (category == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return category;
        }

        // deletes the category with id (or returns a 404 response when invalid)
        public void DeleteCategory(int id)
        {
            Category category = db.Categories.Find(id);
            if (category == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            db.Entry(category).State = EntityState.Deleted;
            db.SaveChanges();
        }

        // creates a new category and returns it (with http status code created 201)
        public HttpResponseMessage PostCategory(Category category)
        {
            db.Entry(category).State = EntityState.Added;
            db.SaveChanges();

            var response = Request.CreateResponse<Category>(HttpStatusCode.Created, category);

            string uri = Url.Link("DefaultApi", new { id = category.Id });
            response.Headers.Location = new Uri(uri);
            return response;
        }

        // updates a category. if not found, return http response 404
        public void PutCategory(Category category)
        {
            if (db.Categories.Find(category.Id) == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            db.Entry(category).State = EntityState.Modified;
            db.SaveChanges();
        }
    }
}