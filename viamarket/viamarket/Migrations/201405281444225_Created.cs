namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Created : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Items", "Created", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Items", "Created");
        }
    }
}
