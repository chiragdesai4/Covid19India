package com.chirag.covid19india.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.chirag.covid19india.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Hex.encodeHex;

public class Utils {

    public static void setLocale(Activity activity, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
    }

    public static String encode(String data) {
        Mac sha256_HMAC;
        String s = "";
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec("}{(*%$}&*^\\({#$%&@}^*$#{?}@^%#)(^%&".getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte digest[] = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
            s = new String(encodeHex(digest));
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    Logger.d("Network", "NETWORK NAME: " + networkInfo.getTypeName());
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Logger.d("Network", "NETWORK NAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void ShareTheApp(Activity mActivity) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mActivity.getResources().getString(R.string.app_name) + "\n\n" + "https://play.google.com/store/apps/details?id=" + mActivity.getPackageName());
        sendIntent.setType("text/plain");
        mActivity.startActivity(Intent.createChooser(sendIntent, mActivity.getResources().getText(R.string.share_via)));
    }

    public static void ShareInfo(Activity mActivity, String info) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, info + "\n\nDownload now at:\n" + "https://play.google.com/store/apps/details?id=" + mActivity.getPackageName());
        sendIntent.setType("text/plain");
        mActivity.startActivity(Intent.createChooser(sendIntent, mActivity.getResources().getText(R.string.share_via)));
    }

    //Image Real Path from URI
    public static String getRealPathFromURI(Context mContext, Uri contentURI) {
        String result;
        Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static void setCurrentDate(AppCompatTextView tvDay, AppCompatTextView tvDate) {
        String[] strDate = TimeStamp.getCurrentDateElements();
        tvDay.setText(strDate[0]);
        tvDate.setText(strDate[1]);
    }


    public static void openDirectionInGoogleMap(Context context, double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", latitude, longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(mapIntent);
    }

    public static void openLinkInBrowser(Context mContext, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://") && url.contains("."))
            url = "http://" + url;
        if (url.length() > 0 && isValidUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(browserIntent);
        } else {
            Toast.makeText(mContext, R.string.no_valid_url_available, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://") && url.contains("."))
            url = "http://" + url;
        Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static String getFormattedNumber(double amount, boolean appendSymbol) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    /*public static String getFormattedNumber(String amount, boolean appendSymbol) {
        if (amount != null && amount.length() > 0) {
            try {
                double value = Double.parseDouble(amount);
                return getFormattedNumber(value, appendSymbol);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }*/


}