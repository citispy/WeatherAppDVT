package com.mobile.weatherappdvt.ui.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest : TestCase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repositoryForSuccess = FakeCurrentWeatherRepository.forSuccessfulResponse()
    private val repositoryForFailure = FakeCurrentWeatherRepository.forFailedResponse()

    @Test
    fun `check current temp value for successful response`() {
        val viewModel = WeatherViewModel(repositoryForSuccess)
        val observer: Observer<Double?> = Observer {
            assert(it == 301.97)
        }
        viewModel.currentTemp.observeForever(observer)
        viewModel.currentTemp.removeObserver(observer)
    }

    @Test
    fun `check current temp value for failed response`() {
        val viewModel = WeatherViewModel(repositoryForFailure)
        val observer: Observer<Double?> = Observer {
            assert(it == null)
        }
        viewModel.currentTemp.observeForever(observer)
        viewModel.currentTemp.removeObserver(observer)
    }

    @Test
    fun `check min temp value for successful response`() {
        val viewModel = WeatherViewModel(repositoryForSuccess)
        val observer: Observer<Double?> = Observer {
            assert(it == 300.43)
        }
        viewModel.minTemp.observeForever(observer)
        viewModel.minTemp.removeObserver(observer)
    }

    @Test
    fun `check min temp value for failed response`() {
        val viewModel = WeatherViewModel(repositoryForFailure)
        val observer: Observer<Double?> = Observer {
            assert(it == null)
        }
        viewModel.minTemp.observeForever(observer)
        viewModel.minTemp.removeObserver(observer)
    }

    @Test
    fun `check max temp value for successful response`() {
        val viewModel = WeatherViewModel(repositoryForSuccess)
        val observer: Observer<Double?> = Observer {
            assert(it == 304.38)
        }
        viewModel.maxTemp.observeForever(observer)
        viewModel.maxTemp.removeObserver(observer)
    }

    @Test
    fun `check max temp value for failed response`() {
        val viewModel = WeatherViewModel(repositoryForFailure)
        val observer: Observer<Double?> = Observer {
            assert(it == null)
        }
        viewModel.maxTemp.observeForever(observer)
        viewModel.maxTemp.removeObserver(observer)
    }

    @Test
    fun `check weather description value for successful response`() {
        val viewModel = WeatherViewModel(repositoryForSuccess)
        val observer: Observer<String?> = Observer {
            assert(it.equals("Clear"))
        }
        viewModel.weatherDescription.observeForever(observer)
        viewModel.weatherDescription.removeObserver(observer)
    }

    @Test
    fun `check weather description value for failed response`() {
        val viewModel = WeatherViewModel(repositoryForFailure)
        val observer: Observer<String?> = Observer {
            assert(it == null)
        }
        viewModel.weatherDescription.observeForever(observer)
        viewModel.weatherDescription.removeObserver(observer)
    }

    @Test
    fun `check message value for successful response`() {
        val viewModel = WeatherViewModel(repositoryForSuccess)
        val observer: Observer<String?> = Observer {
            assert(it == null)
        }
        viewModel.errorMessage.observeForever(observer)
        viewModel.errorMessage.removeObserver(observer)
    }

    @Test
    fun `check message value for failed response`() {
        val viewModel = WeatherViewModel(repositoryForFailure)
        val observer: Observer<String?> = Observer {
            assert(it.equals("city not found"))
        }
        viewModel.errorMessage.observeForever(observer)
        viewModel.errorMessage.removeObserver(observer)
    }
}