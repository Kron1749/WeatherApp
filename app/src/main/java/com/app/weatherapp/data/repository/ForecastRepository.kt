package com.app.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.app.weatherapp.data.db.entity.WeatherLocation
import com.app.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.app.weatherapp.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.app.weatherapp.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository { // interface for repository
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> //suspend for async
    //metric for check is it metric system or imperial

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>

    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}