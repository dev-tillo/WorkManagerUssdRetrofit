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
import com.example.workermanager.R
import com.example.workermanager.adapters.HomeAdapter
import com.example.workermanager.classess.MoneyItem
import com.example.workermanager.dao.Databace
import com.example.workermanager.dao.MoneyInsert
import com.example.workermanager.databinding.BottomsheetBinding
import com.example.workermanager.databinding.FragmentSelectedBinding
import com.example.workermanager.networker.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectedFragment : Fragment() {

    private lateinit var fragment: FragmentSelectedBinding
    lateinit var homeAdapter: HomeAdapter
    lateinit var databace: Databace
    private lateinit var moneyInsert: MoneyInsert

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentSelectedBinding.inflate(inflater, container, false)
        fragment.apply {
            databace = Databace.getInstance(requireContext())
            homeAdapter = HomeAdapter(requireContext(), object : HomeAdapter.OnitemCliked {
                override fun onCliked(money: MoneyInsert, position: Int) {

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
        }
        return fragment.root
    }
}