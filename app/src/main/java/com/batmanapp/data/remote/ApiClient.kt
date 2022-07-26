package com.batmanapp.data.remote


import com.batmanapp.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    companion object {

        const val BASE_URL = "https://www.omdbapi.com/"

        private const val REQUEST_TIMEOUT: Long = 60

        fun getClient(): Retrofit {

            val httpClient = configHTTPClient()
            val gson = configGson()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        private fun configHTTPClient(): OkHttpClient {

            val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            httpClient.addInterceptor(interceptor)

            //httpClient.addInterceptor(AuthorizationInterceptor(PrefsHelper.credential))

            httpClient.addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("os", "Android")
                    .addQueryParameter("app_version", BuildConfig.VERSION_CODE.toString())
                    .addQueryParameter("build_type", BuildConfig.BUILD_TYPE)
                    .build()

                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder().url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)

            }

            return httpClient.build()
        }


        private fun configGson(): Gson {
            return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create()
        }


    }


}

