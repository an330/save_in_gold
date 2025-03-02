package com.example.saveingold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationView(url: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(url))

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}
