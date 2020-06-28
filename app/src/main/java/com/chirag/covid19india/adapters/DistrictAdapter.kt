package com.chirag.covid19india.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.XItemDistrictDataBinding
import com.chirag.covid19india.model.DistrictDatum
import com.chirag.covid19india.util.Utils
import java.util.*

class DistrictAdapter(private val context: Context, private val districtDataList: ArrayList<DistrictDatum>) : RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding: XItemDistrictDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.x_item_district_data, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val districtData = districtDataList[position]

        //State Name
        holder.mBinder.tvDistrictName.text = districtData.district

        //Confirmed Cases
        holder.mBinder.tvConfirmedCount.text = Utils.getFormattedNumber(districtData.confirmed.toString().toDouble(), false)
        holder.mBinder.tvConfirmedDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(districtData.delta.confirmed.toString().toDouble(), false))
    }

    override fun getItemCount(): Int {
        return districtDataList.size
    }

    class ViewHolder(var mBinder: XItemDistrictDataBinding) : RecyclerView.ViewHolder(mBinder.root)

}