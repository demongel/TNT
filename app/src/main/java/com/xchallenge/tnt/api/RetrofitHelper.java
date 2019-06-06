package com.xchallenge.tnt.api;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.xchallenge.tnt.App;
import com.xchallenge.tnt.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    //    private static OkHttpClient mOkHttpClient;
//
//    static {
//        initOkHttpClient();
//    }
    private RetrofitHelper() {
    }


    @NonNull
    public static OkHttpClient getInstance() {
        return HttpClientHolder.init();
    }

    private static class HttpClientHolder {
        @NonNull
        private static OkHttpClient init() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new CacheInterceptor())
//                            .addNetworkInterceptor(new StethoInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new UserAgentInterceptor());
            return enableTls12OnPreLollipop(client).build();
        }

        //  去掉了json头部，用于上传文件
        private static OkHttpClient init2() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new CacheInterceptor())
//                            .addNetworkInterceptor(new StethoInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
//                    .addInterceptor(new UserAgentInterceptor())
            return enableTls12OnPreLollipop(client).build();
        }
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    private static <T> T createUploadApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(HttpClientHolder.init2())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 带有Auth认证的http请求
     *
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    private static <T> T createLocalBtcApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(
                        HttpClientHolder.init().newBuilder().authenticator(new Authenticator() {
                            @Nullable
                            @Override
                            public Request authenticate(Route route, Response response) throws IOException {
                                // FIXME  need Fixed
                                String credential = Credentials.basic("7VJWMjaaXG0YjdgCesk3jAVDg88=", "r0i9x2c0B2PqVculTWlEQlQUBik=");
                                return response.request().newBuilder()
                                        .header("Authorization", credential)
                                        .build();
                            }
                        }).build()
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 添加UA拦截器，  似乎不加也没什么问题？？？
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            request = requestBuilder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
//                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36")
//                    .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyToString(request.body())))//关键部分，设置requestBody的编码格式为json
                    .build();

//            request = requestBuilder
//                    .addHeader("Content-Type", "application/json;charset=UTF-8")
//                    .build();
            return chain.proceed(request);
        }
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

    /**
     * 为okhttp添加缓存，
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetWorkUtils.isNetworkConnected(App.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetWorkUtils.isNetworkConnected(App.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    // 处理4.4 以下的ssl问题
    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("TLS");
                    try {
                        sslContext.init(null, null, null);
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
                client.sslSocketFactory(socketFactory, new HttpsUtils.UnSafeTrustManager());
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }

}
