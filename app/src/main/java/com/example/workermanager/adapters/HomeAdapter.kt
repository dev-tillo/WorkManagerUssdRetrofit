package com.example.workermanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workermanager.R
import com.example.workermanager.dao.MoneyInsert
import com.example.workermanager.databinding.ItemHomeBinding
import com.squareup.picasso.Picasso

class HomeAdapter(var context: Context, var onitemCliked: OnitemCliked) :
    ListAdapter<MoneyInsert, HomeAdapter.Vh>(MyDiffUtill()) {

    inner class Vh(var itemHomeBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(money: MoneyInsert, position: Int) {
            val item = getItem(position)
            itemHomeBinding.apply {
                name.text = item.Ccy
                valyutaname.text = item.CcyNm_UZ
                sinonim.text = item.Rate
                daymonthyear.text = item.Date + "/"
                time.text = item.Diff

                Glide.with(context).load(item.image).placeholder(R.drawable.list).into(image)
//                Picasso.get().load(item.image).placeholder(R.drawable.list).into(image)

                card.setOnClickListener {
                    onitemCliked.cardClick(money, position)
                }
                blur.setOnClickListener {
                    onitemCliked.onCliked(money, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class MyDiffUtill : DiffUtil.ItemCallback<MoneyInsert>() {
        override fun areItemsTheSame(oldItem: MoneyInsert, newItem: MoneyInsert): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoneyInsert, newItem: MoneyInsert): Boolean {
            return oldItem == newItem
        }
    }

    interface OnitemCliked {
        fun onCliked(money: MoneyInsert, position: Int)
        fun cardClick(money: MoneyInsert, position: Int)
    }
}