﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace ViaMarket
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            // configure the routing for the web mvc urls
            
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "DetailsCategory",
                url: "{controller}/{action}/{id}/{idPage}",
                defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional ,idPage = UrlParameter.Optional }
            );
        }
    }
}
