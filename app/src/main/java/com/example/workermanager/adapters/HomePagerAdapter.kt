package com.example.workermanager.adapters


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workermanager.fragments.Pager
import com.example.workermanager.fragments.SelectedFragment

class HomePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            Pager()
        } else {
            SelectedFragment()
        }
    }
}