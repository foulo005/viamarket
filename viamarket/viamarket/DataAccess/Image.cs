
namespace ViaMarket.DataAccess
{
    using System;
    using System.Collections.Generic;
    
    public class Image
    {
        public int Id { get; set; }
        public Nullable<int> IdItem { get; set; }
        public string PathOriginal { get; set; }
        public string PathPreview { get; set; }
    
        public virtual Item Item { get; set; }
    }
}
