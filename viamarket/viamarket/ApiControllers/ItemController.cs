using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using ViaMarket.ApiControllers.Dto;
using ViaMarket.App_Start;
using ViaMarket.DataAccess;
using AutoMapper;

namespace ViaMarket.ApiControllers
{
    [RoutePrefix("api/item")]
    public class ItemController : ApiController
    {
        private ApplicationDbContext db;

        public ItemController()
        {
            db = new ApplicationDbContext();
            Mapper.CreateMap<Item, ItemDto>();
            Mapper.CreateMap<ItemDto, Item>();
            Mapper.CreateMap<ViaMarket.DataAccess.Image, ImageDto>();
            Mapper.CreateMap<Category, CategoryDto>();
            Mapper.CreateMap<CategoryDto, Category>();
            Mapper.CreateMap<Currency, CurrencyDto>();
            Mapper.CreateMap<CurrencyDto, Currency>();
            Mapper.CreateMap<ApplicationUser, UserDto>();
            Mapper.CreateMap<UserDto, ApplicationUser>();
            Mapper.CreateMap<ViaMarket.DataAccess.Image, ImageDto>();
            Mapper.CreateMap<ImageDto, ViaMarket.DataAccess.Image>();
        }

        // returns a list with all items
        [HttpGet]
        [Route("")]
        public IEnumerable<ItemDto> GetAll()
        {   
            return Mapper.Map<IEnumerable<Item>, IEnumerable<ItemDto>>(db.Items.AsEnumerable());
        }

        // gets an item by id
        [HttpGet]
        [Route("{id:int}")]
        public ItemDto GetById(int id)
        {
            Item item = db.Items.Find(id);
            if (item == null)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            return Mapper.Map<ItemDto>(item);
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
        public ItemDto UpdateItem(ItemUpdateDto itemDto)
        {
            Item item = new Item
            {
                IdAspNetUsers = itemDto.IdAspNetUsers,
                IdCategory = itemDto.IdCategory,
                IdCurrency = itemDto.IdCurrency,
                Created = DateTime.Now,
                Description = itemDto.Description,
                Id = itemDto.Id,
                Price = itemDto.Price,
                Title = itemDto.Title
            };

            if (item.Id > 0)
            {
                if (db.Items.Any(i => i.Id == item.Id))
                {
                    db.Items.Attach(item);
                    db.Entry(item).State = EntityState.Modified;
                    db.SaveChanges();
                    item = db.Items.Find(item.Id);
                    return  Mapper.Map<ItemDto>(item);
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
                item = db.Items.Find(item.Id);
                return Mapper.Map<ItemDto>(item);
            }
        }



        [HttpGet]
        [Route("{userId}/{ongoing:bool}")]
        public IEnumerable<ItemDto> GetItemsForUser(string userId, bool ongoing)
        {
            var items = from i in db.Items
                        where i.IdAspNetUsers == userId
                        && i.Ongoing == ongoing
                        select i;
            return Mapper.Map<IEnumerable<Item>, IEnumerable<ItemDto>>(items);
        }

        [HttpGet]
        [Route("category/{id:int}")]
        public IEnumerable<ItemDto> GetItemsForCategory(int id)
        {
            var items = from i in db.Items
                        where i.IdCategory == id
                        select i;
            return Mapper.Map<IEnumerable<Item>, List<ItemDto>>(items);
        }

        [HttpGet]
        [Route("category/{id:int}/{search}")]
        public IEnumerable<ItemDto> SearchItemsInCategory(int id, string search)
        {
            if (db.Categories.Any(c => c.Id == id))
            {
                var resultsTitle = from c in db.Items
                              where c.IdCategory == id
                              && c.Title.Contains(search)
                                       select c;
                var resultsDescr = from c in db.Items
                                   where c.IdCategory == id
                                   && c.Description.Contains(search)
                                   select c;
                return Mapper.Map<IEnumerable<Item>, IEnumerable<ItemDto>>(resultsTitle.Union(resultsDescr));
            }
            else
            {
                return new List<ItemDto>();
            }
        }
        

        [HttpPost]
        [Route("{idItem:int}/image/upload")]
        public async Task<HttpResponseMessage> PostFormData(int idItem)
        {
            if (db.Items.Any(i => i.Id == idItem) == false)
            {
                return Request.CreateResponse(HttpStatusCode.NotAcceptable, "The provided idItem is not valid.");
            }

            // Check if the request contains multipart/form-data.
            if (!Request.Content.IsMimeMultipartContent())
            {
                throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
            }

            string tmpFolderUpload = HttpContext.Current.Server.MapPath(ApplicationConfig.ImgTempDir);
            var provider = new MultipartFormDataStreamProvider(tmpFolderUpload);
            string folderItem = HttpContext.Current.Server.MapPath(ApplicationConfig.ImgDir + idItem);
            string folderItemPreview = Path.Combine(folderItem, "preview");
            try
            {
                // Read the form data.
                await Request.Content.ReadAsMultipartAsync(provider);

                // This illustrates how to get the file names.
                MultipartFileData file = provider.FileData.First();

                if (string.IsNullOrEmpty(file.Headers.ContentDisposition.FileName))
                {
                    return Request.CreateResponse(HttpStatusCode.NotAcceptable, "This request is not properly formatted.");
                }
                string fileName = file.Headers.ContentDisposition.FileName;
                if (fileName.StartsWith("\"") && fileName.EndsWith("\""))
                {
                    fileName = fileName.Trim('"');
                }
                if (fileName.Contains(@"/") || fileName.Contains(@"\"))
                {
                    fileName = Path.GetFileName(fileName);
                }


                string newFileName = Guid.NewGuid() + fileName.Substring(fileName.LastIndexOf(".") - 1);
                string newPathOrig = Path.Combine(folderItem, newFileName);
                string newPathPrev = Path.Combine(folderItemPreview, newFileName);

                if (System.IO.Directory.Exists(folderItem) == false)
                {
                    System.IO.Directory.CreateDirectory(folderItem);
                }

                if (System.IO.Directory.Exists(folderItemPreview) == false)
                {
                    System.IO.Directory.CreateDirectory(folderItemPreview);
                }
                File.Move(file.LocalFileName, newPathOrig);
                File.Delete(Path.Combine(folderItem, fileName));

                var origPic = System.Drawing.Image.FromFile(newPathOrig);
                var previewPic = ScaleImage(origPic, ApplicationConfig.ImgPreviewMaxWidth, ApplicationConfig.ImgPreviewMaxHeight);
                previewPic.Save(newPathPrev);

                ViaMarket.DataAccess.Image image = new ViaMarket.DataAccess.Image();
                image.IdItem = idItem;

                image.PathOriginal = Path.Combine(ApplicationConfig.ImgDir, idItem.ToString(), newFileName);
                image.PathOriginal = new Uri(Request.RequestUri, Url.Content(image.PathOriginal)).ToString();
                image.PathPreview = Path.Combine(ApplicationConfig.ImgDir, idItem.ToString(), "preview", newFileName);
                image.PathPreview = new Uri(Request.RequestUri, Url.Content(image.PathPreview)).ToString();

                db.Images.Add(image);
                await db.SaveChangesAsync();

                return Request.CreateResponse<ImageDto>(HttpStatusCode.OK, Mapper.Map<ImageDto>(image));
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }

        [HttpGet]
        [Route("image/delete/{idImage:int}")]
        public HttpResponseMessage DeleteImage(int idImage)
        {
            if (db.Images.Any(i => i.Id == idImage) == false)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound, "The provided image id is not valid.");
            }
            ViaMarket.DataAccess.Image item = db.Images.Find(idImage);
            db.Entry(item).State = EntityState.Deleted;
            db.SaveChanges();
            return Request.CreateResponse(HttpStatusCode.OK);
        }


        private static System.Drawing.Image ScaleImage(System.Drawing.Image image, int maxWidth, int maxHeight)
        {
            var ratioX = (double)maxWidth / image.Width;
            var ratioY = (double)maxHeight / image.Height;
            var ratio = Math.Min(ratioX, ratioY);

            var newWidth = (int)(image.Width * ratio);
            var newHeight = (int)(image.Height * ratio);

            var newImage = new Bitmap(newWidth, newHeight);
            Graphics.FromImage(newImage).DrawImage(image, 0, 0, newWidth, newHeight);
            return newImage;
        }

        [HttpGet]
        [Route("latest/{amount:int}/{startPos:int?}")]
        public IEnumerable<ItemDto> GetLatest(int amount, int startPos = 0)
        {
            var items = from i in db.Items
                        orderby i.Created descending
                        select i;
            return Mapper.Map<IEnumerable<Item>, IEnumerable<ItemDto>>(items.Skip(startPos).Take(amount));
        }
    }
}