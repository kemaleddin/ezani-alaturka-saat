package com.sahnisemanyazilim.ezanisaat.services

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListenableFutureTask
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.BuildConfig
import com.sahnisemanyazilim.ezanisaat.model.Town
import com.sahnisemanyazilim.ezanisaat.widget.EzaniBigWidget
import com.sahnisemanyazilim.ezanisaat.widget.EzaniSaatWidget
import java.util.concurrent.TimeUnit

class SaatWidgetService
/**
 * @param appContext   The application [Context]
 * @param workerParams Parameters to setup the internal state of this worker
 */
    (appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val updatingTimes: MutableList<Town>? = ArrayList()
    private var notificationManager: NotificationManagerCompat? = null

    companion object {
        const val TAG = "SaatWidgetService"

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
                OneTimeWorkRequest.Builder(SaatWidgetService::class.java)
                    .setConstraints(constraints)
                    .setInitialDelay(1, TimeUnit.MINUTES)
            val workRequest = workRequestBuilder.build()
            workManager.enqueueUniqueWork(SaatWidgetService.TAG, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }

    override fun doWork(): Result {

        Util.log("job started@$TAG")

        val appWidgetManager = AppWidgetManager.getInstance(
            this.applicationContext
        )
        appWidgetManager.getAppWidgetIds(
            ComponentName(
                applicationContext,
                EzaniSaatWidget::class.java
            )
        )?.let {
            EzaniSaatWidget().onUpdate(applicationContext, appWidgetManager, it)
        }
        return Result.success()
    }
}