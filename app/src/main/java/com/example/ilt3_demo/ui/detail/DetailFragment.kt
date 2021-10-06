package com.example.ilt3_demo.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.ilt3_demo.api.RestoApiConfig
import com.example.ilt3_demo.data.detail.CategoryItem
import com.example.ilt3_demo.data.detail.CustomerReviewItem
import com.example.ilt3_demo.databinding.FragmentDetailBinding
import com.example.ilt3_demo.utils.restoPictureUrlFromId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private var customerReviewAdapter: CustomerReviewAdapter? = null
    private val workManagerInstance: WorkManager
        get() {
            return WorkManager.getInstance(requireContext())
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantId = DetailFragmentArgs.fromBundle(requireArguments()).restaurantId
        customerReviewAdapter = CustomerReviewAdapter()
        binding?.customerRecycler?.apply {
            adapter = customerReviewAdapter
            binding?.customerRecycler?.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        loadData(restaurantId)
        setupAddReview(restaurantId)
    }

    private fun setupAddReview(restaurantId: String) {
        binding?.apply {
            submitReview.setOnClickListener {
                val name = customerName.text.toString().trim()
                val content = customerReview.text.toString().trim()

                if(name.isNullOrEmpty()){
                    customerName.error = "Please fill name!"
                }

                if(content.isNullOrEmpty()){
                    customerReview.error = "Please fill review resutl!"
                    return@setOnClickListener
                }

                callAddReview(restaurantId, name, content)
            }
        }
    }

    private fun callAddReview(restaurantId: String, restoName: String, restoContent: String) {
        val worker = OneTimeWorkRequestBuilder<ReviewPostWorker>()
            .setInputData(Data.Builder().apply {
                putString(ReviewPostWorker.RESTAURANT_ID, restaurantId)
                putString(ReviewPostWorker.RESTAURANT_NAME, restoName)
                putString(ReviewPostWorker.RESTAURANT_CONTENT, restoContent)
            }.build())

            .setConstraints(Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }.build())
            .build()

        workManagerInstance.enqueue(worker)
        setupWorkInfo(worker.id)
    }

    private fun setupWorkInfo(workId: UUID){
        workManagerInstance.getWorkInfoByIdLiveData(workId)
            .observe(viewLifecycleOwner) {
                when(it.state){
                    WorkInfo.State.SUCCEEDED -> {
                        val name = it.outputData.getString(ReviewPostWorker.RESTAURANT_NAME)
                        val content = it.outputData.getString(ReviewPostWorker.RESTAURANT_CONTENT)
                        appendReviewData(name, content)
                        Toast.makeText(requireContext(), "WORK SUCCESS!", Toast.LENGTH_LONG).show()
                    }
                    WorkInfo.State.FAILED -> {
                        Toast.makeText(requireContext(), "WORK FAILED!", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun appendReviewData(reviewerName: String?, reviewContent: String?){
        if(reviewerName == null || reviewContent == null) return
        customerReviewAdapter?.append(CustomerReviewItem(reviewerName, reviewContent))
    }

    private fun loadData(restaurantId: String) {
        lifecycleScope.launchWhenResumed {
            launch(Dispatchers.IO) {
                val restoApi = RestoApiConfig.getRestaurantApi()
                val restaurantData = restoApi.getDetailRestaurant(restaurantId)
                val restaurant = restaurantData.restaurant
                launch(Dispatchers.Main) {

                    displayTitleAndPoster(restaurant.name, restaurant.pictureId.restoPictureUrlFromId)
                    displayDescriptionData(
                        restaurant.address,
                        restaurant. city,
                        restaurant.categories,
                        restaurant.description
                    )
                    displayRestaurantReview(
                        restaurant.customerReviews
                    )

                }
            }
        }
    }

    private fun displayRestaurantReview(customerReviews: List<CustomerReviewItem>) {
        customerReviewAdapter?.addAllData(customerReviews)
    }

    private fun displayDescriptionData(
        address: String,
        city: String,
        categories: List<CategoryItem>,
        description: String
    ) {
        binding?.apply {
            addressValue.text = address
            cityValue.text = city
            categoriesValue.text = categories.joinToString { it.name }
            descriptionValue.text = description
        }
    }

    private fun displayTitleAndPoster(name: String, restoPictureUrlFromId: String?) {
        binding?.apply {
            restarurantName.text = name
            Glide.with(root.context).load(restoPictureUrlFromId).into(posterImage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.customerRecycler?.adapter = null
        binding = null
    }


    //
}