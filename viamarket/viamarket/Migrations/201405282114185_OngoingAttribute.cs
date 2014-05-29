namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class OngoingAttribute : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Items", "Ongoing", c => c.Boolean(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Items", "Ongoing");
        }
    }
}
