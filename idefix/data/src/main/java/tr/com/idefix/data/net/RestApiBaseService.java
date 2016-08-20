package tr.com.idefix.data.net;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashSet;
import mu.comon.cache.MemoryCache;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import tr.com.dr.data.BuildConfig;
import tr.com.idefix.data.type_adapter_factory.PasswordResponseAdapterFactory;

/**
 * Created on 9.13.15.
 */
public abstract class RestApiBaseService<T> {

    public static final String API_BASE_URL = "http://www.idefix.com/";
    public static String API_BASE_IMAGE_URL; // = "http://img1.dr.com.tr";
    public static String LOGIN_COOKIES = "LOGIN_COOKIES";
    static MemoryCache memoryCache;
    static HashSet<String> cookies;
    final T restApi;
    RestAdapter restAdapter;

    public RestApiBaseService(String endPoint, Class<T> service) {

        RequestInterceptor requestInterceptor = request -> {

//            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("platform", "androidphone");
            request.addHeader("AppVersionCode", String.valueOf(BuildConfig.VERSION_CODE));
            request.addHeader("AppVersionName", BuildConfig.VERSION_NAME);
//            Log.i("<->", "add header");
//
//            if (cookies != null) {
//                for (String cookie : cookies) {
//                    request.addHeader("platform", "androidphone");
//                }
//            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new PasswordResponseAdapterFactory())
                .create();

        OkHttpClient client = new OkHttpClient();

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

        client.interceptors().add(new AddCookiesInterceptor());
        client.interceptors().add(new ReceivedCookiesInterceptor());

        restAdapter = new RestAdapter.Builder()
                .setClient(new InterceptingOkClient(client))
                .setEndpoint(endPoint)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        Log.i(this.getClass().getSimpleName(), endPoint);

        restApi = restAdapter.create(service);
    }

    public static MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public static void setMemoryCache(MemoryCache memoryCache) {
        RestApiBaseService.memoryCache = memoryCache;
    }

    public class InterceptingOkClient extends OkClient {

        public InterceptingOkClient(OkHttpClient client) {
            super(client);
        }

        @Override
        public Response execute(Request request) throws IOException {

            Response response = super.execute(request);


//            if (response.getUrl().endsWith("Login") && response.getStatus() == 200) {
//
//                if (memoryCache != null) {
//
//                    if (cookies != null) {
//                        Log.i("<->", "cookies clear");
//                        cookies.clear();
//                    }
//                    if (memoryCache != null) {
//                        Log.i("<->", "remove cache");
//                        memoryCache.removeCache(LOGIN_COOKIES);
//                    }
//                }
//            }
/*
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {

                reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            String result = sb.toString();
*/
            //String s = new String(((TypedByteArray) response.getBody()).getBytes());

            return response;
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

            com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());

            if (chain.request().httpUrl().toString().endsWith("Login")) {
                if (memoryCache != null) {
                    memoryCache.removeCache(LOGIN_COOKIES);
                }
                cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            }

            return originalResponse;
        }
    }

    public class AddCookiesInterceptor implements Interceptor {

        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
            com.squareup.okhttp.Request.Builder builder = chain.request().newBuilder();

            if (memoryCache != null) {
                Log.i("<->", "get cookies from cache");
                cookies = memoryCache.getCacheByType(
                        LOGIN_COOKIES,
                        new TypeToken<HashSet<String>>() {
                        }.getType());
            }

            if (cookies != null) {

                StringBuilder stringBuilder = new StringBuilder();

                Object[] hArr = cookies.toArray();

                for (int i = 0; i < hArr.length; i++) {

                    stringBuilder.append(hArr[i].toString());

                    if (i < hArr.length - 1) {
                        stringBuilder.append(";");
                    }
                }

                String cook = stringBuilder.toString();
                Log.d("cook", cook);
                builder.addHeader("Cookie", cook);


//                for (String cookie : cookies) {
//                    Log.i("<->", "add header");
//
//                    builder
//                            .addHeader(
//                                    "Cookie",
//                                    cookie
////                                    cookies.get(key)
//                            );
//                    Log.v("OkHttp", "Adding Header: " + cookie);
////                    Log.v("OkHttp", "key: " + key);
////                    Log.v("OkHttp", "value: " + cookies.get(key));
//                    // This is done so I know which headers
//                    // are being added; this interceptor is used
//                    // after the normal logging of OkHttp
//                }
            }

            return chain.proceed(builder.build());
        }
    }

}
