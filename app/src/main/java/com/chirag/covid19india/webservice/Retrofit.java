package com.chirag.covid19india.webservice;

import android.content.Context;
import android.util.Log;

import com.chirag.covid19india.util.Logger;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Retrofit {
    private Context context;

    private String baseURL;
    private String endPoint, endPointExtra = "";

    private HashMap<String, String> params = new HashMap<>();
    private HashMap<String, File> fileParams = new HashMap<>();
    private HashMap<String, String> headerMap = new HashMap<>();
    HashMap<String, RequestBody> bodyParams = new HashMap<>();

    RequestBody bodyRequest;
    private JSONObject jsonObject;
    Map<String, RequestBody> partMap = new HashMap<>();
    List<MultipartBody.Part> files = new ArrayList<>();

    MultipartBody body;

    public Retrofit(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @return Instance of this class
     * create instance of this class
     */
    public static Retrofit with(Context context) {
        return new Retrofit(context);
    }

    /**
     * @param baseUrl
     * @return Instance
     * set Base Url for temporary
     * optional method if you set default Base URL in APIs class
     */
    public Retrofit setUrl(String baseUrl) {
        this.baseURL = baseUrl;
        return this;
    }

    /**
     * @param endPoint
     * @return Instance
     * set Endpoint when call every time
     */
    public Retrofit setAPI(String endPoint) {
        this.endPoint = endPoint;
        Logger.e("URL", APIs.BASE_URL + endPoint);
        return this;
    }
    public Retrofit setAPIDemo(String endPoint) {
        this.endPoint = endPoint;
        Logger.e("URL", APIs.BASE_URL + endPoint);
        return this;
    }

    /**
     * @param token
     * @return Instance
     * set Header when call every time
     */
    public Retrofit setHeader(String token) {
        headerMap.put("Authorization", token);
        Logger.e("header", token);
        return this;
    }


    /**
     * @param headerMap
     * @return Instance
     * set Header when call every time
     */
    public Retrofit setHeader(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            Logger.e("header", entry.getKey() + "\t" + entry.getValue());
        }
        return this;
    }

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
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

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(HashMap<String, String> params) {
        this.params = params;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Logger.e("params", entry.getKey() + "\t" + entry.getValue());
        }
        return this;
    }

    /**
     * @param jsonObject
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return this;
    }

    /**
     * @param mJsonObject
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(JSONObject mJsonObject, HashMap<String, File> fileParams) {
        bodyParams = new HashMap<>();
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
//            String fileName = entry.getKey() + "\"; filename=\"" + entry.getValue().getName();
            String fileName = entry.getKey();
            Log.e("FileName", fileName);
            bodyParams.put(fileName, RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue()));
        }
        this.fileParams = fileParams;
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mJsonObject.toString());
        bodyParams.put("json", mRequestBody);
        Log.e("RequestBody", params.toString());
        return this;
    }

    /**
     * @param mJsonObject
     * @return Call
     * to set request parameter
     */
    /*public Retrofit setParametersNew(String mJsonObject, HashMap<String, File> fileParams) {

        bodyParams = new HashMap<>();
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
//            String fileName = entry.getKey() + "\"; filename=\"" + entry.getValue().getName();
            String fileName = entry.getKey();
            Log.e("FileName", fileName);

//            MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"), entry.getValue());
            files.add(MultipartBody.Part.createFormData(fileName, entry.getValue().getPath().substring(entry.getValue().getPath().lastIndexOf("/") + 1), requestImageFile));
            bodyParams.put(fileName, RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue()));

            partMap.put(fileName + "\"; filename=\"" + entry.getValue().getPath().substring(entry.getValue().getPath().lastIndexOf("/") + 1) + "\"", requestImageFile);
        }
        this.fileParams = fileParams;
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("text/plain"), mJsonObject);
        partMap.put("json", mRequestBody);

        jsonData = mJsonObject;
//        bodyParams.put("json", mRequestBody);
        return this;
    }*/


    /**
     * @param mJsonObject
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(String mJsonObject, HashMap<String, File> fileParams) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (fileParams != null) {
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {

                String fileName = entry.getKey();
                Log.e("FileName", fileName);
                RequestBody requestImageFile = RequestBody.create(MediaType.parse("image/*"), entry.getValue());
                builder.addFormDataPart(fileName, entry.getValue().getPath().substring(entry.getValue().getPath().lastIndexOf("/") + 1), requestImageFile);

            }
            this.fileParams = fileParams;
        }
        /*RequestBody mRequestBody = RequestBody.create(MediaType.parse("text/plain"), mJsonObject);
        builder.addPart(mRequestBody);*/
        builder.addFormDataPart("json", mJsonObject);
        body = builder.build();
        return this;
    }

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(HashMap<String, String> params, HashMap<String, File> fileParams) {
        bodyParams = new HashMap<>();
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
//            String fileName = entry.getKey() + "\"; filename=\"" + entry.getValue().getName();
            String fileName = entry.getKey();
            Log.e("FileName", fileName);
            bodyParams.put(fileName, RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue()));
        }
        this.fileParams = fileParams;
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), params.toString());
        bodyParams.put("json", mRequestBody);
        Log.e("RequestBody", params.toString());
        return this;
    }

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
    /*public Retrofit setParameters(String params, HashMap<String, File> fileParams) {
        bodyParams = new HashMap<>();
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
//            String fileName = entry.getKey() + "\"; filename=\"" + entry.getValue().getName();
            String fileName = entry.getKey();
            Log.e("FileName", fileName);
            bodyParams.put(fileName, RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue()));
        }
        this.fileParams = fileParams;
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), params);
        bodyParams.put("json", mRequestBody);
        Log.e("RequestBody", params.toString());
        return this;
    }*/

    /**
     * @param files
     * @return Call
     */
    public Retrofit setFileParameters(HashMap<String, File> files) {
        this.fileParams = files;
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
            Logger.e("params", entry.getKey() + "\t" + entry.getValue());
        }
        return this;
    }

    public ApiInterface getAPIInterface() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);
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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = chain.request().newBuilder()
                                //.header("Authorization", "token " + generateToken(original.url().toString(), original.method()))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        ApiInterface APIInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        if (bodyRequest != null) {
            call = APIInterface.callPostMethod(endPoint, bodyRequest);
        } else if (params.size() > 0) {
            call = APIInterface.callPostMethod(endPoint, params);
        } else {
            call = APIInterface.callGetMethod(endPoint.concat(endPointExtra));
        }
        return call;
    }

    public void setCallBackListenerMultipart(JSONCallbackMultipart listener) {
        makeCallMultipart().enqueue(listener);
    }

    private Call<ResponseBody> makeCallMultipart() {
        Call<ResponseBody> call;

        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = chain.request().newBuilder()
                                //.header("Authorization", "token " + generateToken(original.url().toString(), original.method()))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        ApiInterface APIInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        call = APIInterface.callRegistrationPostMethod(endPoint, body);
        return call;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("Log", message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
