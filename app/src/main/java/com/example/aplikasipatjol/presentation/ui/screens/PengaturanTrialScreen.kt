package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav

@Composable
fun PengaturanTrialScreen(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    onExitTrial: () -> Unit
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
                text = "Pengaturan Aplikasi",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Menu Items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                SettingMenuItem(title = "Isi Biodata")
                Spacer(modifier = Modifier.height(16.dp))
                SettingMenuItem(title = "Tentang Aplikasi")
                Spacer(modifier = Modifier.height(16.dp))
                SettingMenuItem(title = "Panduan Penggunaan Aplikasi")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Exit Trial Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0)) // Light Grey
                    .clickable { onExitTrial() }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Keluar Mode Penerima Pesan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "(hanya di mode uji coba)",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color.LightGray,
                thickness = 1.dp
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Feedback Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFD9D9D9)) // Light Grey
                    .clickable { /* Handle feedback click */ }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Mail Icon Box
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Feedback Aplikasi PATJOL",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Silakan tekan tombol ini untuk memberikan saran dan masukan untuk aplikasi PATJOL.",
                            fontSize = 11.sp,
                            color = Color.DarkGray,
                            lineHeight = 14.sp
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
fun SettingMenuItem(title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Black
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Black
        )
    }
}
