package com.example.workermanager.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.workermanager.adapters.HomeAdapter
import com.example.workermanager.classess.MoneyItem
import com.example.workermanager.dao.Databace
import com.example.workermanager.dao.MoneyInsert
import com.example.workermanager.databinding.BottomsheetBinding
import com.example.workermanager.databinding.FragmentPagerBinding
import com.example.workermanager.networker.NetworkHelper
import com.example.workermanager.retrofit.ApiClient
import com.example.workermanager.retrofit.ApiService
import com.example.workermanager.utils.MyWorker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class Pager : Fragment() {

    lateinit var fragment: FragmentPagerBinding
    private lateinit var homeAdapter: HomeAdapter
    lateinit var databace: Databace
    lateinit var apiService: ApiService
    lateinit var networkHelper: NetworkHelper
    lateinit var userEntity: MoneyInsert
    private val TAG = "Pager"

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentPagerBinding.inflate(inflater, container, false)
        fragment.apply {
            databace = Databace.getInstance(requireContext())
            apiService = ApiClient.apiService
            networkHelper = NetworkHelper(requireContext())

            homeAdapter = HomeAdapter(requireContext(), object : HomeAdapter.OnitemCliked {
                override fun onCliked(money: MoneyInsert, position: Int) {
                    if (money.isliked == false) {
                        databace.onlineDao().like(money)
                    } else {
                        databace.onlineDao().like(money)
                    }
                }

                override fun cardClick(money: MoneyInsert, position: Int) {
                    val dialog = AlertDialog.Builder(requireContext())
                    val binding_dialog =
                        BottomsheetBinding.inflate(layoutInflater)
                    val create = dialog.create()
                    create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    create.setView(binding_dialog.root)
                    binding_dialog.apply {
                        name.text = money.CcyNm_UZ
                        xa.setOnClickListener {
                            var its = money.Rate
                            val sum = text.text.toString()
                            if (sum == "") {
                                Toast.makeText(requireContext(),
                                    "Maydonni to`ldiring",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                val number = text.text.toString()
                                var num = number.toDouble() * its.toDouble()
                                nummss.text = num.toString()
                            }
                        }
                        create.dismiss()
                    }
                    create.show()
                }
            })
            rvc.adapter = homeAdapter

            if (networkHelper.isNetworkConnected()) {
                apiService.getUsers().enqueue(object : Callback<List<MoneyItem>> {
                    override fun onResponse(
                        call: Call<List<MoneyItem>>,
                        response: Response<List<MoneyItem>>,
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            body?.forEach { it ->
                                it.image =
                                    "https://flagcdn.com/w160/" + it.Ccy[0].lowercase() + it.Ccy[1].lowercase() + ".png"
                                userEntity =
                                    MoneyInsert(it.Ccy,
                                        it.CcyNm_UZ,
                                        it.Date,
                                        it.Diff,
                                        it.Rate,
                                        it.id,
                                        it.image,
                                        it.isliked
                                    )
                                databace.onlineDao().addUser(userEntity)
                                databace.onlineDao().getAllUsers()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        homeAdapter.submitList(it)
                                    }
                            }
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<List<MoneyItem>>, t: Throwable) {
                        Toast.makeText(requireContext(),
                            "Inertnetdan ma`lumotlarni olib kela olmayapmiz",
                            Toast.LENGTH_SHORT).show()
                    }
                })
            } else {

            }

            databace.onlineDao().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    homeAdapter.submitList(it)
                }

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build()

            val workRequest2 = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints).build()
            WorkManager.getInstance(requireContext())
                .enqueue(workRequest2)
        }
        return fragment.root
    }
}