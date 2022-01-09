package com.mobile.weatherappdvt.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.FragmentWeatherBinding
import com.mobile.weatherappdvt.ui.weather.viewmodel.ForecastViewModel
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel
import com.mobile.weatherappdvt.util.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 0

@AndroidEntryPoint
class WeatherFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val forecastViewModel: ForecastViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        adapter = ForecastAdapter(requireContext())

        initForecastsList()
        observeViewModels()
        requestLocationPermissions()

        return binding.root
    }

    private fun initForecastsList() {
        val forecastList = binding.forecastList
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        forecastList.adapter = adapter
    }


    private fun observeViewModels() {
        weatherViewModel.currentTemp.observe(this) {
            setCurrentTemp(it)
        }

        weatherViewModel.minTemp.observe(this) {
            setMinTemp(it)
        }

        weatherViewModel.maxTemp.observe(this) {
            setMaxTemp(it)
        }

        weatherViewModel.weatherDescription.observe(this) {
            binding.weatherDescription.text = it
        }

        weatherViewModel.imageDrawable.observe(this) {
            setImageViewDrawable(it)
        }

        weatherViewModel.backgroundColor.observe(this) {
            setBackgroundColor(it)
        }

        forecastViewModel.forecast.observe(this) {
            adapter.updateForecasts(it)
        }
    }

    private fun requestLocationPermissions() {
        if (TrackingUtils.hasLocationPermissions(requireContext())) {
            getLastKnownLocation()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.location_permissions_required),
                LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        Log.d(WeatherFragment::javaClass.name, "latitude: " + location?.latitude + " longitude: " + location?.longitude)
        getWeatherInfo(location)
    }

    private fun getWeatherInfo(location: Location?) {
        if(location == null) {
            return
        }

        weatherViewModel.getCurrentWeather(location.longitude, location.latitude)
        forecastViewModel.getForecast(location.longitude, location.latitude)
    }

    private fun setCurrentTemp(it: String?) {
        val temp = convertToTempFormat(it)
        binding.temp.text = temp
        binding.currentTemp.text = temp
    }

    private fun setMinTemp(it: String?) {
        val temp = convertToTempFormat(it)
        binding.minTemp.text = temp
    }

    private fun setMaxTemp(it: String?) {
        val temp = convertToTempFormat(it)
        binding.maxTemp.text = temp
    }

    private fun setImageViewDrawable(it: Int?) {
        val drawable = it?.let { drawable -> ResourcesCompat.getDrawable(resources, drawable, null) }
        binding.image.setImageDrawable(drawable)
    }

    private fun setBackgroundColor(it: Int?) {
        if (it != null) {
            val color = resources.getColor(it, null)
            binding.parentView.setBackgroundColor(color)
        }
    }

    private fun convertToTempFormat(it: String?): String = getString(R.string.temp, it)

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getLastKnownLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}