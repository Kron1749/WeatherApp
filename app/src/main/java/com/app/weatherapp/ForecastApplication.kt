package com.app.weatherapp

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.app.weatherapp.data.db.ForecastDatabase
import com.app.weatherapp.data.network.*
import com.app.weatherapp.data.provider.LocationProvider
import com.app.weatherapp.data.provider.LocationProviderImpl
import com.app.weatherapp.data.provider.UnitProvider
import com.app.weatherapp.data.provider.UnitProviderImpl
import com.app.weatherapp.data.repository.ForecastRepository
import com.app.weatherapp.data.repository.ForecastRepositoryImpl
import com.app.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.app.weatherapp.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.app.weatherapp.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication:Application(),KodeinAware {
    override val kodein= Kodein.lazy {
        import(androidXModule(this@ForecastApplication)) //implement every what we need(now we dont need to code it)

        bind() from singleton { ForecastDatabase(instance()) } // instance is app context
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() } // getting instance from ForecastDatabase
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } //байндим ConnectivityInterceptor
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNerworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance(),instance()) } //2 instances 1 for CurrentWeatherDao  second for WeatherNerworkDataSource
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(),instance()) }
        bind() from factory{detailDate:LocalDate -> FutureDetailWeatherViewModelFactory(detailDate,instance(),instance())}
    }

    override fun onCreate(){
        super.onCreate()
        AndroidThreeTen.init(this) // time initialize for ZonedbaseTime
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false) //default value,it will be Metric system
    }
}