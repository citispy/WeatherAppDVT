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
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val forecastViewModel: ForecastViewModel by viewModels()
    private val permissionsViewModel: PermissionsViewModel by activityViewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.retryWeather.setOnClickListener {
            val location = TrackingUtils.getLastKnownLocation(requireContext())
            getWeatherAttempt(location)
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
            val location = TrackingUtils.getLastKnownLocation(requireContext())
            getWeatherAttempt(location)
        }
    }

    private fun getWeatherAttempt(location: Location?) {
        when {
            location == null -> setNoLocationViewsVisible(true)
            weatherViewModel.haveAllWeatherInfo() -> return
            else -> {
                getWeatherFor(location)
                setNoLocationViewsVisible(false)
            }
        }
    }

    private fun setNoLocationViewsVisible(visible: Boolean) {
        val visibility = if(visible) View.VISIBLE else View.GONE
        binding.locationNotFound.visibility = visibility
        binding.setLocation.visibility = visibility
    }

    private fun getWeatherFor(location: Location) {
        weatherViewModel.getCurrentWeather(location.longitude, location.latitude)
        forecastViewModel.getForecast(location.longitude, location.latitude)
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
}