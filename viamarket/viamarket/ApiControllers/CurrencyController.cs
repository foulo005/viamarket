using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ViaMarket.ApiControllers.Dto;
using ViaMarket.DataAccess;
using AutoMapper;

namespace ViaMarket.ApiControllers
{
    [RoutePrefix("api/currency")]
    public class CurrencyController : ApiController
    {

        private ApplicationDbContext db;

        public CurrencyController()
        {
            db = new ApplicationDbContext();
            Mapper.CreateMap<Currency, CurrencyDto>();
        }

        [HttpGet]
        [Route("list")]
        public IEnumerable<CurrencyDto> GetAllCurrencies()
        {
            var currencies = from c in db.Currencies
                        orderby c.Code ascending
                        select c;
            return Mapper.Map<IEnumerable<Currency>, IEnumerable<CurrencyDto>>(currencies);
        }
    }
}
