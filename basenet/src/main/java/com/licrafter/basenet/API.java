package com.licrafter.basenet;

import com.google.gson.GsonBuilder;
import com.licrafter.basenet.cookie.MemoryCookieJar;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lijx on 2017/7/22.
 */

public class API {
    public static HttpClientConfig mHttpClientConfig = new HttpClientConfig();

    private static volatile OkHttpClient mHttpClient;
    private static volatile Retrofit mRetrofit;

    /**
     * get api service
     */
    public static <T> T getService(Class<T> service) {
        return getRetrofit().create(service);
    }

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (API.class) {
                if (mRetrofit == null) {
                    OkHttpClient okClient = getOkHttpClient();
                    mRetrofit = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                    .serializeNulls()
                                    .create()))
                            .client(okClient)
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        if (mHttpClient == null) {
            synchronized (API.class) {
                if (mHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(mHttpClientConfig.getConnectionTimeout(), TimeUnit.MILLISECONDS);
                    builder.readTimeout(mHttpClientConfig.getReadTimeout(), TimeUnit.MILLISECONDS);
                    builder.writeTimeout(mHttpClientConfig.getWriteTimeout(), TimeUnit.MILLISECONDS);
                    builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                    builder.cookieJar(new MemoryCookieJar());
                    mHttpClient = builder.build();
                }
            }
        }
        return mHttpClient;
    }

    public static class HttpClientConfig {
        private long connectionTimeout = Config.TIMEOUT_DEFAULT * 2;
        private long readTimeout = Config.TIMEOUT_DEFAULT * 2;
        private long writeTimeout = Config.TIMEOUT_DEFAULT * 2;

        public long getConnectionTimeout() {
            return connectionTimeout;
        }

        public HttpClientConfig setConnectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public long getReadTimeout() {
            return readTimeout;
        }

        public HttpClientConfig setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public long getWriteTimeout() {
            return writeTimeout;
        }

        public HttpClientConfig setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }
    }
}
