
using System;
namespace ProductsApp.Models
{
    public class Item
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string description { get; set; }
        public string Category { get; set; }
        public decimal Price { get; set; }
        public int IdCategory { get; set; }
        public int IdCurrency { get; set; }
        public DateTime date { get; set; }
    }
}