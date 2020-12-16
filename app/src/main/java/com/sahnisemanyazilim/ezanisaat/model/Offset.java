
package com.sahnisemanyazilim.ezanisaat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offset {

    @SerializedName("Imsak")
    @Expose
    private Integer imsak;
    @SerializedName("Fajr")
    @Expose
    private Integer fajr;
    @SerializedName("Sunrise")
    @Expose
    private String sunrise;
    @SerializedName("Dhuhr")
    @Expose
    private String dhuhr;
    @SerializedName("Asr")
    @Expose
    private String asr;
    @SerializedName("Maghrib")
    @Expose
    private String maghrib;
    @SerializedName("Sunset")
    @Expose
    private Integer sunset;
    @SerializedName("Isha")
    @Expose
    private Integer isha;
    @SerializedName("Midnight")
    @Expose
    private Integer midnight;

    public Integer getImsak() {
        return imsak;
    }

    public void setImsak(Integer imsak) {
        this.imsak = imsak;
    }

    public Integer getFajr() {
        return fajr;
    }

    public void setFajr(Integer fajr) {
        this.fajr = fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public void setDhuhr(String dhuhr) {
        this.dhuhr = dhuhr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Integer getIsha() {
        return isha;
    }

    public void setIsha(Integer isha) {
        this.isha = isha;
    }

    public Integer getMidnight() {
        return midnight;
    }

    public void setMidnight(Integer midnight) {
        this.midnight = midnight;
    }

}
