package com.chirag.covid19india.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.adapters.StatewiseAdapter
import com.chirag.covid19india.databinding.FragmentStateListBinding
import com.chirag.covid19india.dialog.MessageDialog
import com.chirag.covid19india.model.DistrictData
import com.chirag.covid19india.model.StatewiseData
import com.chirag.covid19india.ui.base.BaseFragment
import com.chirag.covid19india.util.Logger
import com.chirag.covid19india.webservice.APIs
import com.chirag.covid19india.webservice.JSONCallback
import com.chirag.covid19india.webservice.Retrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class StateListFragment : BaseFragment() {
    private lateinit var mBinding: FragmentStateListBinding
    private var statewiseDataList: ArrayList<StatewiseData>? = null
    private var statewiseAdapter: StatewiseAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_state_list, container, false)
        apiGetNationalData()
        mBinding.swipeToRefresh.setOnRefreshListener {
            apiGetNationalData()
            mBinding.swipeToRefresh.isRefreshing = false
        }
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
            }
        })
        mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        return mBinding.root
    }

    private fun apiGetNationalData() {
        try {
            Retrofit.with(mActivity)
                    .setAPI(APIs.API_NATIONAL_DATA)
                    .setGetParameters(null)
                    .setCallBackListener(object : JSONCallback(mContext, showProgressBar()) {
                        override fun onSuccess(statusCode: Int, jsonObject: JSONObject) {
                            try {
                                jsonObject.getJSONArray("statewise")
                                if (jsonObject.getJSONArray("statewise").length() > 0) {
                                    val gson = Gson()
                                    statewiseDataList = ArrayList()
                                    for (i in 1 until jsonObject.getJSONArray("statewise").length()) {
                                        statewiseDataList!!.add(gson.fromJson(jsonObject.getJSONArray("statewise").optJSONObject(i).toString(),
                                                object : TypeToken<StatewiseData?>() {}.type))
                                    }
                                    apiGetDistrictData()
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                hideProgressBar()
                            }
                        }

                        override fun onSuccess(statusCode: Int, jsonArray: JSONArray) {}
                        override fun onFailed(statusCode: Int, message: String) {
                            showShortToast(message)
                            hideProgressBar()
                        }
                    })
        } catch (e: Exception) {
            e.printStackTrace()
            MessageDialog(mActivity)
                    .setMessage(e.message)
                    .setPositiveButton(getString(R.string.retry)) { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                        apiGetNationalData()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int -> dialog.dismiss() }.show()
        }
    }

    private fun apiGetDistrictData() {
        try {
            Retrofit.with(mActivity)
                    .setAPI(APIs.API_STATE_DISTRICT_DATA)
                    .setGetParameters(null)
                    .setCallBackListener(object : JSONCallback(mContext) {
                        override fun onSuccess(statusCode: Int, jsonObject: JSONObject) {}
                        override fun onSuccess(statusCode: Int, jsonArray: JSONArray) {
                            hideProgressBar()
                            try {
                                val gson = Gson()
                                var jsonObject: JSONObject
                                val districtDataList = ArrayList<DistrictData>()
                                for (n in 0 until jsonArray.length()) {
                                    jsonObject = jsonArray.getJSONObject(n)
                                    districtDataList.add(gson.fromJson(jsonObject.toString(), object : TypeToken<DistrictData?>() {}.type))
                                }
                                Logger.d("District Data", districtDataList.toString())
                                statewiseAdapter = statewiseDataList?.let { StatewiseAdapter(mContext, it, districtDataList) }
                                mBinding.rvStateList.adapter = statewiseAdapter
                            } catch (e: Exception) {
                                e.printStackTrace()
                                hideProgressBar()
                            }
                        }

                        override fun onFailed(statusCode: Int, message: String) {
                            showShortToast(message)
                            hideProgressBar()
                        }
                    })
        } catch (e: Exception) {
            e.printStackTrace()
            MessageDialog(mActivity)
                    .setMessage(e.message)
                    .setPositiveButton(getString(R.string.retry)) { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                        apiGetDistrictData()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int -> dialog.dismiss() }.show()
        }
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<StatewiseData>()

        //looping through existing elements
        for (s in statewiseDataList!!) {
            //if the existing elements contains the search input
            if (s.state.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault())) ||
                    s.state.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))) {
                //adding the element to filtered list
                filteredNames.add(s)
            }
        }
        statewiseAdapter!!.filterList(filteredNames)
    }
}