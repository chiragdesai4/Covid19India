package com.chirag.covid19india.webservice;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

class ServiceGenerator {

    private static final String TAG = ServiceGenerator.class.getSimpleName();

    // region Constants
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    private static String app_id = "79679861-AAK0-45CA-A50C-65F5F1A57510";
    private static String api_key = "HiFXTGEtkFIcj2htaPe4HSbdRed2Gki42Ih0hfV87u4=";

    /*private static String app_id = "3BFD63BF-5DC7-4DAE-91D0-9D5D4E921D24";
    private static String api_key = "CBUMnb/5J0C8TFP3XRNuC3qpb8ZsxnTqHn1s4JK86xM=";*/

    private String token;
    private static String requestTimeStamp, uniqueID;
    private static byte[] secretKeyByteArray;


    // endregion

    /*private static Retrofit.Builder retrofitBuilder
            = new Retrofit.Builder()
            .baseUrl(DeliveryService.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient defaultOkHttpClient
            = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .cache(getCache())
            .build();*/

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    /*public static <S> S createService(Class<S> serviceClass, String baseUrl) {
//        return createService(serviceClass, baseUrl, null);
        return createService(serviceClass, baseUrl, getInterCeptor());
    }
*/
    private static Interceptor getInterCeptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "token " + generateToken(original.url().toString(), original.method()))
//                        .header("Authorization", "token eyJhbGciOiJSUzI1NiJ9.eyJub25jZSI6IkRuV0h0LVlYVURWLUxKdFVWbnZXRFgxQVVwdmVDVDF4IiwiYXVkIjoibW9iaWxpdGkuYmxpbmR0cnVzdC50aGlyZHBhcnR5Lmlkb3duZXIuZXZpZGVudGlkLmNvbSIsIm")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };
    }

    /*public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        OkHttpClient.Builder httpClient = defaultOkHttpClient.newBuilder();
        Retrofit retrofit = retrofitBuilder.build();

        if (!TextUtils.isEmpty(authToken)) {
            AuthorizedNetworkInterceptor interceptor =
                    new AuthorizedNetworkInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                retrofitBuilder.client(httpClient.build());
                retrofit = retrofitBuilder.build();
            }
        }

        return retrofit.create(serviceClass);
    }*/

    /*public static <S> S createService(Class<S> serviceClass, String baseUrl, Interceptor networkInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();

        if (networkInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
        }

        OkHttpClient modifiedOkHttpClient = okHttpClientBuilder
                .addInterceptor(getHttpLoggingInterceptor())
                .build();

        retrofitBuilder.client(modifiedOkHttpClient);
        retrofitBuilder.baseUrl(baseUrl);

        Retrofit retrofit = retrofitBuilder.build();
        Logger.e("Retro call", "" + "Interceptor call");
        return retrofit.create(serviceClass);
    }

    private static Cache getCache() {

        Cache cache = null;
        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(MobilitiApplication.getCacheDirectory(), "http");
            cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, "Unable to install disk cache.");
        }
        return cache;
    }*/

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
        return httpLoggingInterceptor;
    }

    static String generateToken(String requestUri, String requestHttpMethod) {

        // Calculate UNIX timestamp
        long unixTime = System.currentTimeMillis() / 1000L;
        requestTimeStamp = String.valueOf(unixTime);

        //URL encode
        try {
            requestUri = requestUri.toLowerCase();
            requestUri = URLEncoder.encode(requestUri, "UTF-8");
            requestUri = requestUri.toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Find the UUID
        uniqueID = UUID.randomUUID().toString();

        /*requestUri = "http%3a%2f%2f18.221.250.235%2fapiengine%2fapi%2fvehiclemakeapi%2fgetvehiclemakelist";
        requestTimeStamp = "1511359444";
        uniqueID = "18315f5b-75e5-4c30-9de9-0a28d9c32ba1";*/


//        String signatureRawData = String.Format("{0}{1}{2}{3}{4}", app_id, requestHttpMethod, requestUri, requestTimeStamp, uniqueID);
//        String signatureRawData = String.format(app_id, requestHttpMethod, requestUri, requestTimeStamp, uniqueID, "{0}{1}{2}{3}{4}");
        String signatureRawData = app_id + requestHttpMethod + requestUri + requestTimeStamp + uniqueID;

        //            byte[] name = Base64.encodeBase64("hello World".getBytes());
//        byte[] name = Base64.encode(api_key.getBytes(), 0);
//            byte[] decodedString = Base64.decodeBase64(new String(name).getBytes("UTF-8"));
//            secretKeyByteArray = Base64.decode(new String(api_key).getBytes("UTF-8"), 0);
        secretKeyByteArray = Base64.decode(api_key, Base64.DEFAULT);
//            System.out.println(new String(secretKeyByteArray));


//        byte[] signature = Base64.encode(signatureRawData.getBytes(), 0);
        byte[] signature = new byte[0];
        try {
            signature = signatureRawData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*--------------------================*/
        Mac mac = null;
        try {

            SecretKeySpec secret = new SecretKeySpec(secretKeyByteArray, "HmacSHA256");
            mac = Mac.getInstance("HmacSHA256");
            mac.init(secret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        byte[] hashByte = mac.doFinal(signature);
        // get the base 64 string
        String hash = Base64.encodeToString(hashByte,
                Base64.DEFAULT).trim();

//            StringBuffer stringBuffer = new StringBuffer(hash);
        String trimHash = removeLastChar(hash);
//            String hash = new BigInteger(1, signature).toString(16);

        /*Final token*/
//        Logger.e("Token", "token " + app_id + ":" + trimHash + ":" + uniqueID + ":" + requestTimeStamp);

        /*--------------------================*/
        /*  byte[] data = new byte[0];
        try {
            data = api_key.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            Logger.e("Token_HMAC", "Base_64" + base64);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
*/
//        Toast.makeText(this, "Generate token", Toast.LENGTH_LONG).show();
        return app_id + ":" + trimHash + ":" + uniqueID + ":" + requestTimeStamp;
    }

    private static String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '=') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}

