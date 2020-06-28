package com.chirag.covid19india.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.XItemUpdateDataBinding
import com.chirag.covid19india.model.DailyUpdateData
import com.chirag.covid19india.util.TimeStamp
import java.util.*

class DailyUpdatesAdapter(private val context: Context, private val mUpdateDataArrayList: ArrayList<DailyUpdateData>) : RecyclerView.Adapter<DailyUpdatesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding: XItemUpdateDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.x_item_update_data, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyUpdateData = mUpdateDataArrayList[position]

        //Update Text
        holder.mBinder.tvUpdates.text = dailyUpdateData.update

        //Update Time
        val lastUpdatedTIme = TimeStamp.dMMMhhmma(dailyUpdateData.timestamp.toLong())
        holder.mBinder.tvUpdateTime.text = String.format("%s IST", lastUpdatedTIme)
    }

    override fun getItemCount(): Int {
        return mUpdateDataArrayList.size
    }

    class ViewHolder(var mBinder: XItemUpdateDataBinding) : RecyclerView.ViewHolder(mBinder.root)

}