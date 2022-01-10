package com.mobile.weatherappdvt.ui.main

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.ActivityMainBinding
import com.mobile.weatherappdvt.util.Constants.LOCATION_PERMISSION_REQUEST_CODE
import com.mobile.weatherappdvt.util.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val permissionsViewModel: PermissionsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.enablePermissions.setOnClickListener {
            AppSettingsDialog.Builder(this).build().show()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        permissionsViewModel.locationPermissionsRequested.observe(this) {
            val requested = it.contentIfNotHandled as Boolean
            if (requested) {
                checkLocationPermissions()
            }
        }

        permissionsViewModel.locationPermissionsGranted.observe(this) {
            val visibility = if(it) View.GONE else View.VISIBLE
            binding.noPermissons.visibility = visibility
            binding.enablePermissions.visibility = visibility
            binding.navigationView.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    private fun checkLocationPermissions() {
        if (TrackingUtils.hasLocationPermissions(this)) {
            permissionsViewModel.permissionsGranted(true)
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.location_permissions_required),
            LOCATION_PERMISSION_REQUEST_CODE,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permissionsViewModel.permissionsGranted(false)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            checkLocationPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionsViewModel.permissionsGranted(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}