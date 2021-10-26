package com.example.workermanager.utils

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workermanager.classess.MoneyItem
import com.example.workermanager.dao.Databace
import com.example.workermanager.dao.MoneyInsert
import com.example.workermanager.networker.NetworkHelper
import com.example.workermanager.retrofit.ApiClient
import com.example.workermanager.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyWorker(var context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    lateinit var appDatabase: Databace
    lateinit var apiService: ApiService
    lateinit var networkHelper: NetworkHelper
    override fun doWork(): Result {
        apiService = ApiClient.apiService
        appDatabase = Databace.getInstance(context)
        networkHelper = NetworkHelper(context)
        if (networkHelper.isNetworkConnected()) {
            apiService.getUsers().enqueue(object : Callback<List<MoneyItem>> {
                override fun onResponse(
                    call: Call<List<MoneyItem>>,
                    response: Response<List<MoneyItem>>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.forEach {
                            val userEntity =
                                MoneyInsert(it.Ccy,
                                    it.CcyNm_UZ,
                                    it.Date,
                                    it.Diff,
                                    it.Rate,
                                    it.id,
                                    it.image,
                                    it.isliked
                                )
                            appDatabase.onlineDao().addUser(userEntity)
                            appDatabase.onlineDao().getAllUsers()
                        }
                    }
                }
                override fun onFailure(call: Call<List<MoneyItem>>, t: Throwable) {
                    Toast.makeText(context, "Malumot yuklanmadi", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return Result.success()
    }
}