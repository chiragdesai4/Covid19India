package com.chirag.covid19india;

import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDexApplication;


public class ApplicationClass extends MultiDexApplication {

    private static ApplicationClass context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Context getAppContext() {
        return context;
    }

}
