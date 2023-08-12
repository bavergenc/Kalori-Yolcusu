package com.example.healtapp.adapter

import android.content.Context
import android.view.LayoutInflater
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
        val kcarbText = "${currentItem.carbohydrateContent} kcal"

//        holder.list_item_binding.foodName.text = currentItem.name
        holder.list_item_binding.foodKcal.text = kcalText
        holder.list_item_binding.foodCarb.text = kcarbText

        Glide.with(context).load(currentItem.image).into(holder.list_item_binding.foodImage)
        holder.list_item_binding.imageView2.setBackgroundResource(R.drawable.menu_default)

        holder.list_item_binding.imageView2.setOnClickListener {

            holder.list_item_binding.imageView2.setOnClickListener {

                val calories = currentItem.calories
                val carbonhidrat = currentItem.carbohydrateContent
                val protein = currentItem.proteinContent
                val yağ = currentItem.fatContent

                val kcal = Kcal(0, "", "", calories, carbonhidrat, protein, yağ, "")

                viewModel.addKcalNoteAndShowToast(kcal, context)

            }
        }
    }

    override fun getItemCount(): Int = appList.size

    override fun getItemViewType(position: Int): Int {
        // Her satırda iki öğe olduğu için tür 0 olarak döndürülür
        return 0
    }

    class ReciyclerViewHolder(var list_item_binding: RvItemBinding) : RecyclerView.ViewHolder(list_item_binding.root)
}

