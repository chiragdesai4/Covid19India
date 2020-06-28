package com.chirag.covid19india.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CasesTimeSeriesData {
    @SerializedName("dailyconfirmed")
    @Expose
    private val dailyconfirmed: String? = null

    @SerializedName("dailydeceased")
    @Expose
    private val dailydeceased: String? = null

    @SerializedName("dailyrecovered")
    @Expose
    private val dailyrecovered: String? = null

    @SerializedName("date")
    @Expose
    private val date: String? = null

    @SerializedName("totalconfirmed")
    @Expose
    private val totalconfirmed: String? = null

    @SerializedName("totaldeceased")
    @Expose
    private val totaldeceased: String? = null

    @SerializedName("totalrecovered")
    @Expose
    private val totalrecovered: String? = null
    override fun toString(): String {
        return "CasesTimeSeriesData{" +
                "dailyconfirmed='" + dailyconfirmed + '\'' +
                ", dailydeceased='" + dailydeceased + '\'' +
                ", dailyrecovered='" + dailyrecovered + '\'' +
                ", date='" + date + '\'' +
                ", totalconfirmed='" + totalconfirmed + '\'' +
                ", totaldeceased='" + totaldeceased + '\'' +
                ", totalrecovered='" + totalrecovered + '\'' +
                '}'
    }
}