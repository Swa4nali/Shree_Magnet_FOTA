package com.s2labs.shreemagnetfota.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s2labs.shreemagnetfota.R
import com.s2labs.shreemagnetfota.api.ApiService
import com.s2labs.shreemagnetfota.api.ProductModel
import com.s2labs.shreemagnetfota.databinding.ProductLineRowItemBinding
import com.squareup.picasso.Picasso

class ProductListAdapter(
	var clickListener: OnClickListener? = null
) : ListAdapter<ProductModel, ProductListAdapter.ViewHolder>(ProductModel.DiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_line_row_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)

		holder.binding?.item = item
		holder.binding?.root?.setOnClickListener {
			clickListener?.onClick(item, position)
		}
		Picasso.get().load(ApiService.IMAGE_BASE_URL + item.filePath).into(holder.binding?.productImage)
	}

	class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
		val binding: ProductLineRowItemBinding? = DataBindingUtil.bind(v)
	}

	interface OnClickListener {
		fun onClick(item: ProductModel, position: Int)
	}
}