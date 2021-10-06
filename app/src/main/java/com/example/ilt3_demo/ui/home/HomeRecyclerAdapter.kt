package com.example.ilt3_demo.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilt3_demo.data.RestaurantItem
import com.example.ilt3_demo.databinding.ItemRestaurantBinding
import com.example.ilt3_demo.utils.restoPictureUrlFromId

class HomeRecyclerAdapter(
    private val clickCallback: (RestaurantItem) -> Unit
): RecyclerView.Adapter<HomeItemVH>(){

    private val listRestaurantItem = mutableListOf<RestaurantItem>()

    fun addAllData(restoCollection: List<RestaurantItem>){
        listRestaurantItem.addAll(restoCollection)
        notifyItemRangeInserted(listRestaurantItem.size, restoCollection.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemVH {
        return HomeItemVH.createFrom(parent)
    }

    override fun onBindViewHolder(holder: HomeItemVH, position: Int) {
        holder.bind(listRestaurantItem[position], clickCallback)
    }

    override fun getItemCount(): Int = listRestaurantItem.size
}

class HomeItemVH private constructor(
    private val itemRestaurantBinding: ItemRestaurantBinding
): RecyclerView.ViewHolder(itemRestaurantBinding.root) {

    @SuppressLint("SetTextI18n") // NOT RECOMMENDED
    fun bind(data: RestaurantItem, clickCb: (RestaurantItem) -> Unit){
        itemRestaurantBinding.apply {
            restaurantName.text = data.name
            city.text = "From: ${data.city}"
            rating.text = "Rating: ${data.rating}"
            toDetail.setOnClickListener {
                clickCb(data)
            }

            Glide.with(root.context)
                .load(data.pictureId?.restoPictureUrlFromId)
                .into(poster)

        }
    }

    companion object {
        fun createFrom(parent: ViewGroup): HomeItemVH {
            val binding = ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HomeItemVH(binding)
        }
    }
}