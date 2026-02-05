package com.example.cmpphonecall

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape/*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call*/
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun App() {
    MaterialTheme {
        val phoneCaller = rememberPhoneCaller()
        val phoneNumber = "1234567890"

        // Infinite transition for a subtle breathing effect on the icon
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F172A), // Very Dark Blue
                            Color(0xFF1E293B), // Dark Grayish Blue
                            Color(0xFF334155)  // Mid Gray Blue
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                // Animated Phone Icon in a Glowing Circle
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .background(Color(0xFF22C55E).copy(alpha = 0.2f), CircleShape)
                        .padding(20.dp)
                        .background(Color(0xFF22C55E).copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E)),
                        contentAlignment = Alignment.Center
                    ) {
                        /*Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call Icon",
                            modifier = Modifier.size(50.dp),
                            tint = Color.White
                        )*/
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Customer Support",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(60.dp))

                Button(
                    onClick = { phoneCaller.makeCall(phoneNumber) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxSize(0.8f)
                ) {
                    /*Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )*/
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        text = "START CALL",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
