package com.sahnisemanyazilim.ezanisaat.services

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.BuildConfig
import com.sahnisemanyazilim.ezanisaat.model.Town
import com.sahnisemanyazilim.ezanisaat.widget.EzaniBigWidget
import java.util.concurrent.TimeUnit

class BigWidgetService
/**
 * @param appContext   The application [Context]
 * @param workerParams Parameters to setup the internal state of this worker
 */
    (appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    companion object {
        const val TAG = "BigWidgetService"

        fun disableWork(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(TAG)
        }
        fun scheduleWork(context: Context) {
            val constraints: Constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
            val workManager = WorkManager.getInstance(context)
            val workRequestBuilder =
                OneTimeWorkRequest.Builder(BigWidgetService::class.java)
                    .setConstraints(constraints)
                    .setInitialDelay(1, TimeUnit.MINUTES)
            val workRequest = workRequestBuilder.build()
            workManager.enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }

    private val updatingTimes: MutableList<Town>? = ArrayList()
    private var notificationManager: NotificationManagerCompat? = null


    override fun doWork(): Result {
        //TODO ASDA
        Util.log("job started@$TAG")

        val appWidgetManager = AppWidgetManager.getInstance(
            this.applicationContext
        )
        appWidgetManager.getAppWidgetIds(
            ComponentName(
                applicationContext,
                EzaniBigWidget::class.java
            )
        )?.let {
            EzaniBigWidget().onUpdate(applicationContext, appWidgetManager, it)
        }
        scheduleWork(applicationContext)
        return Result.success()
    }
}