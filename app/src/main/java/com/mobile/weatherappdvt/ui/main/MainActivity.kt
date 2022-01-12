package com.mobile.weatherappdvt.ui.main

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.ActivityMainBinding
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel
import com.mobile.weatherappdvt.util.Constants.LOCATION_PERMISSION_REQUEST_CODE
import com.mobile.weatherappdvt.util.KEY_CITY_NAME
import com.mobile.weatherappdvt.util.SharedPrefsUtils
import com.mobile.weatherappdvt.util.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val permissionsViewModel: PermissionsViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var cityName: TextView? = null
    private var edit: ImageButton? = null
    private var favourite: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.enablePermissions.setOnClickListener {
            AppSettingsDialog.Builder(this).build().show()
        }
        observeViewModel()
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost)
                as NavHostFragment
        navController = navHostFragment.navController
        //Set toolbar title before setting NavGraph. Otherwise the title won't set.
        binding.toolbar.title = SharedPrefsUtils(this).getPrefs(KEY_CITY_NAME)
        navController.setGraph(R.navigation.nav_graph)

        //Navigation Drawer
        NavigationUI.setupWithNavController(binding.navView, navController)
        initNavDrawerHeader()
        binding.navView.setNavigationItemSelectedListener {
            Toast.makeText(this, "Not Implemented", Toast.LENGTH_LONG).show()
            binding.drawerLayout.close()
            NavigationUI.onNavDestinationSelected(it, navController)
        }

        //Toolbar
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.toolbar, navController, getAppBarConfiguration())
    }

    private fun initNavDrawerHeader() {
        val view = binding.navView.getHeaderView(0)

        cityName = view.findViewById(R.id.city)
        edit = view.findViewById(R.id.edit)
        favourite = view.findViewById(R.id.favourite)

        edit?.setOnClickListener {
            navController.navigate(R.id.action_weatherFragment_to_placePickerFragment)
            binding.drawerLayout.close()
        }
    }

    private fun observeViewModel() {
        observeCityName()
        observePermissions()
        observerBackgroundColor()
    }

    private fun observeCityName() {
        locationViewModel.cityName.observe(this) {
            if (it != null) {
                binding.toolbar.title = it
                cityName?.text = it
            } else {
                cityName?.text = getString(R.string.no_city_yet)
            }

            favourite?.visibility = if(it != null) View.VISIBLE else View.GONE
        }
    }

    private fun observePermissions() {
        permissionsViewModel.locationPermissionsRequested.observe(this) {
            if (it.contentIfNotHandled == true) {
                checkLocationPermissions()
            }
        }

        permissionsViewModel.locationPermissionsGranted.observe(this) {
            val visibility = if (it.peekContent()) View.GONE else View.VISIBLE
            binding.noPermissons.visibility = visibility
            binding.enablePermissions.visibility = visibility
            binding.navHost.visibility = if (it.peekContent()) View.VISIBLE else View.GONE
        }
    }

    private fun observerBackgroundColor() {
        weatherViewModel.backgroundColor.observe(this) {
            if (it != null) {
                val color = resources.getColor(it, null)
                binding.toolbar.setBackgroundColor(color)
                binding.navView.setBackgroundColor(color)
            }
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

    private fun getAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration.Builder(R.id.weatherFragment)
            .setOpenableLayout(binding.drawerLayout)
            .build()
    }
}