package ru.xmn.kotlinstarter.application.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideCache(context: Context)
            = Cache(context.cacheDir, 10 * 1024 * 1024.toLong())

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient
            = OkHttpClient()
            .newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().build()
}


fun provideRestAdapter(client: OkHttpClient, url: String, moshi: Moshi): Retrofit
        = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

fun OkHttpClient.Builder.addParameterInterceptor(key: String, value: String): OkHttpClient.Builder {
    return this.apply {
        addInterceptor {
            val url = it.request().url().newBuilder().addQueryParameter(key, value).build()
            val request = it.request().newBuilder().url(url).build()
            it.proceed(request)
        }
    }
}

fun OkHttpClient.modify(modifyFunc: OkHttpClient.Builder.() -> OkHttpClient.Builder) = newBuilder().modifyFunc().build()!!