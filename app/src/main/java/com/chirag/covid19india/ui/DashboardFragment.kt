package com.chirag.covid19india.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.adapters.DailyUpdatesAdapter
import com.chirag.covid19india.databinding.FragmentDashboardBinding
import com.chirag.covid19india.dialog.MessageDialog
import com.chirag.covid19india.interfaces.OnRecyclerViewItemClicked
import com.chirag.covid19india.model.DailyUpdateData
import com.chirag.covid19india.model.StatewiseData
import com.chirag.covid19india.model.TestedData
import com.chirag.covid19india.ui.base.BaseFragment
import com.chirag.covid19india.util.TimeStamp
import com.chirag.covid19india.util.Utils
import com.chirag.covid19india.webservice.APIs
import com.chirag.covid19india.webservice.JSONCallback
import com.chirag.covid19india.webservice.Retrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DashboardFragment : BaseFragment(), View.OnClickListener {
    private lateinit var mBinding: FragmentDashboardBinding
    private lateinit var statewiseData: StatewiseData
    private lateinit var testedData: TestedData
    private var mDailyUpdatesAdapter: DailyUpdatesAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        apiGetNationalData()
        apiGetDailyUpdates()
        setClickEvents()
        mBinding.swipeToRefresh.setOnRefreshListener {
            apiGetNationalData()
            apiGetDailyUpdates()
            mBinding.swipeToRefresh.isRefreshing = false
        }
        mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        return mBinding.root
    }

    private fun apiGetDailyUpdates() {
        try {
            Retrofit.with(mActivity)
                    .setAPI(APIs.API_DAILY_UPDATES)
                    .setGetParameters(null)
                    .setCallBackListener(object : JSONCallback(mContext, showProgressBar()) {
                        override fun onSuccess(statusCode: Int, jsonObject: JSONObject) {}
                        override fun onSuccess(statusCode: Int, jsonArray: JSONArray) {
                            hideProgressBar()
                            try {
                                val gson = Gson()
                                var jsonObject: JSONObject
                                val dailyUpdateList = ArrayList<DailyUpdateData>()
                                for (n in jsonArray.length() - 1 downTo jsonArray.length() - 5) {
                                    jsonObject = jsonArray.getJSONObject(n)
                                    dailyUpdateList.add(gson.fromJson(jsonObject.toString(), object : TypeToken<DailyUpdateData?>() {}.type))
                                }
                                mDailyUpdatesAdapter = DailyUpdatesAdapter(mContext, dailyUpdateList)
                                mBinding.rvDailyUpdates.adapter = mDailyUpdatesAdapter
                                if (dailyUpdateList.size > 0) {
                                    mBinding.rvDailyUpdates.visibility = View.VISIBLE
                                    mBinding.tvViewAllUpdates.visibility = View.VISIBLE
                                    mBinding.tvNoUpdates.visibility = View.GONE
                                } else {
                                    mBinding.rvDailyUpdates.visibility = View.GONE
                                    mBinding.tvViewAllUpdates.visibility = View.GONE
                                    mBinding.tvNoUpdates.visibility = View.VISIBLE
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                hideProgressBar()
                                mBinding.rvDailyUpdates.visibility = View.GONE
                                mBinding.tvViewAllUpdates.visibility = View.GONE
                                mBinding.tvNoUpdates.visibility = View.VISIBLE
                            }
                        }

                        override fun onFailed(statusCode: Int, message: String) {
                            showShortToast(message)
                            hideProgressBar()
                            mBinding.rvDailyUpdates.visibility = View.GONE
                            mBinding.tvViewAllUpdates.visibility = View.GONE
                            mBinding.tvNoUpdates.visibility = View.VISIBLE
                        }
                    })
        } catch (e: Exception) {
            e.printStackTrace()
            MessageDialog(mActivity)
                    .setMessage(e.message)
                    .setPositiveButton(getString(R.string.retry)) { dialog, _ ->
                        dialog.dismiss()
                        apiGetDailyUpdates()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }.show()
        }
    }

    private fun apiGetNationalData() {
        try {
            Retrofit.with(mActivity)
                    .setAPI(APIs.API_NATIONAL_DATA)
                    .setGetParameters(null)
                    .setCallBackListener(object : JSONCallback(mContext, showProgressBar()) {
                        override fun onSuccess(statusCode: Int, jsonObject: JSONObject) {
                            hideProgressBar()
                            try {
                                jsonObject.getJSONArray("statewise")
                                if (jsonObject.getJSONArray("statewise").length() > 0) {
                                    val gson = Gson()
                                    statewiseData = gson.fromJson<StatewiseData>(jsonObject.getJSONArray("statewise").optJSONObject(0).toString(),
                                            object : TypeToken<StatewiseData?>() {}.type)
                                    testedData = gson.fromJson<TestedData>(jsonObject.getJSONArray("tested").optJSONObject(jsonObject.getJSONArray("tested").length() - 1).toString(),
                                            object : TypeToken<TestedData?>() {}.type)

                                    //Confirmed Cases
                                    if (statewiseData.confirmed.isNotEmpty()) mBinding.tvConfirmedCount.text = Utils.getFormattedNumber(statewiseData.confirmed.toDouble(), false) else mBinding.tvConfirmedCount.setText(R.string.no_counts_found)
                                    if (statewiseData.deltaconfirmed.isNotEmpty()) mBinding.tvConfirmedDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltaconfirmed.toDouble(), false)) else mBinding.tvConfirmedDeltaCount.setText(R.string.no_counts_found)

                                    //Active Cases
                                    if (statewiseData.active.isNotEmpty()) mBinding.tvActiveCount.text = Utils.getFormattedNumber(statewiseData.active.toDouble(), false) else {
                                        mBinding.tvActiveCount.setText(R.string.no_counts_found)
                                        mBinding.tvActiveCount.textSize = 20.0f
                                    }

                                    //Death Cases
                                    if (statewiseData.deaths.isNotEmpty()) mBinding.tvDeathCount.text = Utils.getFormattedNumber(statewiseData.deaths.toDouble(), false) else {
                                        mBinding.tvDeathCount.setText(R.string.no_counts_found)
                                        mBinding.tvDeathCount.textSize = 20.0f
                                    }
                                    if (statewiseData.deltadeaths.isNotEmpty()) mBinding.tvDeathDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltadeaths.toDouble(), false)) else {
                                        mBinding.tvDeathDeltaCount.setText(R.string.no_counts_found)
                                        mBinding.tvDeathDeltaCount.textSize = 20.0f
                                    }

                                    //Recovered Cases
                                    if (statewiseData.recovered.isNotEmpty()) mBinding.tvRecoveryCount.text = Utils.getFormattedNumber(statewiseData.recovered.toDouble(), false) else {
                                        mBinding.tvRecoveryCount.setText(R.string.no_counts_found)
                                        mBinding.tvRecoveryCount.textSize = 20.0f
                                    }
                                    if (statewiseData.deltarecovered.isNotEmpty()) mBinding.tvRecoveryDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltarecovered.toDouble(), false)) else {
                                        mBinding.tvRecoveryDeltaCount.setText(R.string.no_counts_found)
                                        mBinding.tvRecoveryDeltaCount.textSize = 20.0f
                                    }

                                    //Tested Cases
                                    when {
                                        testedData.totalindividualstested.isNotEmpty() -> mBinding.tvTestedCount.text = Utils.getFormattedNumber(testedData.totalindividualstested.toDouble(), false)
                                        testedData.totalsamplestested.isNotEmpty() -> mBinding.tvTestedCount.text = Utils.getFormattedNumber(testedData.totalsamplestested.toDouble(), false)
                                        else -> {
                                            mBinding.tvTestedCount.setText(R.string.no_counts_found)
                                            mBinding.tvTestedCount.textSize = 20.0f
                                        }
                                    }

                                    //Latest Updated Time
                                    val lastUpdatedTIme = TimeStamp.dMMMhhmma(TimeStamp.dateToSeconds(statewiseData.lastupdatedtime))
                                    mBinding.tvLatestUpdate.text = String.format("%s IST", lastUpdatedTIme)
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

    private fun setClickEvents() {
        mBinding.cvActiveCases.setOnClickListener(this)
        mBinding.cvConfirmedCases.setOnClickListener(this)
        mBinding.cvDeathCases.setOnClickListener(this)
        mBinding.cvRecoveredCases.setOnClickListener(this)
        mBinding.cvTestedCases.setOnClickListener(this)
        mBinding.tvViewAllUpdates.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cvActiveCases, R.id.cvConfirmedCases, R.id.cvDeathCases, R.id.cvRecoveredCases, R.id.cvTestedCases -> changeFragment!!.onItemClicked()
            R.id.tvViewAllUpdates -> startActivity(Intent(mActivity, DailyUpdatesActivity::class.java))
        }
    }

    companion object {
        private var changeFragment: OnRecyclerViewItemClicked? = null

        @JvmStatic
        fun setListener(listener: OnRecyclerViewItemClicked?) {
            changeFragment = listener
        }
    }
}