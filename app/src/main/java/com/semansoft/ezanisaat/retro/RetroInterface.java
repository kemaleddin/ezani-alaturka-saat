package com.semansoft.ezanisaat.retro;

import com.semansoft.ezanisaat.model.City;
import com.semansoft.ezanisaat.model.Country;
import com.semansoft.ezanisaat.model.TimesOfDay;
import com.semansoft.ezanisaat.model.Town;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public interface RetroInterface {

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("/ulkeler")
    Call<List<Country>> getCountries();

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("/sehirler")
    Call<List<City>> getCities(@Query("ulke") String code);

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("/ilceler")
    Call<List<Town>> getTowns(@Query("sehir") String code);

    @Headers( "Content-Type: application/json; charset=utf-8" )
    @GET("/vakitler")
    Call<List<TimesOfDay>> getTimes(@Query("ilce") String code);



}
