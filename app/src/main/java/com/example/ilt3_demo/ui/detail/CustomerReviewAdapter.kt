package com.example.ilt3_demo.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ilt3_demo.data.detail.CustomerReviewItem
import com.example.ilt3_demo.databinding.ItemReviewBinding

class CustomerReviewAdapter: RecyclerView.Adapter<CustomerReviewVH>() {

    private val listCustomerReview = mutableListOf<CustomerReviewItem>()

    fun addAllData(reviewCollection: List<CustomerReviewItem>){
        listCustomerReview.addAll(reviewCollection)
        notifyItemRangeInserted(listCustomerReview.size, reviewCollection.size)
    }

    fun append(data: CustomerReviewItem){
        listCustomerReview.add(data)
        notifyItemInserted(listCustomerReview.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerReviewVH {
        return CustomerReviewVH.createFrom(parent)
    }

    override fun onBindViewHolder(holder: CustomerReviewVH, position: Int) {
        holder.bind(listCustomerReview[position])
    }

    override fun getItemCount(): Int {
        return listCustomerReview.size
    }

}

class CustomerReviewVH private constructor(
    private val itemReviewBinding: ItemReviewBinding
): RecyclerView.ViewHolder(itemReviewBinding.root) {

    fun bind(data: CustomerReviewItem){
        itemReviewBinding.reviewerName.text = data.name
        itemReviewBinding.reviewContent.text = data.review
    }

    companion object {
        fun createFrom(container: ViewGroup): CustomerReviewVH {
            val binding = ItemReviewBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return CustomerReviewVH(binding)
        }
    }
}