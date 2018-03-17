package com.semansoft.ezanisaat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Town implements Parcelable {

    @SerializedName("IlceAdi")
    @Expose
    private String ilceAdi;
    @SerializedName("IlceAdiEn")
    @Expose
    private String ilceAdiEn;
    @SerializedName("IlceID")
    @Expose
    private String ilceID;
    @SerializedName("vakitler")
    @Expose
    private List<TimesOfDay> vakitler=new ArrayList<>();

    public final static Parcelable.Creator<Town> CREATOR = new Creator<Town>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Town createFromParcel(Parcel in) {
            return new Town(in);
        }

        public Town[] newArray(int size) {
            return (new Town[size]);
        }

    };

    protected Town(Parcel in) {
        this.ilceAdi = ((String) in.readValue((String.class.getClassLoader())));
        this.ilceAdiEn = ((String) in.readValue((String.class.getClassLoader())));
        this.ilceID = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(vakitler,TimesOfDay.class.getClassLoader());
    }

    public Town() {
    }

    public String getIlceAdi() {
        return ilceAdi;
    }

    public void setIlceAdi(String ilceAdi) {
        this.ilceAdi = ilceAdi;
    }

    public String getIlceAdiEn() {
        return ilceAdiEn;
    }

    public void setIlceAdiEn(String ilceAdiEn) {
        this.ilceAdiEn = ilceAdiEn;
    }

    public String getIlceID() {
        return ilceID;
    }

    public void setIlceID(String ilceID) {
        this.ilceID = ilceID;
    }

    public List<TimesOfDay> getVakitler() {
        return vakitler;
    }

    public void setVakitler(List<TimesOfDay> vakitler) {
        this.vakitler = vakitler;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ilceAdi);
        dest.writeValue(ilceAdiEn);
        dest.writeValue(ilceID);
        dest.writeList(vakitler);
    }



    public int describeContents() {
        return 0;
    }


    @Override
    public boolean equals(Object obj) {
        try{
            return ((Town)obj).ilceID.equals(ilceID);
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public String toString() {
        return ilceAdi;
    }

}