package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav

@Composable
fun StatistikTrialScreen(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // space for bottom nav
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            Text(
                text = "Statistik Pesan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1877F2)
            )
            
            Spacer(modifier = Modifier.height(60.dp))
            
            // Pie Chart and Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Simple Pie Chart via Canvas
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val sweepAngle1 = 180f // 50%
                        val sweepAngle2 = 108f // 30%
                        val sweepAngle3 = 72f  // 20%
                        
                        // Draw arcs
                        drawArc(color = Color(0xFF167700), startAngle = -90f, sweepAngle = sweepAngle1 - 2f, useCenter = true)
                        drawArc(color = Color(0xFF909E1B), startAngle = 90f, sweepAngle = sweepAngle2 - 2f, useCenter = true)
                        drawArc(color = Color(0xFF8C1D0D), startAngle = 198f, sweepAngle = sweepAngle3 - 2f, useCenter = true)
                    }
                    
                    Text("xx%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.offset(x = 30.dp, y = 0.dp))
                    Text("xx%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.offset(x = (-20).dp, y = 30.dp))
                    Text("xx%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.offset(x = (-20).dp, y = (-25).dp))
                }
                
                Spacer(modifier = Modifier.width(32.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BulletText("Hijau: 50 pesan utama")
                    BulletText("Kuning: 30 pesan mencurigakan")
                    BulletText("Merah: 20 pesan judi online")
                }
            }
            
            Spacer(modifier = Modifier.height(60.dp))
            
            // Waktu Rawan Card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(Color(0xFFD9D9D9)) // Light Grey background
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Clock Icon (large)
                    Canvas(modifier = Modifier.size(80.dp)) {
                        drawCircle(color = Color.Black)
                        // Minute hand (pointing up)
                        drawLine(
                            color = Color(0xFFD9D9D9),
                            start = center,
                            end = center.copy(y = center.y - 20.dp.toPx()),
                            strokeWidth = 6.dp.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                        // Hour hand (pointing down-right)
                        drawLine(
                            color = Color(0xFFD9D9D9),
                            start = center,
                            end = center.copy(x = center.x + 15.dp.toPx(), y = center.y + 15.dp.toPx()),
                            strokeWidth = 6.dp.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(24.dp))
                    
                    Column {
                        Text(
                            text = "Waktu Rawan",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Pesan Judol",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Jam 20:00 (Malam)",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SharedBottomNav(
                currentScreen = currentScreen,
                onNavigate = onNavigate,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BulletText(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}
