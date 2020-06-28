package com.chirag.covid19india.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictDelta implements Parcelable {
    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }



    @Override
    public String toString() {
        return "DistrictDelta{" +
                "confirmed=" + confirmed +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.confirmed);
    }

    public DistrictDelta() {
    }

    protected DistrictDelta(Parcel in) {
        this.confirmed = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<DistrictDelta> CREATOR = new Creator<DistrictDelta>() {
        @Override
        public DistrictDelta createFromParcel(Parcel source) {
            return new DistrictDelta(source);
        }

        @Override
        public DistrictDelta[] newArray(int size) {
            return new DistrictDelta[size];
        }
    };
}
