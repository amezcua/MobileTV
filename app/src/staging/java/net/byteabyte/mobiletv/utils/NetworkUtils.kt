package net.byteabyte.mobiletv.utils

import net.byteabyte.mobiletv.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkUtils {
    companion object {
        fun buildOkHttpClient(): OkHttpClient {
            val logLevel = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

            val builder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(logLevel)
                })

            return builder.build()
        }
    }
}