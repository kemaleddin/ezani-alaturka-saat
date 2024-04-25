package com.sahnisemanyazilim.ezanisaat.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.StringRes
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.BuildConfig
import com.sahnisemanyazilim.ezanisaat.C
import com.sahnisemanyazilim.ezanisaat.R
import com.sahnisemanyazilim.ezanisaat.enums.TimeEnum
import com.sahnisemanyazilim.ezanisaat.extensions.getDefaultTown
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * Written by "كمال الدّين صارغين"  on 22.04.2024.
 * و من الله توفیق
 */
class AlarmHelper {
    companion object {
        fun setExactAlarmFor(
            context: Context,
            key:String,
            @StringRes notificationContent: Int,
            timeEnum: TimeEnum,
            status: Boolean = false,
            before: Long = 0,
        ) {
            val town = context.getDefaultTown() ?: return
            var time = town.getNearestTime(timeEnum, -before).time
            val requestCode = notificationContent
            val alarmMgr: AlarmManager? =
                (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?)
            alarmMgr?.let {
                val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
                intent.putExtra(C.KEY_CONTENT_STR_ID, notificationContent)
                intent.putExtra(C.KEY_REMAINING_TIME,TimeUnit.MILLISECONDS.toMinutes(before).toInt())
                intent.putExtra(C.KEY_PREF_KEY,key)
                val alarmIntent = PendingIntent.getBroadcast(
                    context.applicationContext,
                    requestCode,
                    intent,
                    FLAG_ONE_SHOT or FLAG_IMMUTABLE
                )
                var canSetExact = false
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                    canSetExact = it.canScheduleExactAlarms()
                }
                if (!status) {
                    it.cancel(alarmIntent)
                    return
                }
                if(BuildConfig.DEBUG){
                    val date = Calendar.getInstance()
                    date.add(Calendar.MINUTE,1)
                    time=date.time.time
                }
                if (canSetExact) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        it.setExactAndAllowWhileIdle(AlarmManager.RTC, time, alarmIntent)
                    else
                        it.setExact(AlarmManager.RTC, time, alarmIntent)
                } else {
                    it.set(AlarmManager.RTC, time, alarmIntent)
                }
            }

        }

        fun setAlarms(context: Context,sharedPreferences:SharedPreferences, key: String) {
            var status = false
            var contentId = R.string.app_name
            var timeEnum = TimeEnum.IMSAK
            var before = 0L
            var beforeKey:String?=null
            when (key) {
                C.KEY_PREF_NOTIFICATION_BAR -> {

                }

                C.KEY_PREF_FAJR -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.imsak_vakti_girdi
                    timeEnum = TimeEnum.IMSAK
                }

                C.KEY_PREF_SUNRISE -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.gunes_dogdu
                    timeEnum = TimeEnum.GUNES
                }

                C.KEY_PREF_SUNRISE_KERAHET -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.gun_dogumu_kerahet_cikti
                    timeEnum = TimeEnum.GUN_DOG_KERAHET
                }

                C.KEY_PREF_NOON -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.ogle_vakti_girdi
                    timeEnum = TimeEnum.OGLE                }

                C.KEY_PREF_ASR -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.ikindi_vakti_girdi
                    timeEnum = TimeEnum.IKINDI
                }

                C.KEY_PREF_ASR_KERAHET -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.ikindi_kerahet_girdi
                    timeEnum = TimeEnum.GUN_BAT_KERAHET
                }

                C.KEY_PREF_SUNSET -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.aksam_vakti_girdi
                    timeEnum = TimeEnum.AKSAM
                }

                C.KEY_PREF_ISHA -> {
                    status = sharedPreferences.getBoolean(key, false)
                    contentId = R.string.yatsi_vakti_girdi
                    timeEnum = TimeEnum.YATSI
                }

                C.KEY_PREF_BEFORE_FAJR, C.KEY_TIME_TO_IMSAK -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_FAJR, false)
                    beforeKey = C.KEY_TIME_TO_IMSAK
                    contentId = R.string.imsak_vaktine_kaldi
                    timeEnum = TimeEnum.IMSAK

                }

                C.KEY_PREF_BEFORE_SUNRISE, C.KEY_TIME_TO_GUNES -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_SUNRISE, false)
                    beforeKey = C.KEY_TIME_TO_GUNES
                    contentId = R.string.gun_dogumu_vaktine_kaldi
                    timeEnum = TimeEnum.GUNES
                }

                C.KEY_PREF_BEFORE_NOON, C.KEY_TIME_TO_OGLE -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_NOON, false)
                    beforeKey = C.KEY_TIME_TO_OGLE
                    contentId = R.string.ogle_vaktine_kaldi
                    timeEnum = TimeEnum.OGLE

                }

                C.KEY_PREF_BEFORE_ASR, C.KEY_TIME_TO_IKINDI -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_ASR, false)
                    beforeKey = C.KEY_TIME_TO_IKINDI
                    contentId = R.string.ikindi_vaktine_kaldi
                    timeEnum = TimeEnum.IKINDI
                }

                C.KEY_PREF_BEFORE_ASR_KERAHET, C.KEY_TIME_TO_IKINDI_KERAHET -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_ASR_KERAHET, false)
                    beforeKey = C.KEY_TIME_TO_IKINDI_KERAHET

                    contentId = R.string.ikindi_kerahet_vaktine_kaldi
                    timeEnum = TimeEnum.GUN_BAT_KERAHET
                }

                C.KEY_PREF_BEFORE_SUNSET, C.KEY_TIME_TO_AKSAM -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_SUNSET, false)
                    before = sharedPreferences.getInt(
                            C.KEY_TIME_TO_AKSAM,
                            15
                        )!!.toLong()
                    contentId = R.string.aksam_vaktine_kaldi
                    timeEnum = TimeEnum.AKSAM
                }

                C.KEY_PREF_BEFORE_ISHA, C.KEY_TIME_TO_YATSI -> {
                    status = sharedPreferences.getBoolean(C.KEY_PREF_BEFORE_ISHA, false)
                    before = sharedPreferences.getInt(
                            C.KEY_TIME_TO_YATSI,
                            15
                        )!!.toLong()
                    contentId = R.string.yatsi_vaktine_kaldi
                    timeEnum = TimeEnum.YATSI
                }

            }
            beforeKey?.let {
                before=sharedPreferences.getString(it,"15")!!.toLong()
            }
            if(before>0)
                before-=1
            before=TimeUnit.MINUTES.toMillis(before)
            setExactAlarmFor(context,key,contentId,timeEnum,status,before)
        }

    }
}