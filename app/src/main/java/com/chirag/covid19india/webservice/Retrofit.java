package com.chirag.covid19india.webservice;

import android.content.Context;
import android.util.Log;

import com.chirag.covid19india.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Retrofit {
    private Context context;

    private String baseURL;
    private String endPoint, endPointExtra = "";

    public Retrofit(Context context) {
        this.context = context;
    }

    public static Retrofit with(Context context) {
        return new Retrofit(context);
    }

    public Retrofit setAPI(String endPoint) {
        this.endPoint = endPoint;
        Logger.e("URL", APIs.BASE_URL + endPoint);
        return this;
    }

    public Retrofit setGetParameters(HashMap<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Logger.e("params", entry.getKey() + "\t" + entry.getValue());
                endPointExtra = endPointExtra.concat(endPointExtra.contains("?") ? "&" : "?").concat(entry.getKey()).concat("=").concat(entry.getValue());
            }
            Logger.e("EndpointExtra: ", endPointExtra);
        }
        return this;
    }

    public void setCallBackListener(JSONCallback listener) {
        makeCall().enqueue(listener);
    }

    private Call<ResponseBody> makeCall() {
        Call<ResponseBody> call;

        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = chain.request().newBuilder()
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        ApiInterface APIInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        call = APIInterface.callGetMethod(endPoint.concat(endPointExtra));
        return call;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d("Log", message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
