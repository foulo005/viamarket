using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.DataAccess;

namespace ViaMarket.Controllers
{
    public class CategoryController : ApiController
    {
        MarketEntities data = new MarketEntities();
        public IEnumerable<Category> Get()
        {
            return data.Category;
        }
        public IEnumerable<Item> Get(int id)
        {
            return data.Item;
        }
        public List<string> getListCategory(){
            List<string> list = new List<string>();
            foreach(Category c in this.Get()){
                list.Add(c.name);
            }
            return list;
        }
    }
}
