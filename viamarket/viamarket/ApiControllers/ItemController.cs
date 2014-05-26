using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.DataAccess;

namespace ViaMarket.ApiControllers
{
    public class ItemController : ApiController
    {
        MarketEntities db = new MarketEntities();

        // returns a list with all items
        public IEnumerable<Item> GetAll()
        {
            return db.Item.AsEnumerable();
        }

        // gets an item by id
        public Item GetById(int id)
        {
            Item item = db.Item.Find(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return item;
        }

        // deletes the item with id (or returns a 404 response when invalid)
        public void DeleteItem(int id)
        {
            Item item = db.Item.Find(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            db.Entry(item).State = EntityState.Deleted;
            db.SaveChanges();
        }

        // creates a new item and returns it (with http status code created 201)
        public HttpResponseMessage PostItem(Item item)
        {
            db.Entry(item).State = EntityState.Added;
            db.SaveChanges();

            var response = Request.CreateResponse<Item>(HttpStatusCode.Created, item);

            string uri = Url.Link("DefaultApi", new { id = item.idItem });
            response.Headers.Location = new Uri(uri);
            return response;
        }

        // updates a item. if not found, return http response 404
        public void PutItem(Item item)
        {
            if (db.Item.Find(item.idItem) == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            db.Entry(item).State = EntityState.Modified;
            db.SaveChanges();
        }
    }
}