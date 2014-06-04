namespace viamarket.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class random : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.UserDtoes",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        FirstName = c.String(),
                        LastName = c.String(),
                        UserName = c.String(),
                        Password = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.ContactDtoes",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ContactType = c.Int(nullable: false),
                        Value = c.String(),
                        UserDto_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.UserDtoes", t => t.UserDto_Id)
                .Index(t => t.UserDto_Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.ContactDtoes", "UserDto_Id", "dbo.UserDtoes");
            DropIndex("dbo.ContactDtoes", new[] { "UserDto_Id" });
            DropTable("dbo.ContactDtoes");
            DropTable("dbo.UserDtoes");
        }
    }
}
