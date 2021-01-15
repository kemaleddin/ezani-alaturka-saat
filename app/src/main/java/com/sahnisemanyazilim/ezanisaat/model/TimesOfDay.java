package com.sahnisemanyazilim.ezanisaat.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sahnisemanyazilim.ezanisaat.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.HijrahDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressLint("SimpleDateFormat")
public class TimesOfDay implements Parcelable {
    public static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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

    public TimesOfDay(String miladiTarihKisa) {
        this.miladiTarihKisa = miladiTarihKisa;
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
        return getHicriTarih();
    }

    public String getHicriTarihUzunGunlu() {
        return String.format("%s %s", getHicriTarih(), getGun_());
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
            Calendar now=Calendar.getInstance(), thisDay = Calendar.getInstance();
            thisDay.setTime(myDay);
            now.set(Calendar.HOUR_OF_DAY,0);
            now.set(Calendar.MINUTE,0);
            now.set(Calendar.SECOND,0);
            now.set(Calendar.MILLISECOND,0);
            thisDay.set(Calendar.HOUR_OF_DAY,0);
            thisDay.set(Calendar.MINUTE,0);
            thisDay.set(Calendar.SECOND,0);
            thisDay.set(Calendar.MILLISECOND,0);
            return thisDay.before(now);
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

    public String getGun_() {
        DateFormat dayFormat = new SimpleDateFormat("EEEE");
        try {
            Date myDay = dateFormat.parse(miladiTarihKisa);
            Calendar calendar, myCalendar = Calendar.getInstance();
            myCalendar.setTime(myDay);
            calendar = Calendar.getInstance();
            return dayFormat.format(myCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    @SuppressLint("DefaultLocale")
    public static String getSaat12(String time){
        int hr=(Integer.parseInt(time.substring(0,2)) % 12);
        return String.format("%s%d%s", hr < 10 ? "0" : "", hr, time.substring(2, 5));
    }
    private String getSureFormatted(long ms){
        String st, dk, sn;
        long min, hr, kalansn = TimeUnit.SECONDS.convert(ms, TimeUnit.MILLISECONDS);
        //kalansn = (kalansn - (kalansn % 1000)) / 1000;//milisaniyeyi saniyeye çevirdik-total
        min = (kalansn - (kalansn % 60)) / 60;
        kalansn = kalansn % 60;
        hr = (min - (min % 60)) / 60;
        min = min % 60;
        st = hr < 10 ? "0" + hr : String.valueOf(hr);
        dk = min < 10 ? "0" + min : String.valueOf(min);
        sn = kalansn < 10 ? "0" + kalansn : String.valueOf(kalansn);
        return String.format("%s:%s:%s", st, dk, sn);
    }

    public String getKalan() {
        Calendar now = Calendar.getInstance();
        Calendar nextTime = getNextTime(now);
        return getSureFormatted(nextTime.getTimeInMillis() - System.currentTimeMillis());
    }

    public String getKalanWOsec() {
        return getKalan().substring(0, 5);
    }

    public Calendar getNextTime(Calendar now) {
        Calendar nextTime = Calendar.getInstance();
        String[] hr_min;
        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 0:
                    hr_min = getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 1:
                    hr_min = getGunes().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 2:
                    hr_min = getOgle().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 3:
                    hr_min = getIkindi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 4:
                    hr_min = getAksam().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 5:
                    hr_min = getYatsi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
                    continue;
                case 6:
                    try {
                        nextTime.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hr_min = toMorrow.getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return nextTime;
            }
        }
        return null;
    }

    private void setHrMin(Calendar nextTime, String[] hr_min) {
        nextTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hr_min[0]));
        nextTime.set(Calendar.MINUTE, Integer.parseInt(hr_min[1]));
        nextTime.set(Calendar.SECOND, 0);
        nextTime.set(Calendar.MILLISECOND, 0);
    }

    public int[] getNextIds() {
        Calendar now = Calendar.getInstance();
        Calendar nextTime = Calendar.getInstance();
        String[] hr_min;
        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 0:
                    hr_min = getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_imsak,
                            R.id.text_e_tit_imsak,
                            R.id.text_u_imsak,
                            R.id.text_u_tit_imsak};
                    continue;
                case 1:
                    hr_min = getGunes().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_gunes,
                            R.id.text_e_tit_gunes,
                            R.id.text_u_gunes,
                            R.id.text_u_tit_gunes};
                    continue;
                case 2:
                    hr_min = getOgle().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_ogle,
                            R.id.text_e_tit_ogle,
                            R.id.text_u_ogle,
                            R.id.text_u_tit_ogle};
                    continue;
                case 3:
                    hr_min = getIkindi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_ikindi,
                            R.id.text_e_tit_ikindi,
                            R.id.text_u_ikindi,
                            R.id.text_u_tit_ikindi};
                    continue;
                case 4:
                    hr_min = getAksam().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_aksam,
                            R.id.text_e_tit_aksam,
                            R.id.text_u_aksam,
                            R.id.text_u_tit_aksam};
                    continue;
                case 5:
                    hr_min = getYatsi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_yatsi,
                            R.id.text_e_tit_yatsi,
                            R.id.text_u_yatsi,
                            R.id.text_u_tit_yatsi};
                    continue;
                case 6:
                    try {
                        nextTime.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hr_min = toMorrow.getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return new int[]{
                            R.id.text_e_imsak,
                            R.id.text_e_tit_imsak,
                            R.id.text_u_imsak,
                            R.id.text_u_tit_imsak};
            }
        }
        return null;
    }

    public int getNextId() {
        Calendar now = Calendar.getInstance();
        Calendar nextTime = Calendar.getInstance();
        String[] hr_min;
        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 0:
                    hr_min = getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_imsak;
                    continue;
                case 1:
                    hr_min = getGunes().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_gunes;
                    continue;
                case 2:
                    hr_min = getOgle().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_ogle;
                    continue;
                case 3:
                    hr_min = getIkindi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_ikindi;
                    continue;
                case 4:
                    hr_min = getAksam().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_aksam;
                    continue;
                case 5:
                    hr_min = getYatsi().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_yatsi;
                    continue;
                case 6:
                    try {
                        nextTime.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hr_min = toMorrow.getImsak().split(":");
                    setHrMin(nextTime, hr_min);
                    if (now.before(nextTime)) return R.id.text_imsak;
            }
        }
        return 0;
    }

    public boolean isEveningNight() {
        Calendar aksam = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        Calendar midNight = Calendar.getInstance();
        setHrMin(aksam,getAksam().split(":"));

        midNight.set(Calendar.HOUR_OF_DAY, 23);
        midNight.set(Calendar.MINUTE, 59);
        midNight.set(Calendar.SECOND, 59);
        midNight.set(Calendar.MILLISECOND, 999);
        return aksam.before(now) && now.before(midNight);
    }

    public String getEzaniSaat() {
        Calendar oldTime = Calendar.getInstance();
        try {
            oldTime.setTime(dateFormat.parse(getMiladiTarihKisa()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) == oldTime.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == oldTime.get(Calendar.DAY_OF_YEAR) && !isEveningNight()) {
            oldTime.setTime(new Date(oldTime.getTimeInMillis() - ONE_DAY_MILLIS));
        }
        setHrMin(oldTime,getAksam().split(":"));
        return getSureFormatted(System.currentTimeMillis() - oldTime.getTimeInMillis());
    }

    public String getEzaniSaatWithoutSec() {
        return getEzaniSaat().substring(0, 5);
    }

    public String getYatsiEzani() {
        Calendar aksam = Calendar.getInstance();
        Calendar yatsi = Calendar.getInstance();
        if (isEveningNight()) {
            setHrMin(aksam,getAksam().split(":"));
            setHrMin(yatsi,getYatsi().split(":"));
            return getSureFormatted(yatsi.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        } else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
                yatsi.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,yesterDay.getAksam().split(":"));
            setHrMin(yatsi,yesterDay.getYatsi().split(":"));
            return getSureFormatted(yatsi.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        }
    }

    public String getImsakEzani() {
        Calendar aksam = Calendar.getInstance();
        Calendar imsak = Calendar.getInstance();
        if (isEveningNight()) {
            try {
                imsak.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,getAksam().split(":"));
            setHrMin(imsak,toMorrow.getImsak().split(":"));
            return getSureFormatted(imsak.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        } else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,yesterDay.getAksam().split(":"));
            setHrMin(imsak,getImsak().split(":"));
            return getSureFormatted(imsak.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        }
    }

    public String getGunesEzani() {
        Calendar aksam = Calendar.getInstance();
        Calendar gunes = Calendar.getInstance();
        if (isEveningNight()) {
            try {
                gunes.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,getAksam().split(":"));
            setHrMin(gunes,toMorrow.getGunes().split(":"));
            return getSureFormatted (gunes.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        } else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,yesterDay.getAksam().split(":"));
            setHrMin(gunes,getGunes().split(":"));
            return getSureFormatted(gunes.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);

        }
    }

    public String getOgleEzani() {
        Calendar aksam = Calendar.getInstance();
        Calendar ogle = Calendar.getInstance();
        if (isEveningNight()) {
            try {
                ogle.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,getAksam().split(":"));
            setHrMin(ogle,toMorrow.getOgle().split(":"));
            return getSureFormatted(ogle.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        } else {
            try {
                aksam.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,yesterDay.getAksam().split(":"));
            setHrMin(ogle,getOgle().split(":"));
            return getSureFormatted(ogle.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        }
    }

    public String getIkindiEzani() {
        Calendar aksam = Calendar.getInstance();
        Calendar ogle = Calendar.getInstance();
        if (isEveningNight()) {
            try {
                ogle.setTime(dateFormat.parse(toMorrow.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,getAksam().split(":"));
            setHrMin(ogle,toMorrow.getIkindi().split(":"));
            return getSureFormatted(ogle.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);

        } else {
            try {
                aksam.setTime(dateFormat.parse(yesterDay.getMiladiTarihKisa()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setHrMin(aksam,yesterDay.getAksam().split(":"));
            setHrMin(ogle,getIkindi().split(":"));
            return getSureFormatted(ogle.getTimeInMillis() - aksam.getTimeInMillis()).substring(0,5);
        }
    }

    public void setDateToYesterDay() {
        setMiladiTarihKisa(dateFormat.format(new Date(System.currentTimeMillis() - ONE_DAY_MILLIS)));
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((TimesOfDay) obj).miladiTarihKisa.equals(miladiTarihKisa);
        } catch (Exception e) {

        }
        return false;
    }

    public boolean isYatsiGecti() {
        Calendar yatsi = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        String[] hr_min = getYatsi().split(":");
        setHrMin(yatsi,hr_min);
        if (now.after(yatsi)) return true;
        return false;
    }

    public String getChkImsak() {
        try {
            return isYatsiGecti() ? toMorrow.getImsak() : imsak;
        } catch (NumberFormatException e) {
            return imsak;
        }
    }

    public String getChkGunes() {
        try {
            return isYatsiGecti() ? toMorrow.getGunes() : gunes;
        } catch (NumberFormatException e) {
            return gunes;
        }
    }

    public String getChkOgle() {
        try {
            return isYatsiGecti() ? toMorrow.getOgle() : ogle;
        } catch (NumberFormatException e) {
            return ogle;
        }
    }

    public String getChkIkindi() {
        try {
            return isYatsiGecti() ? toMorrow.getIkindi() : ikindi;
        } catch (NumberFormatException e) {
            return ikindi;
        }
    }

    public String getChkAksam() {
        try {
            return isYatsiGecti() ? toMorrow.getAksam() : aksam;
        } catch (NumberFormatException e) {
            return aksam;
        }
    }

    public String getChkYatsi() {
        try {
            return isYatsiGecti() ? toMorrow.getYatsi() : yatsi;
        } catch (NumberFormatException e) {
            return yatsi;
        }
    }

    public static TimesOfDay getToDay() {
        TimesOfDay timesOfDay = new TimesOfDay();
        timesOfDay.miladiTarihKisa = dateFormat.format(Calendar.getInstance().getTime());
        return timesOfDay;
    }

    @SuppressLint("DefaultLocale")
    private String getHicriTarih(){
        String[] dt=hicriTarihKisa.split("\\.");
        int day= Integer.parseInt(dt[0]);
        int month= Integer.parseInt(dt[1]);
        int year= Integer.parseInt(dt[2]);
        String mnt;
        switch (month){
            case 1:
                mnt="Muharrem";
                break;
            case 2:
                mnt="Safer";
                break;
            case 3:
                mnt="Rebiülevvel";
                break;
            case 4:
                mnt="Rebiülahir";
                break;
            case 5:
                mnt="Cemaziyelevvel";
                break;
            case 6:
                mnt="Cemaziyelahir";
                break;
            case 7:
                mnt="Recep";
                break;
            case 8:
                mnt="Şaban";
                break;
            case 9:
                mnt="Ramazan";
                break;
            case 10:
                mnt="Şevval";
                break;
            case 11:
                mnt="Zilkade";
                break;
            default:
                mnt="Zilhicce";
        }
        return String.format("%d %s %d",day,mnt,year);
    }
}