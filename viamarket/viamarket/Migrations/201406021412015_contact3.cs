namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class contact3 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Contacts", "IdAspNetUsers", "dbo.AspNetUsers");
            DropIndex("dbo.Contacts", new[] { "IdAspNetUsers" });
            RenameColumn(table: "dbo.Contacts", name: "IdAspNetUsers", newName: "ApplicationUser_Id");
            AlterColumn("dbo.Contacts", "ApplicationUser_Id", c => c.String(nullable: false, maxLength: 128));
            CreateIndex("dbo.Contacts", "ApplicationUser_Id");
            AddForeignKey("dbo.Contacts", "ApplicationUser_Id", "dbo.AspNetUsers", "Id", cascadeDelete: true);
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Contacts", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropIndex("dbo.Contacts", new[] { "ApplicationUser_Id" });
            AlterColumn("dbo.Contacts", "ApplicationUser_Id", c => c.String(maxLength: 128));
            RenameColumn(table: "dbo.Contacts", name: "ApplicationUser_Id", newName: "IdAspNetUsers");
            CreateIndex("dbo.Contacts", "IdAspNetUsers");
            AddForeignKey("dbo.Contacts", "IdAspNetUsers", "dbo.AspNetUsers", "Id");
        }
    }
}
