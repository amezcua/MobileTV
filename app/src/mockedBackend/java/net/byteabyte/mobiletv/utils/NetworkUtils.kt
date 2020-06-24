package net.byteabyte.mobiletv.utils

import net.byteabyte.mobiletv.mocked.StubInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

class NetworkUtils {
    companion object {
        fun buildOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { setLevel(BODY) })
                .addInterceptor(StubInterceptor())

            return builder.build()
        }
    }
}