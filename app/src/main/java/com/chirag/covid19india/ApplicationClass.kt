package com.chirag.covid19india

import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDexApplication

class ApplicationClass : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}