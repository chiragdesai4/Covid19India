package com.chirag.covid19india.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NationalData {

    @SerializedName("cases_time_series")
    @Expose
    private List<CasesTimeSeriesData> casesTimeSeryData = null;
    @SerializedName("statewiseData")
    @Expose
    private List<StatewiseData> statewiseData = null;
    @SerializedName("tested")
    @Expose
    private List<TestedData> tested = null;

    public List<CasesTimeSeriesData> getCasesTimeSeryData() {
        return casesTimeSeryData;
    }

    public void setCasesTimeSeryData(List<CasesTimeSeriesData> casesTimeSeryData) {
        this.casesTimeSeryData = casesTimeSeryData;
    }

    public List<StatewiseData> getStatewiseData() {
        return statewiseData;
    }

    public void setStatewiseData(List<StatewiseData> statewiseData) {
        this.statewiseData = statewiseData;
    }

    public List<TestedData> getTested() {
        return tested;
    }

    public void setTested(List<TestedData> tested) {
        this.tested = tested;
    }

    @Override
    public String toString() {
        return "NationalData{" +
                "casesTimeSeryData=" + casesTimeSeryData +
                ", statewiseData=" + statewiseData +
                ", tested=" + tested +
                '}';
    }
}
