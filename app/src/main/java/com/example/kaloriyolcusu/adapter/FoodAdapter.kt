package com.example.kaloriyolcusu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healtapp.model.Kcal
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.kaloriyolcusu.R
import com.example.kaloriyolcusu.databinding.RvItemBinding
import com.example.kaloriyolcusu.onclick.OnDeleteClickListener

class FoodAdapter(private val context: Context, private val viewModel: KcalViewModel) :
    RecyclerView.Adapter<FoodAdapter.KcalViewHolder>() {

    var kcalList: List<Kcal> = emptyList()

    // Yeni değişken
    private var showRemoveIcon = false

    // Silme işlemi için listener
    private var deleteListener: OnDeleteClickListener? = null

    inner class KcalViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val nameTextView = binding.foodName
        val caloriesTextView = binding.foodKcal
        val removeIconImageView = binding.removeIcon
        val addIconImageView = binding.addFood
        val serving = binding.foodService
        val image = binding.imageView2

        init {
            removeIconImageView.setOnClickListener {
                val clickedItem = kcalList[adapterPosition]
                // İlgili öğeyi veritabanından silme işlemi
                viewModel.deleteKcalItem(clickedItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KcalViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KcalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KcalViewHolder, position: Int) {
        val currentKcal = kcalList[position]
        holder.caloriesTextView.text = currentKcal.calories +  " kcal"
        holder.nameTextView.text = currentKcal.name
        holder.serving.text = currentKcal.servingSize

        Glide.with(context).load(currentKcal.image).into(holder.binding.foodImage)
        holder.image.setBackgroundResource(R.drawable.menu_default)
        if (showRemoveIcon) {
            holder.removeIconImageView.visibility = View.INVISIBLE
            holder.addIconImageView.visibility = View.INVISIBLE
        } else {
            holder.removeIconImageView.visibility = View.VISIBLE
            holder.addIconImageView.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return kcalList.size
    }

    // removeIcon görünürlüğünü değiştiren metod
    fun toggleRemoveIconVisibility() {
        showRemoveIcon = !showRemoveIcon
        notifyDataSetChanged()
    }

    // Silme işlemi için listener'ı set etmek
    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        deleteListener = listener
    }



    // Silme işlemi için arayüz
    interface OnDeleteClickListener {
        fun onDeleteClicked(item: Kcal)
    }
}