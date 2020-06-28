package com.chirag.covid19india.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestedData {

    @SerializedName("positivecasesfromsamplesreported")
    @Expose
    private String positivecasesfromsamplesreported;
    @SerializedName("samplereportedtoday")
    @Expose
    private String samplereportedtoday;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("testsconductedbyprivatelabs")
    @Expose
    private String testsconductedbyprivatelabs;
    @SerializedName("totalindividualstested")
    @Expose
    private String totalindividualstested;
    @SerializedName("totalpositivecases")
    @Expose
    private String totalpositivecases;
    @SerializedName("totalsamplestested")
    @Expose
    private String totalsamplestested;
    @SerializedName("updatetimestamp")
    @Expose
    private String updatetimestamp;

    public String getPositivecasesfromsamplesreported() {
        return positivecasesfromsamplesreported;
    }

    public void setPositivecasesfromsamplesreported(String positivecasesfromsamplesreported) {
        this.positivecasesfromsamplesreported = positivecasesfromsamplesreported;
    }

    public String getSamplereportedtoday() {
        return samplereportedtoday;
    }

    public void setSamplereportedtoday(String samplereportedtoday) {
        this.samplereportedtoday = samplereportedtoday;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTestsconductedbyprivatelabs() {
        return testsconductedbyprivatelabs;
    }

    public void setTestsconductedbyprivatelabs(String testsconductedbyprivatelabs) {
        this.testsconductedbyprivatelabs = testsconductedbyprivatelabs;
    }

    public String getTotalindividualstested() {
        return totalindividualstested;
    }

    public void setTotalindividualstested(String totalindividualstested) {
        this.totalindividualstested = totalindividualstested;
    }

    public String getTotalpositivecases() {
        return totalpositivecases;
    }

    public void setTotalpositivecases(String totalpositivecases) {
        this.totalpositivecases = totalpositivecases;
    }

    public String getTotalsamplestested() {
        return totalsamplestested;
    }

    public void setTotalsamplestested(String totalsamplestested) {
        this.totalsamplestested = totalsamplestested;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }

    public void setUpdatetimestamp(String updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
    }

    @Override
    public String toString() {
        return "TestedData{" +
                "positivecasesfromsamplesreported='" + positivecasesfromsamplesreported + '\'' +
                ", samplereportedtoday='" + samplereportedtoday + '\'' +
                ", source='" + source + '\'' +
                ", testsconductedbyprivatelabs='" + testsconductedbyprivatelabs + '\'' +
                ", totalindividualstested='" + totalindividualstested + '\'' +
                ", totalpositivecases='" + totalpositivecases + '\'' +
                ", totalsamplestested='" + totalsamplestested + '\'' +
                ", updatetimestamp='" + updatetimestamp + '\'' +
                '}';
    }
}
