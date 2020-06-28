package com.chirag.covid19india.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictDatum implements Parcelable {

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("lastupdatedtime")
    @Expose
    private String lastupdatedtime;
    @SerializedName("delta")
    @Expose
    private DistrictDelta delta;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public DistrictDelta getDelta() {
        return delta;
    }

    public void setDelta(DistrictDelta delta) {
        this.delta = delta;
    }




    @Override
    public String toString() {
        return "DistrictDatum{" +
                "district='" + district + '\'' +
                ", confirmed=" + confirmed +
                ", lastupdatedtime='" + lastupdatedtime + '\'' +
                ", delta=" + delta +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.district);
        dest.writeValue(this.confirmed);
        dest.writeString(this.lastupdatedtime);
        dest.writeParcelable(this.delta, flags);
    }

    public DistrictDatum() {
    }

    protected DistrictDatum(Parcel in) {
        this.district = in.readString();
        this.confirmed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastupdatedtime = in.readString();
        this.delta = in.readParcelable(DistrictDelta.class.getClassLoader());
    }

    public static final Creator<DistrictDatum> CREATOR = new Creator<DistrictDatum>() {
        @Override
        public DistrictDatum createFromParcel(Parcel source) {
            return new DistrictDatum(source);
        }

        @Override
        public DistrictDatum[] newArray(int size) {
            return new DistrictDatum[size];
        }
    };
}
