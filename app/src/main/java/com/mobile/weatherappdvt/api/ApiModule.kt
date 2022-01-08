package com.mobile.weatherappdvt.api

import com.mobile.weatherappdvt.ui.weather.repository.CurrentWeatherRepository
import com.mobile.weatherappdvt.ui.weather.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val API_KEY = "9fede514eb1ce0607ba260c1c96cf81d"
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request.url
                    .newBuilder()
                    .addQueryParameter("APPID", API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()

    @Singleton
    @Provides
    fun provideRetroFit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideWebRequestManager(apiInterface: ApiInterface): WebRequestManager = WebRequestManager(apiInterface)

    @Singleton
    @Provides
    fun providesRepository(webRequestManager: WebRequestManager) =
        CurrentWeatherRepository(webRequestManager) as WeatherRepository
}