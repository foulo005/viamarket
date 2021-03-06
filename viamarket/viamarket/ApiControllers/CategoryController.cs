﻿using AutoMapper;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.ApiControllers.Dto;
using ViaMarket.DataAccess;

namespace ViaMarket.ApiControllers
{
    /**
     * This is the web service related to categories
     */
    [RoutePrefix("api/category")]
    public class CategoryController : ApiController
    {
        private ApplicationDbContext db;

        public CategoryController()
        {
            // initialize db access and mappings for the dto's
            db = new ApplicationDbContext();
            Mapper.CreateMap<Category, CategoryDto>();
            Mapper.CreateMap<CategoryDto, Category>();
        }

        // returns a list with all categories available
        [Route("")]
        [HttpGet]
        public IEnumerable<CategoryDto> CategoryList()
        {
            //in this case we need to add the category "Others" to the end of the list
            //sort the list by name
            var categories = from c in db.Categories
                             where c.Name != "Others"
                             orderby c.Name ascending
                             select c;
<<<<<<< HEAD
            var others = 
                             from c in db.Categories
                             where c.Name == "Others"
                                 select c;
=======
            var others = from c in db.Categories
                         where c.Name == "Others"
                         select c;
>>>>>>> cad916811f11b53f66debc7ed0c72de5318ba709

            ICollection<Category> collection = categories.ToList();
            collection.Add(others.FirstOrDefault());

            return Mapper.Map<ICollection<Category>, ICollection<CategoryDto>>(collection);
        }

        //
        [HttpGet]
        [Route("{id:int}/count")]
        public int GetCountByCategory(int id)
        {
            int count = (from i in db.Items
                         where i.IdCategory == id
                         select i).Count();
            return count;
        }

        [HttpGet]
        [Route("{category:int}/latest/{amount:int}/{startPos:int?}")]
        public IEnumerable<ItemDto> GetLatest(int category, int amount, int startPos = 0)
        {
            var items = from i in db.Items
                        where i.IdCategory == category
                        orderby i.Created descending
                        select i;
            return Mapper.Map<IEnumerable<Item>, IEnumerable<ItemDto>>(items.Skip(startPos).Take(amount));
        }

        // Returns a category by id, throws exception when not found
        [Route("{id:int}")]
        [HttpGet]
        public CategoryDto CategoryById(int id)
        {
            Category category = db.Categories.Find(id);
            if (category == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return Mapper.Map<CategoryDto>(category);
        }

        // deletes the category with id (or returns a 404 response when invalid)
        [HttpDelete]
        [Route("{id:int}")]
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
        [HttpPost]
        [Route("")]
        public HttpResponseMessage UpdateCategory(CategoryDto category)
        {
            Category entity = Mapper.Map<Category>(category);

            //try to update
            if (category.Id > 0)
            {
                if (db.Categories.Any(c => c.Id == category.Id))
                {
                    db.Categories.Attach(entity);
                    db.Entry(entity).State = EntityState.Modified;
                    db.SaveChanges();
                    return Request.CreateResponse<CategoryDto>(HttpStatusCode.OK, Mapper.Map<CategoryDto>(entity));
                }
                else
                {
                    throw new HttpResponseException(HttpStatusCode.NotFound);
                }
            }
            //create new entry
            else
            {
                db.Entry(entity).State = EntityState.Added;
                db.SaveChanges();
                return Request.CreateResponse<CategoryDto>(HttpStatusCode.Created, Mapper.Map<CategoryDto>(entity));
            }
        }
    }
}