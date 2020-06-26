package net.byteabyte.mobiletv

import android.app.Activity

inline fun <reified T> Activity.extra(key: String): Lazy<T> = lazy {
    val value = intent.extras?.get(key)
    if (value is T) {
        value
    } else {
        throw IllegalArgumentException(
            "Couldn't find extra with key \"$key\" from type " +
                    T::class.java.canonicalName
        )
    }
}