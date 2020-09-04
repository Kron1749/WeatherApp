package com.app.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.app.weatherapp.data.provider.UnitProvider
import com.app.weatherapp.data.repository.ForecastRepository
import com.app.weatherapp.internal.UnitSystem
import com.app.weatherapp.internal.lazyDeferred
import com.app.weatherapp.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository,unitProvider) {


    val weather by lazyDeferred { //using lazyDeferred to initialize when we only need it cause
        //Suspend function 'getCurrentWeather' should be called only from a coroutine or another suspend function
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }


}