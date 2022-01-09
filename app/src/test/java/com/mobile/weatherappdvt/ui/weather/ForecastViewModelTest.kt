package com.mobile.weatherappdvt.ui.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mobile.weatherappdvt.model.ForecastListItem
import com.mobile.weatherappdvt.ui.weather.viewmodel.ForecastViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastViewModelTest : TestCase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repositoryForSuccess = FakeForecastRepository.forSuccessfulResponse()
    private val repositoryForFailure = FakeForecastRepository.forFailedResponse()

    @Test
    fun `check list size equals 5`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            assert(it?.size == 5)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check list item values`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            val item1 = it?.get(0)
            assert(item1?.temp == "22" && item1.day == "Monday" && item1.imageDrawableId == 2131165330)
            val item2 = it?.get(1)
            assert(item2?.temp == "20" && item2.day == "Tuesday" && item2.imageDrawableId == 2131165331)
            val item3 = it?.get(2)
            assert(item3?.temp == "25" && item3.day == "Wednesday" && item3.imageDrawableId == 2131165280)
            val item4 = it?.get(3)
            assert(item4?.temp == "29" && item4.day == "Thursday" && item4.imageDrawableId == 2131165330)
            val item5 = it?.get(4)
            assert(item5?.temp == "24" && item5.day == "Friday" && item5.imageDrawableId == 2131165330)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check values for failed response`() {
        val viewModel = ForecastViewModel(repositoryForFailure)
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            assert(it?.isEmpty() == true)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check message for successful response`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        val observer: Observer<String?> = Observer {
            assert(it == "0")
        }

        viewModel.message.observeForever(observer)
        viewModel.message.removeObserver(observer)
    }

    @Test
    fun `check message for failed response`() {
        val viewModel = ForecastViewModel(repositoryForFailure)
        val observer: Observer<String?> = Observer {
            assert(it == "city not found")
        }

        viewModel.message.observeForever(observer)
        viewModel.message.removeObserver(observer)
    }
}