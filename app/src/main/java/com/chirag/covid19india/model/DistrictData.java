package com.chirag.covid19india.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DistrictData implements Parcelable {


    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("districtData")
    @Expose
    private ArrayList<DistrictDatum> districtData = null;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<DistrictDatum> getDistrictData() {
        return districtData;
    }

    public void setDistrictData(ArrayList<DistrictDatum> districtData) {
        this.districtData = districtData;
    }

    @Override
    public String toString() {
        return "DistrictData{" +
                "state='" + state + '\'' +
                ", districtData=" + districtData +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.state);
        dest.writeList(this.districtData);
    }

    public DistrictData() {
    }

    protected DistrictData(Parcel in) {
        this.state = in.readString();
        this.districtData = new ArrayList<>();
        in.readList(this.districtData, DistrictDatum.class.getClassLoader());
    }

    public static final Creator<DistrictData> CREATOR = new Creator<DistrictData>() {
        @Override
        public DistrictData createFromParcel(Parcel source) {
            return new DistrictData(source);
        }

        @Override
        public DistrictData[] newArray(int size) {
            return new DistrictData[size];
        }
    };
}
