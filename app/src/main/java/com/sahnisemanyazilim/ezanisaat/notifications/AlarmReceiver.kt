package com.sahnisemanyazilim.ezanisaat.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.C
import com.sahnisemanyazilim.ezanisaat.R
import com.sahnisemanyazilim.ezanisaat.enums.TimeEnum

/**
 * Written by "كمال الدّين صارغين"  on 22.04.2024.
 * و من الله توفیق
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Code to show notification
        val id = intent.getIntExtra(C.KEY_CONTENT_STR_ID, R.string.app_name)
        val data = intent.getIntExtra(C.KEY_REMAINING_TIME, 0)
        val key = intent.getStringExtra(C.KEY_PREF_KEY)?:C.KEY_PREF_FAJR
        val notificationsActive=Util.getPrefs(context).getBoolean(C.KEY_PREF_NOTIFICATIONS,false)
        if (id == R.string.app_name || !notificationsActive )
            return
        showNotification(context, id,data)
        AlarmHelper.setAlarms(context, Util.getPrefs(context),key)
    }

    private fun showNotification(context: Context, @StringRes contentStringId: Int,data:Int=0) {
        val channelId = context.getString(R.string.NotifyChannelId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.notificationChannels.any { it.id == channelId }) {
                val name = context.getString(R.string.NotifyChannelName)
                val descriptionText = context.getString(R.string.NotifyChannelDescription)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }
                notificationManager.createNotificationChannel(channel)
            }
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notify)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(if(data==0)context.getString(contentStringId)else context.getString(contentStringId,data.toString()))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManagerCompat.notify(contentStringId, builder.build())
        }
    }
}
