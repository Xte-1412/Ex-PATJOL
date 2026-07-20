package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav
import kotlinx.coroutines.delay

enum class ReceiverFlowState { EMPTY, PERMISSION_DIALOG, LOADING, SUCCESS }

@Composable
fun ReceiverModeTrialScreen(
    currentScreen: AppScreen,
    messages: List<SmsMessage>,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit
) {
    var flowState by remember { mutableStateOf(ReceiverFlowState.EMPTY) }
    var selectedMessageId by remember { mutableStateOf<Int?>(null) }

    // Auto-transition from loading to success
    LaunchedEffect(flowState) {
        if (flowState == ReceiverFlowState.LOADING) {
            delay(3000)
            flowState = ReceiverFlowState.SUCCESS
        }
        if (flowState == ReceiverFlowState.SUCCESS) {
            delay(3000)
            // After success, navigate to message list (show messages)
            flowState = ReceiverFlowState.EMPTY
            selectedMessageId = -1 // Marker to show message list
        }
    }

    val showMessageList = selectedMessageId == -1

    if (showMessageList) {
        // Show message list after successful flow
        MessageListScreen(
            messages = messages,
            backgroundColor = Color.White,
            onMessageClick = { selectedMessageId = it.id },
            currentScreen = currentScreen,
            onNavigate = onNavigate,
            onBack = onBack
        )
    } else if (selectedMessageId != null && selectedMessageId != -1) {
        val message = messages.find { it.id == selectedMessageId }
        if (message != null) {
            MessageDetailScreen(
                message = message,
                backgroundColor = Color.White,
                onBack = { selectedMessageId = -1 }
            )
        }
    } else {
        // Empty state + flow overlays
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Main empty state content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp, start = 32.dp, end = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Tidak ada pesan. Pastikan anda\nmenekan tombol\n\"Simulasi Aplikasi Default SMS\"",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { flowState = ReceiverFlowState.PERMISSION_DIALOG },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        "Simulasi Aplikasi Default SMS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // Bottom Navigation
            SharedBottomNav(
                currentScreen = currentScreen,
                onNavigate = onNavigate,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            // ===== OVERLAY STATES =====

            // Permission Dialog Overlay
            if (flowState == ReceiverFlowState.PERMISSION_DIALOG) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                        .clickable(enabled = false) {},
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(28.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Berikan izin untuk\nakses pesan SMS?",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1877F2),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Note box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFB4DCFF), RoundedCornerShape(12.dp))
                                    .border(2.dp, Color(0xFF1877F2), RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Catatan: hanya untuk simulasi saja. Mode \"Set Default SMS App\" masih dalam tahap proses pengembangan",
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    lineHeight = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Buttons row
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.weight(1f))

                                OutlinedButton(
                                    onClick = { flowState = ReceiverFlowState.EMPTY },
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                                ) {
                                    Text("Tidak", fontWeight = FontWeight.Medium)
                                }

                                Button(
                                    onClick = { flowState = ReceiverFlowState.LOADING },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF1877F2),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 10.dp)
                                ) {
                                    Text("Ya", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // Loading Overlay
            if (flowState == ReceiverFlowState.LOADING) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2A2A2A))
                        .clickable(enabled = false) {},
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color.White,
                            trackColor = Color.Gray,
                            strokeWidth = 4.dp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Memuat Pesan",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Bottom nav still visible in loading
                    SharedBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = { }, // Disabled during loading
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }

            // Success Overlay
            if (flowState == ReceiverFlowState.SUCCESS) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2A2A2A))
                        .clickable(enabled = false) {},
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
                                text = "Pemindahan Pesan Berhasil",
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

                    // Bottom nav still visible in success
                    SharedBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = { }, // Disabled during success
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun MessageListScreen(
    messages: List<SmsMessage>,
    backgroundColor: Color,
    onMessageClick: (SmsMessage) -> Unit,
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit
) {
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
            
            Text(
                text = "Daftar Pesan SMS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { msg ->
                    MessageCard(
                        message = msg,
                        onClick = { onMessageClick(msg) }
                    )
                }
            }
        }

        // Shared Bottom Navigation
        SharedBottomNav(
            currentScreen = currentScreen,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun MessageCard(message: SmsMessage, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F6F8))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color(0xFF1877F2)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.sender,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = message.date,
                        fontSize = 12.sp,
                        color = Color(0xFFA6A8AC)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.snippet,
                    fontSize = 12.sp,
                    color = Color(0xFFA6A8AC),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Selengkapnya ->",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1877F2),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Composable
fun MessageDetailScreen(
    message: SmsMessage,
    backgroundColor: Color,
    onBack: () -> Unit
) {
    var replyText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Space for bottom input
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF1877F2)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = message.sender,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Date Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF5F6F8))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = message.date,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Message Bubble
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.85f, fill = false)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(Color(0xFFF5F6F8))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = message.fullBody,
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message.time,
                            fontSize = 10.sp,
                            color = Color(0xFFA6A8AC),
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer Text
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Terdapat kesalahan pada sistem?",
                    fontSize = 12.sp,
                    color = Color(0xFFA6A8AC)
                )
                Text(
                    text = "Kunjungi Aju Banding Pesan",
                    fontSize = 12.sp,
                    color = Color(0xFF1877F2),
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Input Area
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F6F8), RoundedCornerShape(24.dp))
                    .padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = replyText,
                    onValueChange = { replyText = it },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (replyText.isEmpty()) {
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
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier
                            .size(18.dp)
                            .offset(x = 2.dp, y = (-1).dp)
                    )
                }
            }
        }
    }
}
