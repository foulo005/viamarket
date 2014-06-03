using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;

namespace ViaMarket.App_Start
{
    public class ApplicationConfig
    {
        // global config
        public const string HostBaseUrl = "http://viamarket-001-site1.myasp.net/";
        public const string Domain = "via.dk";
        public const string Name = "ViaMarket";

        // activation
        public const string ActivationSubject = Name + " Activation";
        public const string ActivationMessage = @"Hello {0}, <br />
                            You registered for " + Name + @". To confirm your email please visit the following link:<br />
                            <a href=""" + ActivationUrl + @""">" + ActivationUrl + @"</a><br /><br />Your " + Name + @" Team";
        public const string ActivationFromAddress = "viamarket001@yahoo.com";
        public const string ActivationUrl = HostBaseUrl + "Account/Activate?code={0}";
        public const string ActivationToAddress = "{0}@" + Domain;
        public const string ActivationSmtp = "smtp.mail.yahoo.com";
        public const int ActivationSmtpPort = 587;
        public const bool ActivationSmtpEnableSsl = true;
        public const string ActivationSmtpPassword = "";

        // image upload
        public const string ImgTempDir = "~/App_Data\\uploads";
        public const string ImgDir = "~/ItemsPictures/";
        public const int ImgPreviewMaxHeight = 100; //in pixels
        public const int ImgPreviewMaxWidth = 100;
    }
}