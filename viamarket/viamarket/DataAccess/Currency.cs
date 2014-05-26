
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    
    public  class Currency
    {
        public Currency()
        {
            this.Item = new HashSet<Item>();
        }
    
        public int Id { get; set; }
        public string Name { get; set; }
        public string Code { get; set; }
    
        public virtual ICollection<Item> Item { get; set; }
    }
}
