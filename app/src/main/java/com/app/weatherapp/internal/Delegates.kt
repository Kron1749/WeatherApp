package com.app.weatherapp.internal

import kotlinx.coroutines.*


//будет создано только при вызове lazyDeferrred
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}