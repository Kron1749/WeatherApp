package com.app.weatherapp.ui.base

import androidx.lifecycle.ViewModel
import com.app.weatherapp.data.provider.UnitProvider
import com.app.weatherapp.data.repository.ForecastRepository
import com.app.weatherapp.internal.UnitSystem
import com.app.weatherapp.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}