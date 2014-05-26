
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    
    public class Item
    {
        public Item()
        {
            this.Image = new HashSet<Image>();
        }

        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string IdAspNetUsers { get; set; }
        public Nullable<double> Price { get; set; }
        public Nullable<int> IdCurrency { get; set; }
        public int IdCategory { get; set; }
    
        public virtual ApplicationUser ApplicationUser { get; set; }
        public virtual Category Category { get; set; }
        public virtual Currency Currency { get; set; }
        public virtual ICollection<Image> Image { get; set; }
    }
}
