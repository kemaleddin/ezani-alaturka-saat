package com.semansoft.ezanisaat.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kemalettinsargin.mylib.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.HijrahDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat")
public class TimesOfDay implements Parcelable {
    public static final DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
    private static final long ONE_DAY_MILLIS = 86400000L;
    @SerializedName("Aksam")
    @Expose
    private String aksam;
    @SerializedName("AyinSekliURL")
    @Expose
    private String ayinSekliURL;
    @SerializedName("Gunes")
    @Expose
    private String gunes;
    @SerializedName("GunesBatis")
    @Expose
    private String gunesBatis;
    @SerializedName("GunesDogus")
    @Expose
    private String gunesDogus;
    @SerializedName("HicriTarihKisa")
    @Expose
    private String hicriTarihKisa;
    @SerializedName("HicriTarihUzun")
    @Expose
    private String hicriTarihUzun;
    @SerializedName("Ikindi")
    @Expose
    private String ikindi;
    @SerializedName("Imsak")
    @Expose
    private String imsak;
    @SerializedName("KibleSaati")
    @Expose
    private String kibleSaati;
    @SerializedName("MiladiTarihKisa")
    @Expose
    private String miladiTarihKisa;
    @SerializedName("MiladiTarihKisaIso8601")
    @Expose
    private String miladiTarihKisaIso8601;
    @SerializedName("MiladiTarihUzun")
    @Expose
    private String miladiTarihUzun;
    @SerializedName("MiladiTarihUzunIso8601")
    @Expose
    private String miladiTarihUzunIso8601;
    @SerializedName("Ogle")
    @Expose
    private String ogle;
    @SerializedName("Yatsi")
    @Expose
    private String yatsi;
    private String name;
    @SerializedName("yarin")
    @Expose
    private TimesOfDay toMorrow;
    @SerializedName("dun")
    @Expose
    private TimesOfDay yesterDay;

    public final static Parcelable.Creator<TimesOfDay> CREATOR = new Creator<TimesOfDay>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TimesOfDay createFromParcel(Parcel in) {
            return new TimesOfDay(in);
        }

        public TimesOfDay[] newArray(int size) {
            return (new TimesOfDay[size]);
        }

    };

    public TimesOfDay(String imsak, String gunes, String ogle, String ikindi, String aksam, String yatsi) {
        this.aksam = aksam;
        this.gunes = gunes;
        this.ikindi = ikindi;
        this.imsak = imsak;
        this.ogle = ogle;
        this.yatsi = yatsi;
    }

    protected TimesOfDay(Parcel in) {
        this.aksam = ((String) in.readValue((String.class.getClassLoader())));
        this.ayinSekliURL = ((String) in.readValue((String.class.getClassLoader())));
        this.gunes = ((String) in.readValue((String.class.getClassLoader())));
        this.gunesBatis = ((String) in.readValue((String.class.getClassLoader())));
        this.gunesDogus = ((String) in.readValue((String.class.getClassLoader())));
        this.hicriTarihKisa = ((String) in.readValue((String.class.getClassLoader())));
        this.hicriTarihUzun = ((String) in.readValue((String.class.getClassLoader())));
        this.ikindi = ((String) in.readValue((String.class.getClassLoader())));
        this.imsak = ((String) in.readValue((String.class.getClassLoader())));
        this.kibleSaati = ((String) in.readValue((String.class.getClassLoader())));
        this.miladiTarihKisa = ((String) in.readValue((String.class.getClassLoader())));
        this.miladiTarihKisaIso8601 = ((String) in.readValue((String.class.getClassLoader())));
        this.miladiTarihUzun = ((String) in.readValue((String.class.getClassLoader())));
        this.miladiTarihUzunIso8601 = ((String) in.readValue((String.class.getClassLoader())));
        this.ogle = ((String) in.readValue((String.class.getClassLoader())));
        this.yatsi = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TimesOfDay() {
    }

    public String getAksam() {
        return aksam;
    }

    public void setAksam(String aksam) {
        this.aksam = aksam;
    }

    public String getAyinSekliURL() {
        return ayinSekliURL;
    }

    public void setAyinSekliURL(String ayinSekliURL) {
        this.ayinSekliURL = ayinSekliURL;
    }

    public String getGunes() {
        return gunes;
    }

    public void setGunes(String gunes) {
        this.gunes = gunes;
    }

    public String getGunesBatis() {
        return gunesBatis;
    }

    public void setGunesBatis(String gunesBatis) {
        this.gunesBatis = gunesBatis;
    }

    public String getGunesDogus() {
        return gunesDogus;
    }

    public void setGunesDogus(String gunesDogus) {
        this.gunesDogus = gunesDogus;
    }

    public String getHicriTarihKisa() {
        return hicriTarihKisa;
    }

    public void setHicriTarihKisa(String hicriTarihKisa) {
        this.hicriTarihKisa = hicriTarihKisa;
    }

    public String getHicriTarihUzun() {
        return hicriTarihUzun;
    }

    public void setHicriTarihUzun(String hicriTarihUzun) {
        this.hicriTarihUzun = hicriTarihUzun;
    }

    public String getIkindi() {
        return ikindi;
    }

    public void setIkindi(String ikindi) {
        this.ikindi = ikindi;
    }

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getKibleSaati() {
        return kibleSaati;
    }

    public void setKibleSaati(String kibleSaati) {
        this.kibleSaati = kibleSaati;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TimesOfDay getToMorrow() {
        return toMorrow;
    }

    public void setToMorrow(TimesOfDay toMorrow) {
        this.toMorrow = toMorrow;
    }

    public TimesOfDay getYesterDay() {
        return yesterDay;
    }

    public void setYesterDay(TimesOfDay yesterDay) {
        this.yesterDay = yesterDay;
    }

    public String getMiladiTarihKisa() {
        return miladiTarihKisa;
    }

    public void setMiladiTarihKisa(String miladiTarihKisa) {
        this.miladiTarihKisa = miladiTarihKisa;
    }

    public String getMiladiTarihKisaIso8601() {
        return miladiTarihKisaIso8601;
    }

    public void setMiladiTarihKisaIso8601(String miladiTarihKisaIso8601) {
        this.miladiTarihKisaIso8601 = miladiTarihKisaIso8601;
    }

    public String getMiladiTarihUzun() {
        return miladiTarihUzun;
    }

    public void setMiladiTarihUzun(String miladiTarihUzun) {
        this.miladiTarihUzun = miladiTarihUzun;
    }

    public String getMiladiTarihUzunIso8601() {
        return miladiTarihUzunIso8601;
    }

    public void setMiladiTarihUzunIso8601(String miladiTarihUzunIso8601) {
        this.miladiTarihUzunIso8601 = miladiTarihUzunIso8601;
    }

    public String getOgle() {
        return ogle;
    }

    public void setOgle(String ogle) {
        this.ogle = ogle;
    }

    public String getYatsi() {
        return yatsi;
    }

    public void setYatsi(String yatsi) {
        this.yatsi = yatsi;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(aksam);
        dest.writeValue(ayinSekliURL);
        dest.writeValue(gunes);
        dest.writeValue(gunesBatis);
        dest.writeValue(gunesDogus);
        dest.writeValue(hicriTarihKisa);
        dest.writeValue(hicriTarihUzun);
        dest.writeValue(ikindi);
        dest.writeValue(imsak);
        dest.writeValue(kibleSaati);
        dest.writeValue(miladiTarihKisa);
        dest.writeValue(miladiTarihKisaIso8601);
        dest.writeValue(miladiTarihUzun);
        dest.writeValue(miladiTarihUzunIso8601);
        dest.writeValue(ogle);
        dest.writeValue(yatsi);
    }

    public int describeContents() {
        return 0;
    }

    public boolean isOld() {
        try {
            Date myDay = dateFormat.parse(miladiTarihKisa);
            Calendar calendar, myCalendar = Calendar.getInstance();
            myCalendar.setTime(myDay);
            calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_YEAR) > myCalendar.get(Calendar.DAY_OF_YEAR)) {
                return true;
            } else return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public String getGun() {
        DateFormat dayFormat = new SimpleDateFormat("EEEE");
        try {
            Date myDay = dateFormat.parse(miladiTarihKisa);
            Calendar calendar, myCalendar = Calendar.getInstance();
            myCalendar.setTime(myDay);
            calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_YEAR) == myCalendar.get(Calendar.DAY_OF_YEAR)) {
                return "Umumi";
            } else return dayFormat.format(myCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getKalan() {
        Calendar now = Calendar.getInstance();
        Calendar nextTime = getNextTime(now);
        Util.log(nextTime.toString());
        String st,dk,sn;
        long min,hr,kalansn=(nextTime.getTimeInMillis()-System.currentTimeMillis());
        kalansn=(kalansn-(kalansn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
        min=(kalansn-(kalansn % 60))/60;
        kalansn=kalansn % 60;
        hr=(min-(min%60))/60;
        min=min%60;
        st=hr<10?"0"+hr:String.valueOf(hr);
        dk=min<10?"0"+min:String.valueOf(min);
        sn=kalansn<10?"0"+kalansn:String.valueOf(kalansn);
        return String.format("%s:%s:%s",st,dk,sn);
    }
    public String getKalanWOsec() {
        return getKalan().substring(0,5);
    }

    public Calendar getNextTime(Calendar now) {
        Calendar nextTime=Calendar.getInstance();
        String [] hr_min;
        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 0:
                    hr_min=getImsak().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 1:
                    hr_min=getGunes().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 2:
                    hr_min=getOgle().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 3:
                    hr_min=getIkindi().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 4:
                    hr_min=getAksam().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 5:
                    hr_min=getYatsi().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
                    continue;
                case 6:
                    try {
                        nextTime.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hr_min=toMorrow.getImsak().split(":");
                    nextTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
                    nextTime.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
                    nextTime.set(Calendar.SECOND,0);
                    nextTime.set(Calendar.MILLISECOND,0);
                    if(now.before(nextTime))return nextTime;
            }
        }
        return null;
    }

    public boolean isEveningNight(){
        Calendar aksam=Calendar.getInstance();
        Calendar now=Calendar.getInstance();
        Calendar midNight=Calendar.getInstance();
        aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
        aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
        aksam.set(Calendar.SECOND,0);
        aksam.set(Calendar.MILLISECOND,0);

        midNight.set(Calendar.HOUR_OF_DAY,23);
        midNight.set(Calendar.MINUTE,59);
        midNight.set(Calendar.SECOND,59);
        midNight.set(Calendar.MILLISECOND,999);
        return aksam.before(now)&&now.before(midNight);
    }

    public String getEzaniSaat() {
        Calendar oldTime = Calendar.getInstance();
        try {
            oldTime.setTime(dateFormat.parse(getMiladiTarihKisa()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now=Calendar.getInstance();
        if(now.get(Calendar.YEAR)==oldTime.get(Calendar.YEAR)&&now.get(Calendar.DAY_OF_YEAR)==oldTime.get(Calendar.DAY_OF_YEAR)&&!isEveningNight()){
            oldTime.setTime(new Date(oldTime.getTimeInMillis() - ONE_DAY_MILLIS));
        }
        oldTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
        oldTime.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
        oldTime.set(Calendar.SECOND,0);
        oldTime.set(Calendar.MILLISECOND,0);
        String st,dk,sn;
        long min,hr,gecenSn=(System.currentTimeMillis()-oldTime.getTimeInMillis());
        gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
        min=(gecenSn-(gecenSn % 60))/60;
        gecenSn=gecenSn % 60;
        hr=(min-(min%60))/60;
        min=min%60;
        st=hr<10?"0"+hr:String.valueOf(hr);
        dk=min<10?"0"+min:String.valueOf(min);
        sn=gecenSn<10?"0"+gecenSn:String.valueOf(gecenSn);
        return String.format("%s:%s:%s",st,dk,sn);
    }
    public String getEzaniSaatWithoutSec(){
        return getEzaniSaat().substring(0,5);
    }

    public String getYatsiEzani(){
        Calendar aksam=Calendar.getInstance();
        Calendar yatsi=Calendar.getInstance();
        if(isEveningNight()){
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            yatsi.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getYatsi().split(":")[0]));
            yatsi.set(Calendar.MINUTE,Integer.parseInt(getYatsi().split(":")[1]));
            yatsi.set(Calendar.SECOND,0);
            yatsi.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(yatsi.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
                yatsi.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            yatsi.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getYatsi().split(":")[0]));
            yatsi.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getYatsi().split(":")[1]));
            yatsi.set(Calendar.SECOND,0);
            yatsi.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(yatsi.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }
    }
    public String getImsakEzani(){
        Calendar aksam=Calendar.getInstance();
        Calendar imsak=Calendar.getInstance();
        if(isEveningNight()){
            try {
                imsak.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            imsak.set(Calendar.HOUR_OF_DAY,Integer.parseInt(toMorrow.getImsak().split(":")[0]));
            imsak.set(Calendar.MINUTE,Integer.parseInt(toMorrow.getImsak().split(":")[1]));
            imsak.set(Calendar.SECOND,0);
            imsak.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(imsak.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            imsak.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getImsak().split(":")[0]));
            imsak.set(Calendar.MINUTE,Integer.parseInt(getImsak().split(":")[1]));
            imsak.set(Calendar.SECOND,0);
            imsak.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(imsak.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }
    }
    public String getGunesEzani(){
        Calendar aksam=Calendar.getInstance();
        Calendar gunes=Calendar.getInstance();
        if(isEveningNight()){
            try {
                gunes.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            gunes.set(Calendar.HOUR_OF_DAY,Integer.parseInt(toMorrow.getGunes().split(":")[0]));
            gunes.set(Calendar.MINUTE,Integer.parseInt(toMorrow.getGunes().split(":")[1]));
            gunes.set(Calendar.SECOND,0);
            gunes.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(gunes.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            gunes.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getGunes().split(":")[0]));
            gunes.set(Calendar.MINUTE,Integer.parseInt(getGunes().split(":")[1]));
            gunes.set(Calendar.SECOND,0);
            gunes.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(gunes.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }
    }
    public String getOgleEzani(){
        Calendar aksam=Calendar.getInstance();
        Calendar ogle=Calendar.getInstance();
        if(isEveningNight()){
            try {
                ogle.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            ogle.set(Calendar.HOUR_OF_DAY,Integer.parseInt(toMorrow.getOgle().split(":")[0]));
            ogle.set(Calendar.MINUTE,Integer.parseInt(toMorrow.getOgle().split(":")[1]));
            ogle.set(Calendar.SECOND,0);
            ogle.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(ogle.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            ogle.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getOgle().split(":")[0]));
            ogle.set(Calendar.MINUTE,Integer.parseInt(getOgle().split(":")[1]));
            ogle.set(Calendar.SECOND,0);
            ogle.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(ogle.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }
    }
    public String getIkindiEzani(){
        Calendar aksam=Calendar.getInstance();
        Calendar ogle=Calendar.getInstance();
        if(isEveningNight()){
            try {
                ogle.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            ogle.set(Calendar.HOUR_OF_DAY,Integer.parseInt(toMorrow.getIkindi().split(":")[0]));
            ogle.set(Calendar.MINUTE,Integer.parseInt(toMorrow.getIkindi().split(":")[1]));
            ogle.set(Calendar.SECOND,0);
            ogle.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(ogle.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }else {
            try {
                aksam.setTime(dateFormat.parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            aksam.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yesterDay.getAksam().split(":")[0]));
            aksam.set(Calendar.MINUTE,Integer.parseInt(yesterDay.getAksam().split(":")[1]));
            aksam.set(Calendar.SECOND,0);
            aksam.set(Calendar.MILLISECOND,0);

            ogle.set(Calendar.HOUR_OF_DAY,Integer.parseInt(getIkindi().split(":")[0]));
            ogle.set(Calendar.MINUTE,Integer.parseInt(getIkindi().split(":")[1]));
            ogle.set(Calendar.SECOND,0);
            ogle.set(Calendar.MILLISECOND,0);

            String st,dk;
            long min,hr,gecenSn=(ogle.getTimeInMillis()-aksam.getTimeInMillis());
            gecenSn=(gecenSn-(gecenSn % 1000))/1000;//milisaniyeyi saniyeye çevirdik-total
            min=(gecenSn-(gecenSn % 60))/60;
            hr=(min-(min%60))/60;
            min=min%60;
            st=hr<10?"0"+hr:String.valueOf(hr);
            dk=min<10?"0"+min:String.valueOf(min);
            return String.format("%s:%s",st,dk);

        }
    }

    public void setDateToYesterDay() {
        setMiladiTarihKisa(dateFormat.format(new Date(System.currentTimeMillis()-ONE_DAY_MILLIS)));
    }

    @Override
    public boolean equals(Object obj) {
        try{
            return ((TimesOfDay)obj).getMiladiTarihKisa().equals(miladiTarihKisa);
        }catch (Exception e){

        }
        return false;
    }

    public boolean isYatsiGecti() {
        Calendar yatsi=Calendar.getInstance();
        Calendar now=Calendar.getInstance();
        String[] hr_min=getYatsi().split(":");
        yatsi.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hr_min[0]));
        yatsi.set(Calendar.MINUTE,Integer.parseInt(hr_min[1]));
        yatsi.set(Calendar.SECOND,0);
        yatsi.set(Calendar.MILLISECOND,0);
        if(now.after(yatsi))return true;
        return false;
    }
}