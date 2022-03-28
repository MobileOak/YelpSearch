package com.mobileoak.yelpsearch.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("businesses/search")
    fun searchBusinesses(
        @Header("Authorization") authKey: String,
        @Query("location") location: String,
        @Query("term") searchTerm: String)
    : Call<SearchResponseObject>

    companion object {
        val AUTH_KEY = "Bearer O5H9A0D9qSnN2Q-MQerhCuUljXnNlRaYGZdxv0HM5SLfznvtTDj_lwhMg-_RF7Tq-pwB7-KeNvoFRDoEL0Or7xndhButRGOohZn2l8nanLQAIIe2MISSmvw525SmYXYx"
        var BASE_URL = "https://api.yelp.com/v3/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}