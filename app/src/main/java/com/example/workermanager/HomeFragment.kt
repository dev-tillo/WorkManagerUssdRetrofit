package com.example.workermanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.workermanager.adapters.HomePagerAdapter
import com.example.workermanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var fragment: FragmentHomeBinding
    lateinit var homePagerAdapter: HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragment = FragmentHomeBinding.inflate(inflater, container, false)
        fragment.apply {
            homePagerAdapter = HomePagerAdapter(requireParentFragment())
            pager2.adapter = homePagerAdapter
            pager2.isUserInputEnabled = false

            pager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        bottom.selectedItemId = R.id.home
                    } else {
                        bottom.selectedItemId = R.id.second
                    }
                }
            })

            bottom.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> pager2.currentItem = 0
                    R.id.second -> pager2.currentItem = 1
                }
                true
            }
        }
        return fragment.root
    }
}