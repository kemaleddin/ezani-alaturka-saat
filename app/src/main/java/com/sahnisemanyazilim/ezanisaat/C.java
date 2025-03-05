package com.sahnisemanyazilim.ezanisaat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Written by "كمال الدّين صارغين"  on 9.03.2018.
 * و من الله توفیق
 */

public class C {

    public static final String
            KEY_LOCATIONS = "locations",
            KEY_ACTIVE = "active",
            KEY_PREF_NOTIFICATION_BAR = "notificationBar",
            KEY_PREF_NOTIFICATIONS = "notifications",
            KEY_PREF_FAJR = "imsak_vakti",
            KEY_PREF_SUNRISE = "gun_dogumu",
            KEY_PREF_SUNRISE_KERAHET = "gun_dogumu_kerahet_cikmasi",
            KEY_PREF_NOON = "ogle_vakti",
            KEY_PREF_ASR = "ikindi_vakti",
            KEY_PREF_ASR_KERAHET = "ikindi_kerahet_vakti",
            KEY_PREF_SUNSET = "aksam_vakti",
            KEY_PREF_ISHA = "yatsi_vakti",
            KEY_PREF_BEFORE_FAJR = "imsaktan_once",
            KEY_PREF_BEFORE_SUNRISE = "gunesten_once",
            KEY_PREF_BEFORE_NOON = "ogleden_once",
            KEY_PREF_BEFORE_ASR = "ikindiden_once",
            KEY_PREF_BEFORE_ASR_KERAHET = "ikindi_kerahet_once",
            KEY_PREF_BEFORE_SUNSET = "aksamdan_once",
            KEY_PREF_BEFORE_ISHA = "yatsidan_once",
            KEY_TOWN = "town",
            KEY_CONTENT_STR_ID = "CONTENT_STR_ID",
            KEY_REMAINING_TIME = "REMAINING_TIME",
            KEY_PREF_KEY = "PREF_KEY",
            KEY_TIME_TO_IMSAK = "time_to__imsak",
            KEY_TIME_ENUM = "time_enum",
            KEY_TIME_TO_GUNES = "time_to__gunes",
            KEY_TIME_TO_OGLE = "time_to__ogle",
            KEY_TIME_TO_IKINDI = "time_to__ikindi",
            KEY_TIME_TO_IKINDI_KERAHET = "time_to__ikindi_kerahet",
            KEY_TIME_TO_AKSAM = "time_to__aksam",
            KEY_TIME_TO_YATSI = "time_to__yatsi";
    public static long interval_1 = 30000;
    public static DateFormat DateFormat_ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat DateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static DateFormat SimpleTimeFormat = new SimpleDateFormat("HH:mm");
}
