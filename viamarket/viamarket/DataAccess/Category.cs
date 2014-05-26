
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    
    public class Category
    {
        public Category()
        {
            this.Item = new HashSet<Item>();
        }

        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }
        public string name { get; set; }
    
        public virtual ICollection<Item> Item { get; set; }
    }
}
