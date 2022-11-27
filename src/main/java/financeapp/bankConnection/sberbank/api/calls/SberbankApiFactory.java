package financeapp.bankConnection.sberbank.api.calls;

import financeapp.bankConnection.tinkoff.api.calls.TinkoffApi;
import financeapp.bankConnection.tinkoff.api.calls.TinkoffApiFactory;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import javax.xml.bind.JAXBContextFactory;
import java.io.IOException;

public class SberbankApiFactory {
    static final String BASE_URL = "https://online.sberbank.ru:4477/";

    // Стандартные параметры, которые иммитируют обращение с телефона
    static FormBody defaultFormBody = new FormBody.Builder()
        .add("version", "9.20")
        .add("appType", "android")
        .add("appVersion", "14.5.0")
        .add("appBuildType", "RELEASE")
        .add("deviceName", "HUAWEI_ANE-LX1")
        .add("deviceOSVersion", "10")
        .add("devID", "607d725604d1f032e50bb3c0622e791d3f400000")
        .add("devIDOld", "63c103d506178038cb0964403f372ae5af1e0000")
        .add("mobileSdkData", "{\"TIMESTAMP\":\"2021-09-11T12:41:57Z\",\"DeviceModel\":\"Redmi Note 8T\",\"DeviceName\":\"Redmi0Note08T\",\"DeviceSystemName\":\"Android\",\"DeviceSystemVersion\":\"29\",\"Languages\":\"en_US\",\"WiFiMacAddress\":\"a4:45:19:42:72:26\",\"WiFiNetworksData\":{\"BSSID\":\"02:00:00:00:00:00\",\"SignalStrength\":\"-51\",\"Channel\":\"3\"},\"ScreenSize\":\"1080x2130\",\"MCC\":\"250\",\"MNC\":\"1\",\"AppKey\":\"25112496-e869-4900-bd07-20cc887cb052\",\"SDK_VERSION\":\"1.5.1.257\",\"Compromised\":0,\"MultitaskingSupported\":true,\"AdvertiserId\":\"03aae2cc-3d95-41fc-b2fb-9bd172c0e3ec\",\"OS_ID\":\"8735f6daf1c71e0c\",\"Emulator\":0,\"GeoLocationInfo\":[{\"Longitude\":\"0\",\"Latitude\":\"0\",\"Altitude\":\"0\",\"HorizontalAccuracy\":\"0\",\"AltitudeAccuracy\":\"0\",\"Heading\":\"0\",\"Speed\":\"0\",\"Status\":\"1\",\"Timestamp\":\"0\"}],\"DeveloperTools\":1,\"GooglePlayProtect\":-1,\"HoursSinceZoomInstall\":12977,\"HoursSinceQSInstall\":-1,\"HoursSinceAnyDeskInstall\":-1,\"UnknownSources\":-1,\"AgentBrand\":\"Xiaomi\",\"AgentBootTime\":\"1719663\",\"TimeZone\":\"180\",\"SupportedAPILevel\":\"29\",\"OSCodeName\":\"Not Found\",\"AgentAppInfo\":\"СберБанк 12.6.0 arm64-v8a\",\"ApprepInstalledApps\":\"438\",\"OSFontsNumber\":\"257\",\"OSFontsHash\":1038989955,\"ScreenColorDepth\":\"~320 dpi\",\"TimeZoneDSTOffset\":\"0\",\"SimCard\":\"1\",\"AgentSignalStrengthCellular\":\"-1\",\"AgentConnectionType\":\"WIFI\",\"AgentSignalTypeCellular\":\"-1\",\"LocalIPv4\":\"192.168.1.4\",\"LocalIPv6\":\"fe80::5418:77ff:fea6:ad78\",\"DnsIP\":\"192.168.1.1\",\"ApplicationMD5\":\"7dfc767dd1c49320c287aced308afdfd\",\"RdpConnection\":\"0\",\"LocationHash\":\"c2f250a3f92e3be648f817fd934fe5c66e10ba6ae02258933c7c6d1fccdc7241\"}")
        .add("mobileSDKKAV", "{\"osVersion\":0,\"KavSdkId\":\"\",\"KavSdkVersion\":\"\",\"KavSdkVirusDBVersion\":\"\",\"KavSdkVirusDBStatus\":\"\",\"KavSdkVirusDBStatusDate\":\"\",\"KavSdkRoot\":false,\"LowPasswordQuality\":false,\"NonMarketAppsAllowed\":false,\"UsbDebugOn\":false,\"ScanStatus\":\"NONE\",\"bluetooth\":\"1\",\"vibracall\":\"1\",\"f1\":\"0\",\"f2\":\"1\",\"f3\":\"0\",\"f4\":\"0\",\"f5\":\"1\",\"f6\":\"0\",\"f7\":\"1\",\"f8\":\"-1\",\"f9\":\"-1\",\"f10\":\"-1\",\"build_display\":\"QKQ1.200114.002 test-keys\",\"build_fingerprint\":\"xiaomi\\/willow_eea\\/willow:10\\/QKQ1.200114.002\\/V12.0.3.0.QCXEUXM:user\\/release-keys\",\"build_hardware\":\"qcom\",\"build_host\":\"c4-miui-ota-bd29.bj\",\"build_manufacturer\":\"Xiaomi\",\"build_model\":\"Redmi Note 8T\",\"build_product\":\"willow_eea\",\"build_type\":\"user\",\"build_user\":\"builder\",\"build_getradioversion\":\"MPSS.AT.4.3.1-00270-NICOBAR_GEN_PACK-1.374795.2.380402.1\",\"n_cells_wcdma\":\"-1\",\"n_cells_gsm\":\"-1\",\"n_cells_lte\":\"-1\",\"n_contacts\":\"125\",\"nfc_exists\":\"1\",\"nfc_on\":\"1\",\"emulator_files\":\"0\",\"app_context\":\"0\"}")
            .build();

    static Retrofit buildRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JaxbConverterFactory.create())
                .client(
                        new OkHttpClient.Builder()
                                .addInterceptor(SberbankApiFactory::getChain)
                                .addInterceptor(interceptor)
                                .build()
                )
                .build();
    }

    public static SberbankApi getService() {
        return buildRetrofit().create(SberbankApi.class);
    }

    @NotNull
    public static okhttp3.Response getChain(Interceptor.Chain chain) throws IOException {
        var urlBuilder = chain.request()
                .url()
                .newBuilder();

        var requestBody = processFormDataRequestBody(chain.request().body());


        var url = urlBuilder.build();

        return chain.proceed(chain
                .request()
                .newBuilder()
                .url(url)
                .post(requestBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("user-agent", "HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1")
                .build());

    }


    private static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private static RequestBody processFormDataRequestBody(RequestBody requestBody) {
        String postBodyString = bodyToString(requestBody);
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(defaultFormBody);
        return RequestBody.create(requestBody.contentType(), postBodyString);
    }
}
