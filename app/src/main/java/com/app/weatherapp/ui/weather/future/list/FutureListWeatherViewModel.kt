package com.app.weatherapp.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.app.weatherapp.data.provider.UnitProvider
import com.app.weatherapp.data.repository.ForecastRepository
import com.app.weatherapp.internal.lazyDeferred
import com.app.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(),super.isMetricUnit)
    }

}