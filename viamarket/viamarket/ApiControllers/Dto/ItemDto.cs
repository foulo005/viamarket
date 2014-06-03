﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ViaMarket.ApiControllers.Dto
{
    public class ItemDto
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }

        public double Price { get; set; }
        public DateTime Created { get; set; }
        public bool Ongoing { get; set; }

        public UserDto ApplicationUser { get; set; }

        public CategoryDto Category { get; set; }

        public CurrencyDto Currency { get; set; }
        public ICollection<ImageDto> Image { get; set; }
    }
}