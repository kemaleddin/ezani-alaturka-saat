package com.sahnisemanyazilim.ezanisaat.services

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class DelayedWidgetWorker(
    appContext: Context,
    workerParams: WorkerParameters,
): Worker(appContext, workerParams) {

    companion object{
        const val TAG = "EzaniAppWidgetWorkerKeepEnabled"
        fun cancel(context:Context){
            WorkManager.getInstance(context).cancelUniqueWork(TAG)
        }
        fun schedule(context:Context){
            WorkManager.getInstance(context).enqueueUniqueWork(
                TAG,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequestBuilder<DelayedWidgetWorker>()
                    .setInitialDelay(10 * 365, TimeUnit.DAYS)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiresCharging(true)
                            .build()
                    )
                    .build()
            )
        }
    }

    override fun doWork(): Result {
        return Result.success()
    }
}