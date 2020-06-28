package com.chirag.covid19india.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatewiseData {
    @SerializedName("active")
    @Expose
    val active: String? = null

    @SerializedName("confirmed")
    @Expose
    val confirmed: String? = null

    @SerializedName("deaths")
    @Expose
    val deaths: String? = null

    @SerializedName("deltaconfirmed")
    @Expose
    val deltaconfirmed: String? = null

    @SerializedName("deltadeaths")
    @Expose
    val deltadeaths: String? = null

    @SerializedName("deltarecovered")
    @Expose
    val deltarecovered: String? = null

    @SerializedName("lastupdatedtime")
    @Expose
    val lastupdatedtime: String? = null

    @SerializedName("recovered")
    @Expose
    val recovered: String? = null

    @SerializedName("state")
    @Expose
    val state: String? = null

    @SerializedName("statecode")
    @Expose
    val statecode: String? = null

    @SerializedName("statenotes")
    @Expose
    val statenotes: String? = null

    override fun toString(): String {
        return "StatewiseData{" +
                "active='" + active + '\'' +
                ", confirmed='" + confirmed + '\'' +
                ", deaths='" + deaths + '\'' +
                ", deltaconfirmed='" + deltaconfirmed + '\'' +
                ", deltadeaths='" + deltadeaths + '\'' +
                ", deltarecovered='" + deltarecovered + '\'' +
                ", lastupdatedtime='" + lastupdatedtime + '\'' +
                ", recovered='" + recovered + '\'' +
                ", state='" + state + '\'' +
                ", statecode='" + statecode + '\'' +
                ", statenotes='" + statenotes + '\'' +
                '}'
    }
}