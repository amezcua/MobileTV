package net.byteabyte.mobiletv.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.byteabyte.mobiletv.BuildConfig
import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.di.CoreModule
import net.byteabyte.mobiletv.data.MobileTVRepository
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.utils.NetworkUtils
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [CoreModule::class])
object MobileTvModule {

    @Provides
    @Singleton
    fun provideTmdbApi(): TMDBApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(NetworkUtils.buildOkHttpClient())
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTMDBNetwork(tmdbApi: TMDBApi): TMDBNetwork =
        TMDBNetwork(BuildConfig.TMDB_API_KEY, tmdbApi)

    @Module
    @InstallIn(ApplicationComponent::class)
    interface BindFunctions {
        @Binds
        @Singleton
        fun bindRepository(mobileTVRepository: MobileTVRepository): Repository
    }
}
