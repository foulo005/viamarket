using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ViaMarket;
using ViaMarket.Controllers;
using ViaMarket.DataAccess;
using System.Collections.Generic;

namespace ViaMarket.Tests
{
    [TestClass]
    public class CategoryControllerTest
    {

        [TestMethod]
        public void InsertCategory()
        {
            var controller = new CategoryController();
            Category c = new Category();
            c.name = "TestCategory";
            controller.PostCategory(c);
        }

        [TestMethod]
        public void GetAllCategories_ShouldReturnAllCategories()
        {
            var controller = new CategoryController();

            var result = controller.GetAll() as List<Category>;
            Assert.IsNotNull(result);
            Assert.IsTrue(result.Count > 0);
        }

        [TestMethod]
        public void GetProduct_ShouldReturnCorrectProduct()
        {
           /* var testProducts = GetTestProducts();
            var controller = new SimpleProductController(testProducts);

            var result = controller.GetProduct(4) as OkNegotiatedContentResult<Product>;
            Assert.IsNotNull(result);
            Assert.AreEqual(testProducts[3].Name, result.Content.Name);*/
        }

        [TestMethod]
        public void GetProduct_ShouldNotFindProduct()
        {
         /*   var controller = new SimpleProductController(GetTestProducts());

            var result = controller.GetProduct(999);
            Assert.IsInstanceOfType(result, typeof(NotFoundResult));*/
        }

       

    }
}
