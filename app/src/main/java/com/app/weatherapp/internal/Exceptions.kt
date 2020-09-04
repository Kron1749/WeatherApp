package com.app.weatherapp.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException:IOException() // собственное исключение
class LocationPermissionNotGrantedException:Exception()
class DateNotFoundException:Exception()