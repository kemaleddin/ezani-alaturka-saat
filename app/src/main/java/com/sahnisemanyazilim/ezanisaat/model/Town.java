package com.sahnisemanyazilim.ezanisaat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.TimeUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sahnisemanyazilim.ezanisaat.enums.TimeEnum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private List<TimesOfDay> vakitler = new ArrayList<>();

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
    private boolean active;

    protected Town(Parcel in) {
        this.ilceAdi = ((String) in.readValue((String.class.getClassLoader())));
        this.ilceAdiEn = ((String) in.readValue((String.class.getClassLoader())));
        this.ilceID = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((Boolean) in.readValue(Boolean.class.getClassLoader()));
        in.readList(vakitler, TimesOfDay.class.getClassLoader());
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

    public List<TimesOfDay> getTimesOfDays() {
        return vakitler;
    }

    public Date getNearestTime(TimeEnum time,long toAddMs) throws Exception {
        if (needUpdate())
            throw new Exception("times need to update");
        Date now = Calendar.getInstance().getTime();
        TimesOfDay today = TimesOfDay.getToDay();
        today = vakitler.get(vakitler.indexOf(today));
        Date vakit = today.getVakitAsDateTime(time,toAddMs);
        if (vakit.after(now))
            return vakit;
        TimesOfDay tomorrow = TimesOfDay.getMockToMorrow();
        tomorrow = vakitler.get(vakitler.indexOf(tomorrow));
        vakit = tomorrow.getVakitAsDateTime(time,toAddMs);
        return vakit;
    }


    public void setVakitler(List<TimesOfDay> vakitler) {
        this.vakitler = vakitler;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ilceAdi);
        dest.writeValue(ilceAdiEn);
        dest.writeValue(ilceID);
        dest.writeValue(active);
        dest.writeList(vakitler);
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public boolean equals(Object obj) {
        try {
            return ((Town) obj).ilceID.equals(ilceID);
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public String toString() {
        return ilceAdi;
    }

    public void setActive(String active) {
        this.active = ilceID.equals(active);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Town getClone() {
        Town town = new Town();
        town.setIlceAdi(ilceAdi);
        town.setIlceAdiEn(ilceAdiEn);
        town.setIlceID(ilceID);
        return town;
    }

    public boolean needUpdate() {
        return vakitler.size() < 10 || vakitler.get(vakitler.size() - 1).isOld();
    }

    public int getIlceIDInt() {
        try {
            return Integer.parseInt(ilceID);
        } catch (Exception e) {
        }
        return 0;
    }
}