package com.mobile.weatherappdvt.ui.weather

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.FragmentWeatherBinding
import com.mobile.weatherappdvt.ui.main.PermissionsViewModel
import com.mobile.weatherappdvt.ui.weather.viewmodel.ForecastViewModel
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel.*
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel.UiState.*
import com.mobile.weatherappdvt.util.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint
import com.mobile.weatherappdvt.ui.main.MainActivity
import com.mobile.weatherappdvt.util.FormatUtils

/**
 * Fragment for displaying the current weather and 5 day forecast
 * Before retrieving the weather information, we first check if location permissions were granted @see [MainActivity]
 * Then we check if the user has a last known location @see [provideLocation]
 * If the user has a location, then we make a call to the api
 * If the user doesn't have a location, then an error message is displayed
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val forecastViewModel: ForecastViewModel by viewModels()
    private val permissionsViewModel: PermissionsViewModel by activityViewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.retryWeather.setOnClickListener {
            provideLocation()
        }

        adapter = ForecastAdapter(requireContext())

        initForecastsList()
        observeViewModels()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        permissionsViewModel.requestLocationPermissions()
    }

    private fun initForecastsList() {
        val forecastList = binding.forecastList
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        forecastList.adapter = adapter
    }

    private fun observeViewModels() {
        observerWeatherViewModel()
        observeForecastViewModel()
        observePermissionsViewModel()
    }

    private fun observerWeatherViewModel() {
        weatherViewModel.errorMessage.observe(this) {
            setErrorMessage(it)
        }

        weatherViewModel.uiState.observe(this) {
            setVisibility(it)
        }

        weatherViewModel.currentTemp.observe(this) {
            binding.temp.text = it
            binding.currentTemp.text = it
        }

        weatherViewModel.minTemp.observe(this) {
            binding.minTemp.text = it
        }

        weatherViewModel.maxTemp.observe(this) {
            binding.maxTemp.text = it
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

        weatherViewModel.location.observe(this) {
            if (it != null) {
                val city = FormatUtils.descriptionForLocation(it, requireContext())
                getWeatherFor(it)
            }
        }
    }

    private fun observeForecastViewModel() {
        forecastViewModel.forecast.observe(this) {
            adapter.updateForecasts(it)
        }

        forecastViewModel.isLoading.observe(this) {
            binding.forecastProgress.visibility = if (it) View.VISIBLE else View.GONE
            binding.forecastList.visibility = if (it) View.GONE else View.VISIBLE
        }
    }

    private fun observePermissionsViewModel() {
        permissionsViewModel.locationPermissionsGranted.observe(this) {
            provideLocation()
        }
    }

    private fun provideLocation() {
        val location = TrackingUtils.getLastKnownLocation(requireContext())
        weatherViewModel.setLiveLocation(location)
    }

    private fun getWeatherFor(location: Location) {
        val city = FormatUtils.descriptionForLocation(location, requireContext())
        weatherViewModel.getCurrentWeather(city)
        forecastViewModel.getForecast(city)
    }

    private fun setErrorMessage(it: String?) {
        if (it != null) {
            binding.message.text = it
        }
    }

    private fun setVisibility(it: UiState?) {
        binding.message.visibility =
            if (it == ERROR_MESSAGE_RECEIVED) View.VISIBLE else View.GONE
        binding.retryWeather.visibility =
            if (it == ERROR_MESSAGE_RECEIVED) View.VISIBLE else View.GONE
        binding.currentWeatherContainer.visibility =
            if (it == SUCCESSFULLY_RETRIEVED_DATA) View.VISIBLE else View.GONE
        binding.divider.visibility =
            if (it == SUCCESSFULLY_RETRIEVED_DATA) View.VISIBLE else View.GONE
        binding.currentWeatherProgress.visibility =
            if (it == LOADING) View.VISIBLE else View.GONE
        binding.locationNotFound.visibility =
            if (it == NO_LOCATION_FOUND) View.VISIBLE else View.GONE
        binding.setLocation.visibility =
            if (it == NO_LOCATION_FOUND) View.VISIBLE else View.GONE
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
}