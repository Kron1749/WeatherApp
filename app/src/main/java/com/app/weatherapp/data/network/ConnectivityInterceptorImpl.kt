package com.app.weatherapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.app.weatherapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context // необходимо для проверки интернет connection
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext // только для приложения

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException() // выкидываем собственное исключение
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean { // проверка на онлайн
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        return networkInfo != null
    }
}