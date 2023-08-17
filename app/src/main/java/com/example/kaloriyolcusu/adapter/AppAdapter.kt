package com.example.healtapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.healtapp.model.Kcal
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.kaloriyolcusu.R
import com.example.kaloriyolcusu.databinding.RvItemBinding


class AppAdapter(
    val context: Context,
    val appList: ArrayList<Kcal>,
    private val viewModel: KcalViewModel

) : RecyclerView.Adapter<AppAdapter.ReciyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciyclerViewHolder {
        val mBinding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReciyclerViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ReciyclerViewHolder, position: Int) {
        val currentItem = appList[position]
        val kcalText = "${currentItem.calories} kcal"
        val nameText = "${currentItem.name}"
        val servingText = "${currentItem.servingSize}"

//        holder.list_item_binding.foodName.text = currentItem.name
        holder.list_item_binding.foodKcal.text = kcalText
        holder.list_item_binding.foodName.text = nameText
        holder.list_item_binding.foodService.text = servingText

        Glide.with(context).load(currentItem.image).into(holder.list_item_binding.foodImage)
        holder.list_item_binding.imageView2.setBackgroundResource(R.drawable.menu_default)
        val addIconImageView = holder.list_item_binding.addFood
        addIconImageView.visibility = View.VISIBLE


            holder.list_item_binding.imageView2.setOnClickListener {

                val calories = currentItem.calories
                val carbohydrateContent = currentItem.carbohydrateContent
                val protein = currentItem.proteinContent
                val yağ = currentItem.fatContent
                val image = currentItem.image
                val name = currentItem.name
                val servingSize = currentItem.servingSize

                val kcal = Kcal(0, name, image, calories, carbohydrateContent, protein, yağ, servingSize)

                viewModel.addKcalNoteAndShowToast(kcal, context)

            }

    }

    override fun getItemCount(): Int = appList.size

    override fun getItemViewType(position: Int): Int {
        // Her satırda iki öğe olduğu için tür 0 olarak döndürülür
        return 0
    }

    class ReciyclerViewHolder(var list_item_binding: RvItemBinding) : RecyclerView.ViewHolder(list_item_binding.root)
}

