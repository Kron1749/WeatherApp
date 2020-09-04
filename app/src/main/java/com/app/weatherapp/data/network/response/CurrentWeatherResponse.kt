package com.app.weatherapp.data.network.response

import com.app.weatherapp.data.db.entity.CurrentWeatherEntry
import com.app.weatherapp.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location: WeatherLocation,

    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry

)