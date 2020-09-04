package com.app.weatherapp.data.db

import android.content.Context
import androidx.room.*
import com.app.weatherapp.data.db.entity.CurrentWeatherEntry
import com.app.weatherapp.data.db.entity.FutureWeatherEntry
import com.app.weatherapp.data.db.entity.WeatherLocation

@Database( // Объявляем что это датабаза
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile
        private var instance: ForecastDatabase? =
            null // т.к volatile все threads будут иметь доступ
        private val LOCK = Any() // Lock object нужен т.к будем использовать threads

        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) { // Проверяем instance на null
                // если null то след.строка
                // if not null - return instance
                instance ?: buildDatabase(context).also {
                    instance = it
                } // if null = initialize our db
            }

        private fun buildDatabase(context: Context) = // fun for create our db
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java, "futureWeatherEntries.db"
            )
                .build()
    }
}