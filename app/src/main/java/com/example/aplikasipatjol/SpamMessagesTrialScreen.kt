package com.example.aplikasipatjol

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class SpamScreenStep { WARNING, CATEGORY, LIST, TOOLTIP_EYE, TOOLTIP_BANDING, DETAIL }

@Composable
fun SpamMessagesTrialScreen(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    onHome: () -> Unit
) {
    var step by remember { mutableStateOf(SpamScreenStep.WARNING) }

    val mockSpamMessages = List(3) {
        SmsMessage(
            id = it,
            sender = "+62123456789",
            date = "02 Juni 2025",
            snippet = "",
            fullBody = "AKU KSH KM SA_LDO 200K UTK MA_IN ID BARU 100% PASTI DIKASIH MENANG SISTEM P3C4H, PT BAND MAJW78 MAJIN MAKLUM 5 MENIT WD 2JT DP08LAIM SEKR. **********",
            time = "10:00 PM"
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFC0C0C0))) {
        when (step) {
            SpamScreenStep.WARNING, SpamScreenStep.CATEGORY -> {
                // Category Screen is the base
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    
                    // Box 1
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .clickable { if (step == SpamScreenStep.CATEGORY) step = SpamScreenStep.LIST }
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Pesan Terdeteksi Judol", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Box 2
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Pesan Diwaspadai", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    }
                }

                // Bottom Nav
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    SharedBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = onNavigate
                    )
                }

                if (step == SpamScreenStep.WARNING) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.85f))
                            .clickable(enabled = false) {},
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning",
                                tint = Color.White,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Box(
                                modifier = Modifier
                                    .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                                    .padding(24.dp)
                            ) {
                                Column {
                                    Text("Pesan Terdeteksi Judol", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text("1. Anda memasuki area pesan yang mengandung beberapa hal seperti judol, pinjol, dkk.", color = Color.White, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("2. Link yang tertera pada pesan judol/pinjol akan kami sensor.", color = Color.White, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("3. Pesan judol hanya ditampilkan setelah menekan \"Selengkapnya\".", color = Color.White, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("4. Sistem belum bisa mendeteksi link yang mengandung malware/phising selain judol/pinjol. Selalu waspada dan hati-hati.", color = Color.White, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("5. Laporkan pesan yang mengandung judol kepada aduannomor.id", color = Color.White, fontSize = 12.sp)
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            Button(
                                onClick = { step = SpamScreenStep.CATEGORY },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.width(200.dp)
                            ) {
                                Text("Ya, Saya paham", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            SpamScreenStep.LIST -> {
                Column(modifier = Modifier.fillMaxSize().padding(bottom = 100.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { step = SpamScreenStep.CATEGORY }, tint = Color.Black)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Daftar Pesan Judol", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                        }
                        Box(
                            modifier = Modifier.background(Color.Black, RoundedCornerShape(16.dp)).padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Laporkan", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(mockSpamMessages) { msg ->
                            Box(
                                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).clickable { step = SpamScreenStep.TOOLTIP_EYE }.padding(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Black)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(text = msg.sender, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                                            Text(text = msg.date, fontSize = 12.sp, color = Color.DarkGray)
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(text = "Lihat detail ->", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black, textDecoration = TextDecoration.Underline, modifier = Modifier.align(Alignment.End))
                                    }
                                }
                            }
                        }
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) { 
                    SharedBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = onNavigate
                    )
                }
            }

            SpamScreenStep.DETAIL, SpamScreenStep.TOOLTIP_EYE, SpamScreenStep.TOOLTIP_BANDING -> {
                val message = mockSpamMessages[0]
                
                Column(modifier = Modifier.fillMaxSize().padding(bottom = 100.dp)) {
                    // Top Bar
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 32.dp, start = 16.dp, end = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clickable { step = SpamScreenStep.LIST }, tint = Color.Black)
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = message.sender, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Date Pill
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White).padding(horizontal = 16.dp, vertical = 6.dp)) {
                            Text(text = message.date, fontSize = 12.sp, color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Message Bubble
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        Box(modifier = Modifier.weight(0.85f, fill = false).clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)).background(Color.White).padding(16.dp)) {
                            Column {
                                Text(text = message.fullBody, fontSize = 14.sp, color = Color.Black, lineHeight = 20.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = message.time, fontSize = 10.sp, color = Color.Gray)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(imageVector = Icons.Default.Info, contentDescription = "Info", modifier = Modifier.size(16.dp), tint = Color.Black)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Footer Text
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Terdapat kesalahan pada sistem?", fontSize = 12.sp, color = Color.DarkGray)
                        Text(text = "Kunjungi Aju Banding Pesan", fontSize = 12.sp, color = Color.Black, textDecoration = TextDecoration.Underline)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Bottom Input
                Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)).background(Color.White).padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(12.dp)).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Ketik pesan", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send", tint = Color.Black, modifier = Modifier.size(24.dp))
                    }
                }
                
                // Tooltips
                if (step == SpamScreenStep.TOOLTIP_EYE || step == SpamScreenStep.TOOLTIP_BANDING) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)).clickable(enabled = false) {}) {
                        if (step == SpamScreenStep.TOOLTIP_EYE) {
                            Box(modifier = Modifier.align(Alignment.Center).padding(start = 32.dp, end = 32.dp, top = 64.dp).border(1.dp, Color.White, RoundedCornerShape(16.dp)).background(Color(0xFF2C2C2C), RoundedCornerShape(16.dp)).padding(16.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "Analisis pesan untuk mengetahui alasan dari\npelabelan pesan tersebut", color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(onClick = { step = SpamScreenStep.TOOLTIP_BANDING }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black), shape = RoundedCornerShape(16.dp), modifier = Modifier.height(32.dp)) {
                                        Text("Mulai", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        
                        if (step == SpamScreenStep.TOOLTIP_BANDING) {
                            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(start = 32.dp, end = 32.dp, bottom = 180.dp).border(1.dp, Color.White, RoundedCornerShape(16.dp)).background(Color(0xFF2C2C2C), RoundedCornerShape(16.dp)).padding(16.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "Klik untuk melakukan aju banding jika pesan tersebut\ntidak mengandung unsur judol", color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(onClick = { step = SpamScreenStep.DETAIL }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black), shape = RoundedCornerShape(16.dp), modifier = Modifier.height(32.dp)) {
                                        Text("Mengerti", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


