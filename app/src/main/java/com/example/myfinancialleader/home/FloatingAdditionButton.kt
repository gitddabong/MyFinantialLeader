package com.example.myfinancialleader.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfinancialleader.R

@Composable
fun FloatingAdditionButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .size(100.dp)
            .padding(20.dp)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
        contentPadding = PaddingValues(5.dp),
        onClick = {
            onClick.invoke()
        }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.icon_plus_round),
            contentDescription = "expenseAddition",
            colorFilter = ColorFilter.tint(
                color = Color.White,
            ),
            contentScale = ContentScale.Crop
        )
    }
}