package com.sahnisemanyazilim.ezanisaat.retro

import android.content.Context
import com.google.gson.GsonBuilder
import com.sahnisemanyazilim.ezanisaat.R
import com.sahnisemanyazilim.ezanisaat.model.City
import com.sahnisemanyazilim.ezanisaat.model.Country
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay
import com.sahnisemanyazilim.ezanisaat.model.Town
import com.sahnisemanyazilim.ezanisaat.retro.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
interface Api {
    companion object {
        private var api: Api?=null
        private val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        fun getApiInstance(app: Context):Api{
            if(api!=null)
                return api!!
            var client= OkHttpClient.Builder()
                .addInterceptor(NetworkConnectionInterceptor(app))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
            var retrofit: Retrofit? = Retrofit.Builder()
                .baseUrl(app.getString(R.string.base_url))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            api = retrofit!!.create(Api::class.java)
            return api!!
        }
    }

    @get:GET("/ulkeler")
    @get:Headers("Content-Type: application/json; charset=utf-8")
    val countries: Call<List<Country?>?>?

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/sehirler")
    fun getCities(@Query("ulke") code: String?): Call<List<City?>?>?

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/ilceler")
    fun getTowns(@Query("sehir") code: String?): Call<List<Town?>?>?

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/vakitler")
    fun getTimes(@Query("ilce") code: String?): Call<List<TimesOfDay?>?>?
}