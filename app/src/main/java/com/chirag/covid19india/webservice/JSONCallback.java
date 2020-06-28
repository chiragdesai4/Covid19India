package com.chirag.covid19india.webservice;

import android.content.Context;

import com.chirag.covid19india.R;
import com.chirag.covid19india.ui.base.ProgressDialog;
import com.chirag.covid19india.util.Logger;
import com.chirag.covid19india.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class JSONCallback implements Callback<ResponseBody> {
    private Context context;
    private ProgressDialog dialog;

    public JSONCallback(Context context) throws Exception {
        this(context, null);
    }

    public JSONCallback(Context context, ProgressDialog dialog) throws Exception {
        this.dialog = dialog;
        this.context = context;
        if (dialog != null) dialog.show();

        if (!Utils.isConnectingToInternet(context)) {
            throw new Exception(context.getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        String body = null;
        try {//Converting string to JSONObject
            if (response.isSuccessful()) {
                body = ((ResponseBody) response.body()).string();
                if (body.startsWith("[")) {
                    JSONArray array = new JSONArray(body);
                    if (array.length() > 0) {
                        onSuccess(response.code(), array);
                    }
                } else {
                    JSONObject object = new JSONObject(body);
                    Logger.e("Response", call.request().url().toString() + "\n" + object.toString());
                    if (Objects.requireNonNull(object.optJSONArray("statewise")).length() > 0) {
                        onSuccess(response.code(), object);
                    }
                    else{
                        onFailure(response.code(), object);
                    }
                }
            } else {
                body = response.errorBody().string();
                if (body.isEmpty()) {
                    String message = response.raw().message();
                    Logger.e("Response", call.request().url().toString() + "\n" + message);
                    onFailed(response.code(), message);
                } else {
                    JSONObject object = new JSONObject(body);
                    Logger.e("Response", call.request().url().toString() + "\n" + object.toString());
                    onFailure(response.code(), object);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            if (body != null) Logger.e(body);
//            Utils.generateCrashReport(context, call, body);
            onFailed(response.code(), context.getString(R.string.something_went_wrong));
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Logger.e("Response", call.request().url().toString() + "\n" + t.toString());
        if (!Utils.isConnectingToInternet(context)) {
            onFailed(0, context.getString(R.string.no_internet_connection));
        } else if (t instanceof ConnectException
                || t instanceof SocketTimeoutException
                || t instanceof UnknownHostException) {
            onFailed(0, context.getString(R.string.failed_to_connect_with_server));
        } else if (t instanceof IOException) {
            onFailed(0, context.getString(R.string.no_internet_connection));
        } else {
            onFailed(0, t.getMessage());
        }
    }

    private void onFailure(int statusCode, JSONObject object) {
        if (statusCode == 401) {
            if (dialog != null && dialog.isShowing()) dialog.dismiss();
         /*   new MessageDialog(context)
                    .setMessage(object.optString("message"))
                    .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SessionManager manager = new SessionManager(context);
                            manager.clearSession(context, WalkThroughActivity.class);
                        }
                    }).show();*/
        } else {
            onFailed(statusCode, object.optString("Message"));
        }
    }

    protected abstract void onFailed(int statusCode, String message);

    protected abstract void onSuccess(int statusCode, JSONObject jsonObject) throws JSONException;

    protected abstract void onSuccess(int statusCode, JSONArray jsonArray) throws JSONException;
}
