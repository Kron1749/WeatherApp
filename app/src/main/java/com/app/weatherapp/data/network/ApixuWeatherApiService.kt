package com.app.weatherapp.data.network

import com.app.weatherapp.data.network.response.CurrentWeatherResponse
import com.app.weatherapp.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "7b5760ff5e5d4e35a53202255201908" // key для получения данных


//http://api.weatherapi.com/v1/current.json?key=7b5760ff5e5d4e35a53202255201908&q=London&lang=en

interface ApixuWeatherApiService {

    @GET("current.json") // понимаем,что получаем данные current погоды
    fun getCurrentWeatherAsync( // функция для получения текущих данных
        @Query("q") location: String, // понимаем когда идет локация
        @Query("lang") languageCode: String = "en" // понимаем какой язык требуется
    ): Deferred<CurrentWeatherResponse> /* получаем данные,используем deferred т.к нужно время
                                         чтоб получить данные(можем использовать await() при вызове функции)*/

    //http://api.weatherapi.com/v1/forecast.json?key=7b5760ff5e5d4e35a53202255201908&q=Moscow&days=1
    @GET("forecast.json")
    fun getFutureWeatherAsync(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en"
    ): Deferred<FutureWeatherResponse>


    companion object { // используем для взаимодействия с interface ApixuWeatherApiService
        operator fun invoke( // необязательно юзать invoke используем т.к удобнее синтаксис
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService {
            val requestInterceptor = Interceptor { // используем для взаимодействия с API_KEY
                    chain -> // используем lambda syntax
                val url = chain.request() // используем API_KEY как Query параметр
                    .url()
                    .newBuilder() // используем builder для добавление QueryParameter
                    .addQueryParameter("key", API_KEY) // Добавляем API_KEY
                    .build()
                val request = chain.request() // создаем запрос
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request) // возвращаем обновленный url,который содержит API_KEY
            }

            val okHttpClient = OkHttpClient.Builder() // перехватывает каждый call
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder() // реализация ApixuWeatherApiService с помощью Retrofit
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/") // изначальная ссылка для каждого call
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) // используем так как юзаем Deferred
                .addConverterFactory(GsonConverterFactory.create()) // так юзаем Gson используем это
                .build()
                .create(ApixuWeatherApiService::class.java) // создание ApixuWeatherApiService с помощью Retrofit
        }
    }
}