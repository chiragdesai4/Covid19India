package com.chirag.covid19india.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.adapters.DailyUpdatesAdapter
import com.chirag.covid19india.databinding.ActivityDailyUpdatesBinding
import com.chirag.covid19india.dialog.MessageDialog
import com.chirag.covid19india.model.DailyUpdateData
import com.chirag.covid19india.ui.base.BaseActivity
import com.chirag.covid19india.webservice.APIs
import com.chirag.covid19india.webservice.JSONCallback
import com.chirag.covid19india.webservice.Retrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class DailyUpdatesActivity : BaseActivity() {
    lateinit var mBinding: ActivityDailyUpdatesBinding
    private var mDailyUpdatesAdapter: DailyUpdatesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_daily_updates)
        mBinding.toolbar.tvTitle.text = getString(R.string.sting_updates)
        apiGetDailyUpdates()
        mBinding.swipeToRefresh.setOnRefreshListener {
            apiGetDailyUpdates()
            mBinding.swipeToRefresh.isRefreshing = false
        }
        mBinding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        mBinding.toolbar.ivCloseBack.setOnClickListener { finish() }
    }

    private fun apiGetDailyUpdates() {
        try {
            Retrofit.with(this)
                    .setAPI(APIs.API_DAILY_UPDATES)
                    .setGetParameters(null)
                    .setCallBackListener(object : JSONCallback(this, showProgressBar()) {
                        override fun onSuccess(statusCode: Int, jsonObject: JSONObject) {}
                        override fun onSuccess(statusCode: Int, jsonArray: JSONArray) {
                            hideProgressBar()
                            try {
                                val gson = Gson()
                                var jsonObject: JSONObject
                                val dailyUpdateList = ArrayList<DailyUpdateData>()
                                for (n in 0 until jsonArray.length()) {
                                    jsonObject = jsonArray.getJSONObject(n)
                                    dailyUpdateList.add(gson.fromJson<DailyUpdateData>(jsonObject.toString(), object : TypeToken<DailyUpdateData?>() {}.type))
                                }
                                dailyUpdateList.reverse()
                                mDailyUpdatesAdapter = DailyUpdatesAdapter(this@DailyUpdatesActivity, dailyUpdateList)
                                mBinding.rvDailyUpdates.adapter = mDailyUpdatesAdapter
                                if (dailyUpdateList.size > 0) {
                                    mBinding.rvDailyUpdates.visibility = View.VISIBLE
                                    mBinding.tvNoUpdates.visibility = View.GONE
                                } else {
                                    mBinding.rvDailyUpdates.visibility = View.GONE
                                    mBinding.tvNoUpdates.visibility = View.VISIBLE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                hideProgressBar()
                                mBinding.rvDailyUpdates.visibility = View.GONE
                                mBinding.tvNoUpdates.visibility = View.VISIBLE
                            }
                        }

                        override fun onFailed(statusCode: Int, message: String) {
                            showShortToast(message)
                            hideProgressBar()
                            mBinding.rvDailyUpdates.visibility = View.GONE
                            mBinding.tvNoUpdates.visibility = View.VISIBLE
                        }
                    })
        } catch (e: Exception) {
            e.printStackTrace()
            MessageDialog(this)
                    .setMessage(e.message)
                    .setPositiveButton(getString(R.string.retry)) { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                        apiGetDailyUpdates()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int -> dialog.dismiss() }.show()
        }
    }
}