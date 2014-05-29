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
    [RoutePrefix("api/item")]
    public class ItemController : ApiController
    {
        ApplicationDbContext db = new ApplicationDbContext();

        // returns a list with all items
        [HttpGet]
        [Route("")]
        public IEnumerable<Item> GetAll()
        {
            return db.Items.AsEnumerable();
        }

        // gets an item by id
        [HttpGet]
        [Route("{id:int}")]
        public Item GetById(int id)
        {
            Item item = db.Items.Find(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return item;
        }

        // deletes the item with id (or returns a 404 response when invalid)
        [HttpDelete]
        [Route("{id:int}")]
        public void DeleteItem(int id)
        {
            Item item = db.Items.Find(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            db.Entry(item).State = EntityState.Deleted;
            db.SaveChanges();
        }

        // creates a new item and returns it (with http status code created 201)
        [HttpPost]
        [Route("")]
        public HttpResponseMessage UpdateItem(Item item)
        {
            if (item.Id > 0)
            {
                if (db.Items.Any(i => i.Id == item.Id))
                {
                    item.Created = DateTime.Now;
                    db.Items.Attach(item);
                    db.Entry(item).State = EntityState.Modified;
                    db.SaveChanges();
                    return Request.CreateResponse<Item>(HttpStatusCode.OK, item);
                }
                else
                {
                    throw new HttpResponseException(HttpStatusCode.NotFound);
                }
            }
            else
            {
                item.Created = DateTime.Now;
                db.Entry(item).State = EntityState.Added;
                db.SaveChanges();

                return Request.CreateResponse<Item>(HttpStatusCode.Created, item);
            }
        }



        [HttpGet]
        [Route("{userId}/{ongoing:bool}")]
        public IEnumerable<Item> GetItemsForUser(string userId, bool ongoing)
        {
            var items = from i in db.Items
                        where i.IdAspNetUsers == userId
                        && i.Ongoing == ongoing
                        select i;
            return items;
        }

        [HttpGet]
        [Route("category/{id:int}")]
        public IEnumerable<Item> GetItemsForCategory(int id)
        {
            var items = from i in db.Items
                        where i.IdCategory == id
                        select i;
            return items;
        }
    }
}