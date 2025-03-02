package com.example.saveingold

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saveingold.model.OnboardingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private val _onboardingData = MutableStateFlow<OnboardingData?>(null)
    val onboardingData: StateFlow<OnboardingData?> = _onboardingData


    init {
        fetchOnboardingData()
    }

    private fun fetchOnboardingData() {
        viewModelScope.launch {
            try {
                val response = OnboardingRepository.fetchOnboardingData()
                if (response.success) {
                    _onboardingData.value = response.data.onboardingData
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}