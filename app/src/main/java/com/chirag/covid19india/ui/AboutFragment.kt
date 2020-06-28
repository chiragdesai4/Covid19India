package com.chirag.covid19india.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.FragmentAboutBinding
import com.chirag.covid19india.ui.base.BaseFragment

class AboutFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val mBinding: FragmentAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        mBinding.toolbar.tvTitle.text = getString(R.string.string_about)
        mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        return mBinding.root
    }
}