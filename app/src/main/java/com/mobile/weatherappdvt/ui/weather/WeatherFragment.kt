package com.mobile.weatherappdvt.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.FragmentWeatherBinding
import com.mobile.weatherappdvt.ui.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)

        observeViewModels()

        return binding.root
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