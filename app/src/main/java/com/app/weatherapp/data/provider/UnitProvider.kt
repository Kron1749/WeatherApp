package com.app.weatherapp.data.provider

import com.app.weatherapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}