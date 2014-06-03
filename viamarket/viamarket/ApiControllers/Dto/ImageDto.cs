using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.ApiControllers.Dto
{
    public class ImageDto
    {
        public int Id { get; set; }
        public string PathOriginal { get; set; }
        public string PathPreview { get; set; }
    }
}