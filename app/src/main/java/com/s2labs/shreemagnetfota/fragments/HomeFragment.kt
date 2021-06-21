package com.s2labs.shreemagnetfota.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.s2labs.shreemagnetfota.BaseActivity
import com.s2labs.shreemagnetfota.BuildSelectActivity
import com.s2labs.shreemagnetfota.adapter.ProductListAdapter
import com.s2labs.shreemagnetfota.api.ApiService
import com.s2labs.shreemagnetfota.api.ProductModel
import com.s2labs.shreemagnetfota.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

	private val apiService by lazy { ApiService.getInstance() }
	private lateinit var binding: FragmentHomeBinding
	private val adapter by lazy { ProductListAdapter() }

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.recyclerView.adapter = adapter
		adapter.clickListener = object : ProductListAdapter.OnClickListener {
			override fun onClick(item: ProductModel, position: Int) {
				startActivity(Intent(requireActivity(), BuildSelectActivity::class.java).apply {
					putExtra("product_id", item.productId)
				})
			}
		}

		binding.progress.visibility = View.VISIBLE
		lifecycleScope.launch(Dispatchers.IO) {
			try {
				val resp = apiService.productList()
				withContext(Dispatchers.Main) {
					binding.progress.visibility = View.GONE
					adapter.submitList(resp)
				}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					(requireActivity() as BaseActivity).showConnectionError(true)
				}
			}
		}
	}
}











