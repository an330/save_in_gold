package com.example.saveingold
import com.example.saveingold.model.OnboardingResponse
import retrofit2.http.GET



interface OnboardingApi {
    @GET("v3/0a095cf2-a081-44af-965a-953b0fa6499b")
    suspend fun getOnboardingData(): OnboardingResponse
}
