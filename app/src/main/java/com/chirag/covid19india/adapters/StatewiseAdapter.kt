package com.chirag.covid19india.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.XItemStateDataBinding
import com.chirag.covid19india.model.DistrictData
import com.chirag.covid19india.model.StatewiseData
import com.chirag.covid19india.ui.StateDetailsActivity
import com.chirag.covid19india.util.Utils
import java.util.*

class StatewiseAdapter(private val context: Context, private var dataArrayList: ArrayList<StatewiseData>, private val districtDataList: ArrayList<DistrictData>) : RecyclerView.Adapter<StatewiseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding: XItemStateDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.x_item_state_data, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statewiseData = dataArrayList[position]

        //State Name
        holder.mBinder.tvStateName.text = statewiseData.state

        //Confirmed Cases
        holder.mBinder.tvConfirmedCount.text = Utils.getFormattedNumber(statewiseData.confirmed.toDouble())
        holder.mBinder.tvConfirmedDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltaconfirmed.toDouble()))

        //Active Cases
        holder.mBinder.tvActiveCount.text = Utils.getFormattedNumber(statewiseData.active.toDouble())

        //Death Cases
        holder.mBinder.tvDeathCount.text = Utils.getFormattedNumber(statewiseData.deaths.toDouble())
        holder.mBinder.tvDeathDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltadeaths.toDouble()))

        //Recovered Cases
        holder.mBinder.tvRecoveredCount.text = Utils.getFormattedNumber(statewiseData.recovered.toDouble())
        holder.mBinder.tvRecoveredDeltaCount.text = String.format("[+%s]", Utils.getFormattedNumber(statewiseData.deltarecovered.toDouble()))

        //Tested Cases
        holder.mBinder.cvStateCases.setOnClickListener {
            for (i in districtDataList.indices) {
                if (statewiseData.state.equals(districtDataList[i].state, ignoreCase = true)) {
                    context.startActivity(Intent(context, StateDetailsActivity::class.java)
                            .putExtra("DistrictData", districtDataList[i]))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    class ViewHolder(var mBinder: XItemStateDataBinding) : RecyclerView.ViewHolder(mBinder.root)

    fun filterList(filterdNames: ArrayList<StatewiseData>) {
        dataArrayList = filterdNames
        notifyDataSetChanged()
    }

}