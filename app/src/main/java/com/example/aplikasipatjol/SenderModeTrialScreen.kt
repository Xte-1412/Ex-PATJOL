package com.example.aplikasipatjol

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Send
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

@Composable
fun SenderModeTrialScreen(
    onExit: () -> Unit,
    onHome: () -> Unit
) {
    var showSuccessDialog by remember { mutableStateOf(false) }
    var messageText by remember { 
        mutableStateOf(
            "Spe5!al b3r-ma!n har! In!\nDl jam11n 100% P4stl W3 d3e\nServ3r Tha1land bOC- OR\ns.id/bDFLw"
        )
    }
    
    // Background color matching the mockup
    val backgroundColor = Color(0xFFC0C0C0) 

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Main Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp), // Space for the bottom input area
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            // Profile Icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(64.dp),
                tint = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title
            Text(
                text = "Mode Pengirim Pesan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Instructions
            Column(
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "1. Silakan mengirimkan pesan yang akan disampaikan kepada penerima pesan",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2. Pesan akan menyesuaikan dengan isi dari pesan. Jika pesan yang diberikan mengandung judol atau sesuatu yang diwaspadai, akan ditempatkan kepada tempatnya.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Keluar Button
            OutlinedButton(
                onClick = onExit,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                modifier = Modifier.width(160.dp)
            ) {
                Text("Keluar", fontWeight = FontWeight.Bold)
            }
        }

        // Bottom Input Area
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (messageText.isEmpty()) {
                            Text(
                                text = "Ketik pesan",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Send",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (messageText.isNotEmpty()) {
                                showSuccessDialog = true
                            }
                        }
                )
            }
        }

        // Success Overlay
        if (showSuccessDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .clickable(enabled = false) {}, // Intercept clicks
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp),
                        tint = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "Berhasil, pesan\ntelah dikirim",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            messageText = "" // Clear message after sending
                            // If user clicks "Kembali ke beranda", we could navigate back
                            // or just close the dialog. The mockup shows staying on the screen 
                            // with empty text input, or maybe going back to home?
                            // Let's call onHome to navigate back.
                            onHome()
                        },
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
