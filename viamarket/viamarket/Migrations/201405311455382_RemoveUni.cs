namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemoveUni : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AspNetUsers", "University_Id", "dbo.Universities");
            DropIndex("dbo.AspNetUsers", new[] { "University_Id" });
            DropColumn("dbo.AspNetUsers", "University_Id");
            DropTable("dbo.Universities");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.Universities",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Domain = c.String(),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            AddColumn("dbo.AspNetUsers", "University_Id", c => c.Int());
            CreateIndex("dbo.AspNetUsers", "University_Id");
            AddForeignKey("dbo.AspNetUsers", "University_Id", "dbo.Universities", "Id");
        }
    }
}
