package com.app.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.data.network.response.CurrentWeatherResponse
import com.app.weatherapp.data.network.response.FutureWeatherResponse
import com.app.weatherapp.internal.NoConnectivityException

const val FORECAST_DAYS_COUNT = 7

class WeatherNerworkDataSourceImpl( // используется чтобы узнать есть ли интернет
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather =
        MutableLiveData<CurrentWeatherResponse>() //т.к MutableLiveData возможно обновить
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse> // т.к LiveData невозможно обновить
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather =
                apixuWeatherApiService // пробуем получить current weather from apixuWeatherApiService
                    .getCurrentWeatherAsync(location, languageCode)
                    .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)//обновляем
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    ) {
        try {
            val fetchedFutureWeather = apixuWeatherApiService
                .getFutureWeatherAsync(location, FORECAST_DAYS_COUNT, languageCode)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }


}