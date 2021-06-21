package com.s2labs.shreemagnetfota

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.s2labs.shreemagnetfota.databinding.ActivityProductListBinding
import com.s2labs.shreemagnetfota.fragments.AboutUsFragment
import com.s2labs.shreemagnetfota.fragments.ContactUsFragment
import com.s2labs.shreemagnetfota.fragments.HomeFragment
import com.s2labs.shreemagnetfota.fragments.SettingsFragment


class ProductListActivity : BaseActivity() {

	private lateinit var binding: ActivityProductListBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)

		val homeFragment = HomeFragment()
		val aboutUsFragment = AboutUsFragment()
		val contactUsFragment = ContactUsFragment()
		val settingsFragment = SettingsFragment()

		makeCurrentFragment(homeFragment)

		binding.navBar.setOnNavigationItemSelectedListener {
			when (it.itemId) {
				R.id.home -> makeCurrentFragment(homeFragment)
				R.id.aboutUs -> makeCurrentFragment(aboutUsFragment)
				R.id.contact -> makeCurrentFragment(contactUsFragment)
				R.id.settings -> makeCurrentFragment(settingsFragment)
				else -> makeCurrentFragment(homeFragment)
			}
			true
		}

		// check for location permission
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, arrayOf(
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION
			), 1001)
		} else {
			enableLocation()
		}
	}

	private fun makeCurrentFragment(fragment: Fragment) {
		supportFragmentManager.beginTransaction().apply {
			replace(R.id.fragment, fragment)
			commit()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == 1001) {
			enableLocation()
		}
	}

	private fun enableLocation() {
		val locationRequest = LocationRequest.create().apply {
			interval = 10000
			fastestInterval = 5000
			priority = LocationRequest.PRIORITY_HIGH_ACCURACY
		}
		val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
		val client: SettingsClient = LocationServices.getSettingsClient(this)
		val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

		task.addOnFailureListener { exception ->
			if (exception is ResolvableApiException){
				try {
					exception.startResolutionForResult(this@ProductListActivity,
						1002)
				} catch (sendEx: IntentSender.SendIntentException) {
				}
			}
		}
	}
}