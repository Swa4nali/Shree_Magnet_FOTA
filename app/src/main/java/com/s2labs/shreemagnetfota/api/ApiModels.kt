package com.s2labs.shreemagnetfota.api

import androidx.recyclerview.widget.DiffUtil

data class BuildModel(
	var id: Long = 0,
	var name: String? = null,
	var desc: String? = null
)

data class VersionModel(
	var versionId: Long = 0,
	var version: Float = 0f,
	var filePath: String? = null
)

data class ProductModel(
	var productId: Long = 0,
	var name: String? = null,
	var desc: String? = null,
	var filePath: String? = null
) {
	class DiffCallback : DiffUtil.ItemCallback<ProductModel>() {
		override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
			return oldItem.productId == newItem.productId
		}

		override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
			return oldItem == newItem
		}
	}
}