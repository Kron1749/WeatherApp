package com.app.weatherapp.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.app.weatherapp.data.provider.UnitProvider
import com.app.weatherapp.data.repository.ForecastRepository
import com.app.weatherapp.internal.lazyDeferred
import com.app.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}