package com.example.saveingold

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.saveingold.model.EducationCard
import com.example.saveingold.model.SaveButtonCta
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel) {
    val onboardingData by viewModel.onboardingData.collectAsState()

    onboardingData?.let { data ->
        val educationCards = data.educationCardList
        val expandDuration = data.expandCardStayInterval.toLong()

        if (educationCards.isNotEmpty()) {
            CardAnimationScreen(educationCards, expandDuration, data.saveButtonCta)
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun CardAnimationScreen(cards: List<EducationCard>, expandDuration: Long, cta: SaveButtonCta) {
    var expandedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        cards.forEachIndexed { index, card ->
            val isExpanded = index == expandedIndex
            CardItem(
                card = card,
                index= index,
                isExpanded = isExpanded,
                onClick = {
                    if (!isExpanded) {
                        expandedIndex = index
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        CTAButton(cta)
    }
}

@Composable
fun CardItem(card: EducationCard, index: Int, isExpanded: Boolean, onClick: () -> Unit) {
    val targetHeight = if (isExpanded) 444.dp else 100.dp
    val height by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "Card Expand Animation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) Color.White else Color.Transparent // No background in collapsed state
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), // Removed extra padding
            contentAlignment = Alignment.Center
        ) {
            if (isExpanded) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = card.image,
                        contentDescription = card.expandStateText,
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = card.expandStateText,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                val imageRes = when (index % 3) {
                    0 -> R.drawable.card80
                    1 -> R.drawable.card2
                    else -> R.drawable.card1
                }

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Card Image",
                    modifier = Modifier
                        .width(296.dp)
                        .height(100.dp) // Adjusted height to match collapsed state
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }
    }
}


@Composable
fun CTAButton(cta: SaveButtonCta) {
    Button(
        onClick = { /* Handle CTA click with cta.deeplink */ },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor(cta.backgroundColor)))
    ) {
        Text(text = cta.text, color = Color(android.graphics.Color.parseColor(cta.textColor)))
    }
}
