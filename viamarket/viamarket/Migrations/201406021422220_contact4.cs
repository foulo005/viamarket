namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class contact4 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Contacts", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropIndex("dbo.Contacts", new[] { "ApplicationUser_Id" });
            AddColumn("dbo.Contacts", "IdAspNetUsers", c => c.String(maxLength: 128));
            CreateIndex("dbo.Contacts", "IdAspNetUsers");
            AddForeignKey("dbo.Contacts", "IdAspNetUsers", "dbo.AspNetUsers", "Id");
            DropColumn("dbo.Contacts", "ApplicationUser_Id");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Contacts", "ApplicationUser_Id", c => c.String(nullable: false, maxLength: 128));
            DropForeignKey("dbo.Contacts", "IdAspNetUsers", "dbo.AspNetUsers");
            DropIndex("dbo.Contacts", new[] { "IdAspNetUsers" });
            DropColumn("dbo.Contacts", "IdAspNetUsers");
            CreateIndex("dbo.Contacts", "ApplicationUser_Id");
            AddForeignKey("dbo.Contacts", "ApplicationUser_Id", "dbo.AspNetUsers", "Id", cascadeDelete: true);
        }
    }
}
