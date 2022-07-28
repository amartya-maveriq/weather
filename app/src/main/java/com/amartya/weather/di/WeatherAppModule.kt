package com.amartya.weather.di

import android.content.Context
import androidx.room.Room
import com.amartya.weather.BuildConfig
import com.amartya.weather.R
import com.amartya.weather.data.local.PlaceDao
import com.amartya.weather.data.local.WeatherDatabase
import com.amartya.weather.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by Amartya Ganguly on 27/06/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object WeatherAppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val url = request.url().newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
            chain.proceed(request.newBuilder().url(url.build()).build())
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(header: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(header)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            context.getString(R.string.db_name)
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePlaceDao(db: WeatherDatabase): PlaceDao =
        db.placeDao()
}