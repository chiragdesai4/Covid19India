package com.chirag.covid19india.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyUpdateData implements Parcelable {
    @SerializedName("update")
    @Expose
    private String update;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DailyUpdateData{" +
                "update='" + update + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.update);
        dest.writeValue(this.timestamp);
    }

    public DailyUpdateData() {
    }

    protected DailyUpdateData(Parcel in) {
        this.update = in.readString();
        this.timestamp = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<DailyUpdateData> CREATOR = new Creator<DailyUpdateData>() {
        @Override
        public DailyUpdateData createFromParcel(Parcel source) {
            return new DailyUpdateData(source);
        }

        @Override
        public DailyUpdateData[] newArray(int size) {
            return new DailyUpdateData[size];
        }
    };
}
