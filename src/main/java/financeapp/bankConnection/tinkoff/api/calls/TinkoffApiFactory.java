package financeapp.bankConnection.tinkoff.api.calls;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


public class TinkoffApiFactory {
    static final String BASE_URL = "https://api.tinkoff.ru/v1/";

    // Стандартные параметры, которые иммитируют обращение с телефона
    static FormBody defaultFormBody = new FormBody.Builder()
            .add("mobile_device_model", "MAR-LX1M")
            .add("mobile_device_os", "android")
            .add("appVersion", "5.8.1")
            .add("screen_width", "1080")
            .add("root_flag", "false")
            .add("appName", "mobile")
            .add("origin", "mobile,ib5,loyalty,platform")
            .add("connectionType", "WiFi")
            .add("platform", "android")
            .add("screen_dpi", "480")
            .add("mobile_device_os_version", "10")
            .add("screen_height", "2107")
            .add("appsflyer_uid", "1610221902223-9226330507306162632")
            .add("fingerprint", "HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1###1080x2312x32###180###false###false###")
            .build();

    static Retrofit buildRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        new OkHttpClient.Builder()
                                .addInterceptor(TinkoffApiFactory::getChain)
                                .addInterceptor(interceptor)
                                .build()
                )
                .build();
    }

    public static TinkoffApi getService() {
        return buildRetrofit().create(TinkoffApi.class);
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
