using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(ViaMarket.Startup))]
namespace ViaMarket
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
