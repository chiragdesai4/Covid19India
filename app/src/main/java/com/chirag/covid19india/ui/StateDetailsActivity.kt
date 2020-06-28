package com.chirag.covid19india.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.adapters.DistrictAdapter
import com.chirag.covid19india.databinding.ActivityStateDetailsBinding
import com.chirag.covid19india.model.DistrictData
import com.chirag.covid19india.ui.base.BaseActivity
import com.chirag.covid19india.util.Logger

class StateDetailsActivity : BaseActivity() {
    lateinit var mBinding: ActivityStateDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_state_details)
        val districtData: DistrictData = intent.getParcelableExtra("DistrictData")!!
        Logger.e("districtDataIntent", districtData.toString())
        mBinding.toolbar.tvTitle.text = String.format("Confirmed cases in %s", districtData.state)
        mBinding.rvDistrictList.adapter = DistrictAdapter(this, districtData.districtData)
        mBinding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        mBinding.toolbar.ivCloseBack.setOnClickListener { finish() }
    }
}