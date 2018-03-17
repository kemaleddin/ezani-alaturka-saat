package com.semansoft.ezanisaat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {

    @SerializedName("UlkeAdi")
    @Expose
    private String ulkeAdi;
    @SerializedName("UlkeAdiEn")
    @Expose
    private String ulkeAdiEn;
    @SerializedName("UlkeID")
    @Expose
    private String ulkeID;
    public final static Parcelable.Creator<Country> CREATOR = new Creator<Country>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return (new Country[size]);
        }

    };

    public Country(String ulkeID) {
        this.ulkeID = ulkeID;
    }

    protected Country(Parcel in) {
        this.ulkeAdi = ((String) in.readValue((String.class.getClassLoader())));
        this.ulkeAdiEn = ((String) in.readValue((String.class.getClassLoader())));
        this.ulkeID = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Country() {
    }

    public String getUlkeAdi() {
        return ulkeAdi;
    }

    public void setUlkeAdi(String ulkeAdi) {
        this.ulkeAdi = ulkeAdi;
    }

    public String getUlkeAdiEn() {
        return ulkeAdiEn;
    }

    public void setUlkeAdiEn(String ulkeAdiEn) {
        this.ulkeAdiEn = ulkeAdiEn;
    }

    public String getUlkeID() {
        return ulkeID;
    }

    public void setUlkeID(String ulkeID) {
        this.ulkeID = ulkeID;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ulkeAdi);
        dest.writeValue(ulkeAdiEn);
        dest.writeValue(ulkeID);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return ulkeAdi;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            return ((Country)obj).ulkeID.equals(ulkeID);
        }catch (Exception e){
            return false;
        }
    }

}