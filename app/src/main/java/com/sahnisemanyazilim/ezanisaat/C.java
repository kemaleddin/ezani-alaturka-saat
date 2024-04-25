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
            KEY_PREF_FAJR = "imsakVakti",
            KEY_PREF_SUNRISE = "gunDogumu",
            KEY_PREF_SUNRISE_KERAHET = "gunDogumuKerahetCikmasi",
            KEY_PREF_NOON = "ogleVakti",
            KEY_PREF_ASR = "ikindiVakti",
            KEY_PREF_ASR_KERAHET = "ikindiKerahetVakti",
            KEY_PREF_SUNSET = "aksamVakti",
            KEY_PREF_ISHA = "yatsiVakti",
            KEY_PREF_BEFORE_FAJR = "imsaktanOnce",
            KEY_PREF_BEFORE_SUNRISE = "gunestenOnce",
            KEY_PREF_BEFORE_NOON = "ogledenOnce",
            KEY_PREF_BEFORE_ASR = "ikindidenOnce",
            KEY_PREF_BEFORE_ASR_KERAHET = "ikindiKerahetOnce",
            KEY_PREF_BEFORE_SUNSET = "aksamdanOnce",
            KEY_PREF_BEFORE_ISHA = "yatsidanOnce",
            KEY_TOWN = "town",
            KEY_CONTENT_STR_ID = "CONTENT_STR_ID",
            KEY_REMAINING_TIME = "REMAINING_TIME",
            KEY_PREF_KEY = "PREF_KEY",
            KEY_TIME_TO_IMSAK = "time_to_imsak",
            KEY_TIME_ENUM = "time_enum",
            KEY_TIME_TO_GUNES = "time_to_gunes",
            KEY_TIME_TO_OGLE = "time_to_ogle",
            KEY_TIME_TO_IKINDI = "time_to_ikindi",
            KEY_TIME_TO_IKINDI_KERAHET = "time_to_ikindi_kerahet",
            KEY_TIME_TO_AKSAM = "time_to_aksam",
            KEY_TIME_TO_YATSI = "time_to_yatsi";
    public static long interval_1 = 30000;
    public static DateFormat DateFormat_ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat DateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static DateFormat SimpleTimeFormat = new SimpleDateFormat("HH:mm");
}
