package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav


enum class DefaultAppScanState { INITIAL, TOOLTIP, SCANNING, SUCCESS, FILTERED }

@Composable
fun DefaultAppModeTrialScreen(
    currentScreen: AppScreen,
    messages: List<SmsMessage>,
    onNavigate: (AppScreen) -> Unit,
    onHome: () -> Unit
) {
    var state by remember { mutableStateOf(DefaultAppScanState.INITIAL) }
    var scanProgress by remember { mutableFloatStateOf(0f) }

    val allMessages = messages

    val displayedMessages = if (state == DefaultAppScanState.FILTERED) {
        allMessages.filter { it.sender == "Telkomsel" }
    } else {
        allMessages
    }

    LaunchedEffect(Unit) {
        delay(1000)
        if (state == DefaultAppScanState.INITIAL) {
            state = DefaultAppScanState.TOOLTIP
        }
    }

    LaunchedEffect(state) {
        if (state == DefaultAppScanState.SCANNING) {
            for (i in 0..100 step 2) {
                delay(40)
                scanProgress = i / 100f
            }
            state = DefaultAppScanState.SUCCESS
        }
    }

    val backgroundColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // Space for bottom nav
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Pesan SMS",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(16.dp),
                            tint = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cari nomor, pesan, dll...",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Filter",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayedMessages) { msg ->
                    MessageCard(
                        message = msg,
                        onClick = { }
                    )
                }
            }
        }

        SharedBottomNav(
            currentScreen = currentScreen,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Overlays based on state
        if (state == DefaultAppScanState.TOOLTIP || state == DefaultAppScanState.SCANNING || state == DefaultAppScanState.SUCCESS) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable(enabled = false) {}
            ) {
                if (state == DefaultAppScanState.TOOLTIP) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 120.dp) // Above FAB
                            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                            .background(Color(0xFF2C2C2C), RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Lakukan \"scan pesan\" untuk filter\ndan pelabelan pesan SMS",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { state = DefaultAppScanState.SCANNING },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Mulai", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                if (state == DefaultAppScanState.SCANNING) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Proses pelabelan pesan",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(
                            progress = { scanProgress },
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Color.White,
                            trackColor = Color.DarkGray,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${(scanProgress * 100).toInt()}%",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                if (state == DefaultAppScanState.SUCCESS) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Berhasil, pesan\ntelah dilabeli",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { state = DefaultAppScanState.FILTERED },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text("Kembali ke beranda", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
