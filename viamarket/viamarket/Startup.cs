using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(viamarket.Startup))]
namespace viamarket
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
