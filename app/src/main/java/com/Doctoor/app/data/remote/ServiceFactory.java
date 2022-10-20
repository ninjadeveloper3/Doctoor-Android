package com.Doctoor.app.data.remote;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.Doctoor.app.BuildConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.Doctoor.app.BuildConfig.BASE_URL;
import static com.Doctoor.app.BuildConfig.EASY_PAISA_SERVER;


public class ServiceFactory {

    public interface UserTokenProducer {
        String getUserToken();
    }

    public static <T> T makeService(Class<T> serviceClass, Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        return retrofit.create(serviceClass);
    }

    public static <T> T makeServiceEasyPaisa(Class<T> serviceClass, Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EASY_PAISA_SERVER)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        return retrofit.create(serviceClass);
    }

    public static OkHttpClient.Builder makeOkHttpClientBuilder(@NonNull UserTokenProducer producer) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        AuthorizationInterceptor interceptor = new AuthorizationInterceptor(producer);
        OkHttpClient.Builder builder;
        builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(RemoteConstants.INSTANCE.getTIME_OUT_API(), TimeUnit.SECONDS)
                .writeTimeout(RemoteConstants.INSTANCE.getTIME_OUT_API(), TimeUnit.SECONDS)
                .readTimeout(RemoteConstants.INSTANCE.getTIME_OUT_API(), TimeUnit.SECONDS);

        return builder;
    }
/*
    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);

            return (Converter<ResponseBody, Object>) body -> {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            };
        }
    }

  @NonNull
    public static <T> T getContributionService(Class<T> serviceClass, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(serviceClass);

    }*/
}