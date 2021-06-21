package com.s2labs.shreemagnetfota.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.s2labs.shreemagnetfota.R
import com.s2labs.shreemagnetfota.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
	private lateinit var binding: FragmentSettingsBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentSettingsBinding.inflate(inflater, container, false)

		val language = resources.getStringArray(R.array.Languages)
		val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, language)
		binding.autoCompleteTextView.setAdapter(arrayAdapter)

		return binding.root
	}
}