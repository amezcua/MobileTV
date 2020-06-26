package net.byteabyte.mobiletv.data.network.parsing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

internal inline fun <reified T> buildMoshiAdapter() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
    .adapter(T::class.java)