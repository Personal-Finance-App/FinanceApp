package financeapp.bankConnection.tinkoff.api.calls;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class TinkoffApiFactory {
    static final String BASE_URL = "https://api.tinkoff.ru/v1/";

    // Стандартные параметры, которые иммитируют обращение с телефона
    static Map<String, String> defaultParams = new HashMap<>() {{
        put("mobile_device_model", "MAR-LX1M");
        put("mobile_device_os", "android");
        put("appVersion", "5.8.1");
        put("screen_width", "1080");
        put("root_flag", "false");
        put("appName", "mobile");
        put("origin", "mobile,ib5,loyalty,platform");
        put("connectionType", "WiFi");
        put("platform", "android");
        put("screen_dpi", "480");
        put("mobile_device_os_version", "10");
        put("screen_height", "2107");
        put("appsflyer_uid", "1610221902223-9226330507306162632");
        put("fingerprint", "HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1###1080x2312x32###180###false###false###");
        put("deviceId", String.valueOf(UUID.randomUUID()));
    }};


    static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        new OkHttpClient.Builder().addInterceptor(chain -> {
                                    var urlBuilder = chain
                                            .request()
                                            .url()
                                            .newBuilder();

                                    for (Map.Entry<String, String> entry : defaultParams.entrySet()) {
                                        urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                                    }

                                    var url = urlBuilder.build();
                                    return chain.proceed(chain.request().newBuilder().url(url).build());
                                })
                                .build()
                )
                .build();
    }

    public static TinkoffApi getService() {
        return buildRetrofit().create(TinkoffApi.class);
    }

}
