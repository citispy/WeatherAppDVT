package com.mobile.weatherappdvt.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.FragmentWeatherBinding
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
    }
}