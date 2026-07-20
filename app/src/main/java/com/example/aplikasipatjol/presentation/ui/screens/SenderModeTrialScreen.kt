package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SenderModeTrialScreen(
    onExit: () -> Unit,
    onHome: () -> Unit
) {
    var showSuccessDialog by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }
    
    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            delay(3000)
            showSuccessDialog = false
            messageText = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Main Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            
            // App Logo Placeholder
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock, 
                        contentDescription = null, 
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("PATJOL", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color.Black)
                    Text("Pesan Aman Tanpa Judi Online", fontSize = 10.sp, color = Color.Black)
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Title
            Text(
                text = "Mode Pengirim Pesan",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1877F2),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Instructions
            Text(
                text = "Silakan mengirimkan pesan yang akan disampaikan\nkepada penerima pesan\n\nPesan akan menyesuaikan dengan isi dari pesan. Jika\npesan yang diberikan mengandung judol atau sesuatu\nyang diwaspadai, akan ditempatkan kepada tempatnya.",
                fontSize = 13.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Keluar Button
            OutlinedButton(
                onClick = onExit,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFDA4833)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDA4833)),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text("Keluar Mode Pengirim Pesan", fontWeight = FontWeight.Medium)
            }
        }

        // Bottom Input Area
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F6F8), RoundedCornerShape(24.dp))
                    .padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (messageText.isEmpty()) {
                            Text(
                                text = "Tuliskan pesan...",
                                color = Color(0xFFA6A8AC),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                val isTyping = messageText.isNotEmpty()
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(if (isTyping) Color(0xFF1877F2) else Color.Black, CircleShape)
                        .clickable {
                            if (isTyping) {
                                showSuccessDialog = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier
                            .size(18.dp)
                            .offset(x = 2.dp, y = (-1).dp) // slightly adjust send icon
                    )
                }
            }
        }

        // Success Overlay Dialog
        if (showSuccessDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {}, // Intercept clicks
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFF61B54F), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Success",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "Pengiriman Pesan Berhasil",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Kembali ke pesan utama dalam 3 detik",
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
