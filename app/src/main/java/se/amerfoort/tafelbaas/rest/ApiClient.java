package se.amerfoort.tafelbaas.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.amerfoort.tafelbaas.Utils.TafelBossPreference;

import static se.amerfoort.tafelbaas.Utils.TafelBossPreference.USER_ID;
import static se.amerfoort.tafelbaas.rest.ApiClient.MyOkHttpInterceptor.HEADER_CACHE_CONTROL;
import static se.amerfoort.tafelbaas.rest.ApiClient.MyOkHttpInterceptor.HEADER_CONTENT_TYPE;
import static se.amerfoort.tafelbaas.rest.ApiClient.MyOkHttpInterceptor.HEADER_OS;
import static se.amerfoort.tafelbaas.rest.ApiClient.MyOkHttpInterceptor.HEADER_TOKEN;


public class ApiClient {

    private static final String BASE_URL = "https://ppp.amersfoort.se/API/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
            //builder.addInterceptor(new MyOkHttpInterceptor());
            builder.connectTimeout(1, TimeUnit.MINUTES);
            builder.readTimeout(1, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);
            OkHttpClient okHttpClient = builder.build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Map<String, String> getHeaders() {
        TafelBossPreference tafelBossPreference = TafelBossPreference.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("ppp-os", HEADER_OS);
        map.put("Content-Type", HEADER_CONTENT_TYPE);
        map.put("Cache-Control", HEADER_CACHE_CONTROL);
        map.put("Postman-Token", HEADER_TOKEN);
        map.put("ppp-user", tafelBossPreference.getStringData(USER_ID));
        map.put("ppp-business", tafelBossPreference.getStringData(TafelBossPreference.QR_CODE));
        map.put("ppp-uuid", TafelBossPreference.getUDID());
        return map;
    }

    public static Map<String, String> getGroupHeaders() {
        TafelBossPreference tafelBossPreference = TafelBossPreference.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("ppp-os", HEADER_OS);
        // map.put("Content-Type", "application/x-www-form-unlencoded");
//        map.put("Cache-Control", HEADER_CACHE_CONTROL);
//        map.put("Postman-Token", HEADER_TOKEN);
        map.put("ppp-user", tafelBossPreference.getStringData(USER_ID));
        map.put("ppp-business", tafelBossPreference.getStringData(TafelBossPreference.QR_CODE));
        map.put("ppp-uuid", TafelBossPreference.getUDID());
        return map;
    }

    public static class MyOkHttpInterceptor implements Interceptor {
        public static final String HEADER_TOKEN = "7e704d14-391d-4c64-5762-95b6c12fe24b";
        public static final String HEADER_CACHE_CONTROL = "no-cache";
        public static final String HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";
        public static final String HEADER_OS = "android";

        @Override
        public Response intercept(Chain chain) throws IOException {
            TafelBossPreference tafelBossPreference = TafelBossPreference.getInstance();
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                    .header("ppp-os", HEADER_OS)
                    .header("Content-Type", HEADER_CONTENT_TYPE)
                    .header("Cache-Control", HEADER_CACHE_CONTROL)
                    .header("Postman-Token", HEADER_TOKEN)
                    .header("ppp-user", tafelBossPreference.getStringData(USER_ID))
                    .header("ppp-business", tafelBossPreference.getStringData(TafelBossPreference.QR_CODE))
                    .header("ppp-uuid", TafelBossPreference.getUDID())
                    .build();
            return chain.proceed(newRequest);
        }
    }
}

