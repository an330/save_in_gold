package com.example.saveingold

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OnboardingRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(OnboardingApi::class.java)

    suspend fun fetchOnboardingData() = api.getOnboardingData()
}