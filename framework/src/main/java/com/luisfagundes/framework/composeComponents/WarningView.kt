package com.luisfagundes.framework.composeComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun WarningView(
    modifier: Modifier = Modifier,
    title: String,
    bodyMessage: String = "",
    animationId: Int,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieErrorAnimation(animationId)
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        if (bodyMessage.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = bodyMessage,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LottieErrorAnimation(animationId: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationId),
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )

    LottieAnimation(
        enableMergePaths = true,
        outlineMasksAndMattes = true,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(150.dp),
        composition = composition,
        progress = { progress },
    )
}
