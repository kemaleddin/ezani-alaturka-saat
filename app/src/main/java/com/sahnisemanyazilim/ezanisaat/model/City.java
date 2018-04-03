package com.sahnisemanyazilim.ezanisaat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

    @SerializedName("SehirAdi")
    @Expose
    private String sehirAdi;
    @SerializedName("SehirAdiEn")
    @Expose
    private String sehirAdiEn;
    @SerializedName("SehirID")
    @Expose
    private String sehirID;
    public final static Parcelable.Creator<City> CREATOR = new Creator<City>() {


        @SuppressWarnings({
                "unchecked"
        })
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        public City[] newArray(int size) {
            return (new City[size]);
        }

    };

    protected City(Parcel in) {
        this.sehirAdi = ((String) in.readValue((String.class.getClassLoader())));
        this.sehirAdiEn = ((String) in.readValue((String.class.getClassLoader())));
        this.sehirID = ((String) in.readValue((String.class.getClassLoader())));
    }

    public City() {
    }

    public String getSehirAdi() {
        return sehirAdi;
    }

    public void setSehirAdi(String sehirAdi) {
        this.sehirAdi = sehirAdi;
    }

    public String getSehirAdiEn() {
        return sehirAdiEn;
    }

    public void setSehirAdiEn(String sehirAdiEn) {
        this.sehirAdiEn = sehirAdiEn;
    }

    public String getSehirID() {
        return sehirID;
    }

    public void setSehirID(String sehirID) {
        this.sehirID = sehirID;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sehirAdi);
        dest.writeValue(sehirAdiEn);
        dest.writeValue(sehirID);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return sehirAdi;
    }
}