package com.example.ilt3_demo.ui.detail

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.ilt3_demo.api.RestoApiConfig
import com.google.common.util.concurrent.ListenableFuture

class ReviewPostWorker(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(
    appContext, workerParameters
) {
    override suspend fun doWork(): Result {
        val restoId = inputData.getString(RESTAURANT_ID)
        val restoName = inputData.getString(RESTAURANT_NAME)
        val restoContent = inputData.getString(RESTAURANT_CONTENT)

        if(restoId == null || restoName == null || restoContent == null)
            return Result.failure()
        return try {
            val retrofitApi = RestoApiConfig.getRestaurantApi()
            retrofitApi.addRestaurantReview(restoId, restoName, restoContent)

            Result.success(inputData)
        }catch (e: Exception){
            Result.failure()
        }

    }

    companion object {
        const val RESTAURANT_ID = "RESTAURANT_ID"
        const val RESTAURANT_NAME = "RESTAURANT_NAME"
        const val RESTAURANT_CONTENT = "RESTAURANT_DATA"
    }
}