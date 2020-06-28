package com.chirag.covid19india.webservice;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Chirag Desai
 */

public interface ApiInterface {

    @POST
    Call<ResponseBody> callPostMethod(@Url String url, @Body HashMap<String, String> body);

    @FormUrlEncoded
    @POST
    Call<JsonObject> callPostMethod(@Url String url, @Field("data") String data);

    @Multipart
    @POST
    Call<JsonObject> callMultiPartPostMethod(@Url String url, @Part("data") RequestBody data, @Part List<MultipartBody.Part> files);

    @POST
    Call<ResponseBody> callRegistrationPostMethod(@Url String url, @Body MultipartBody body);

    @Multipart
    @POST
    Call<ResponseBody> callMultiPartPostMethod(@Url String url, @PartMap() Map<String, RequestBody> partMap, @Part List<MultipartBody.Part> files);

    @Multipart
    @POST
    Call<ResponseBody> postMethodMultipart(@Url String endpoint, @HeaderMap HashMap<String, String> headerMap, @PartMap HashMap<String, RequestBody> fields);

    /*@Multipart
    @POST
    Call<JsonObject> callMultiPartPostMethod(@Url String url, @Field("data") String data, @Part List<MultipartBody.Part> files);*/

    @Multipart
    @POST
    Call<JsonObject> callMultiImageMethod(@Url String url, @Part("data") RequestBody data, @Part MultipartBody.Part[] image);

    @POST
    Call<ResponseBody> callPostMethod(@Url String url, @Body RequestBody body);

    @POST
    Call<ResponseBody> callPostMethod(@Url String url);

    @GET
    Call<ResponseBody> callGetMethod(@Url String url);

    @GET
//APIs.API_LANGUAGE_LIST
    Call<JsonObject> callGetMethodDirect(@Url String url);

    /*@POST
    Call<JsonObject> callChatSyncContact(@Url String url, @Body ContactSyncRequestBody contactjson);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);*/

    @GET("/maps/api/directions/json")
    Call<JsonObject> getPolylineData(@Query("origin") String origin,
                                     @Query("destination") String destination,
                                     @Query("key") String apiKey,
                                     @Query("language") String language,
                                     @Query("units") String units);

    @GET("/maps/api/geocode/json")
    Call<JsonObject> getGeoCodeData(@Query("latlng") String latLng,
                                    @Query("key") String apiKey,
                                    @Query("language") String language,
                                    @Query("units") String units);

}
