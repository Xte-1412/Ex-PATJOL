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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav
import kotlinx.coroutines.delay

enum class WaspadaTab { MENCURIGAKAN, JUDI_ONLINE }
enum class WaspadaScreenState { LIST, DETAIL }
enum class LaporanFlowState { HIDDEN, CONFIRMATION, LOADING, SUCCESS, PDF_VIEW }

@Composable
fun SpamMessagesTrialScreen(
    currentScreen: AppScreen,
    messages: List<SmsMessage>,
    onNavigate: (AppScreen) -> Unit,
    onHome: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(WaspadaTab.MENCURIGAKAN) }
    var screenState by remember { mutableStateOf(WaspadaScreenState.LIST) }
    var selectedMessage by remember { mutableStateOf<SmsMessage?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    
    // Laporan PDF State
    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedMessagesForReport by remember { mutableStateOf(setOf<SmsMessage>()) }
    var laporanState by remember { mutableStateOf(LaporanFlowState.HIDDEN) }

    LaunchedEffect(laporanState) {
        if (laporanState == LaporanFlowState.LOADING) {
            delay(2000)
            laporanState = LaporanFlowState.SUCCESS
        } else if (laporanState == LaporanFlowState.SUCCESS) {
            delay(3000)
            laporanState = LaporanFlowState.PDF_VIEW
            isSelectionMode = false
            selectedMessagesForReport = setOf()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (screenState == WaspadaScreenState.LIST) {
                // Top padding
                Spacer(modifier = Modifier.height(40.dp))

                // Toggle Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFE5E5E5)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tab 1
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedTab == WaspadaTab.MENCURIGAKAN) Color.Black else Color.Transparent)
                            .clickable { selectedTab = WaspadaTab.MENCURIGAKAN },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pesan Mencurigakan",
                            color = if (selectedTab == WaspadaTab.MENCURIGAKAN) Color.White else Color.Black,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == WaspadaTab.MENCURIGAKAN) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    
                    // Tab 2
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedTab == WaspadaTab.JUDI_ONLINE) Color.Black else Color.Transparent)
                            .clickable { selectedTab = WaspadaTab.JUDI_ONLINE },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pesan Judi Online",
                            color = if (selectedTab == WaspadaTab.JUDI_ONLINE) Color.White else Color.Black,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == WaspadaTab.JUDI_ONLINE) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar and Action Button
                if (isSelectionMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "x", 
                                modifier = Modifier
                                    .clickable { 
                                        isSelectionMode = false 
                                        selectedMessagesForReport = setOf()
                                    }
                                    .padding(end = 16.dp), 
                                fontSize = 16.sp, 
                                color = Color.Black
                            )
                            Text("${selectedMessagesForReport.size} Pesan dipilih", color = Color.Black, fontSize = 14.sp)
                        }
                        
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                                .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                                .clickable { 
                                    if (selectedMessagesForReport.isNotEmpty()) {
                                        laporanState = LaporanFlowState.CONFIRMATION 
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Buat Laporan PDF", color = Color.Black, fontSize = 12.sp)
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Search Bar
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .background(Color(0xFFE5E5E5), RoundedCornerShape(18.dp))
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp),
                                modifier = Modifier.weight(1f),
                                decorationBox = { innerTextField ->
                                    if (searchQuery.isEmpty()) {
                                        Text("Cari pesan...", color = Color(0xFFA6A8AC), fontSize = 12.sp)
                                    }
                                    innerTextField()
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(18.dp),
                                tint = Color.Black
                            )
                        }

                        if (selectedTab == WaspadaTab.JUDI_ONLINE) {
                            Spacer(modifier = Modifier.width(8.dp))
                            // Laporkan Pesan button
                            Row(
                                modifier = Modifier
                                    .height(36.dp)
                                    .background(Color(0xFFE5E5E5), RoundedCornerShape(18.dp))
                                    .padding(horizontal = 12.dp)
                                    .clickable { isSelectionMode = true },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Black, RoundedCornerShape(4.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("!", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 10.sp)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Laporkan Pesan", color = Color.Black, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // List of messages
                val displayedMessages = messages.filter { message ->
                    val matchesTab = if (selectedTab == WaspadaTab.MENCURIGAKAN) {
                        message.category == com.example.aplikasipatjol.domain.model.MessageCategory.SPAM || 
                        message.category == com.example.aplikasipatjol.domain.model.MessageCategory.PINJOL
                    } else {
                        message.category == com.example.aplikasipatjol.domain.model.MessageCategory.JUDOL
                    }
                    val matchesSearch = message.sender.contains(searchQuery, ignoreCase = true) || 
                                        message.snippet.contains(searchQuery, ignoreCase = true) ||
                                        message.fullBody.contains(searchQuery, ignoreCase = true)
                    matchesTab && matchesSearch
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 90.dp) // padding for bottom nav
                ) {
                    items(displayedMessages) { message ->
                        WaspadaMessageCard(
                            message = message,
                            tabType = selectedTab,
                            isSelectionMode = isSelectionMode,
                            isSelected = selectedMessagesForReport.contains(message),
                            onClick = {
                                if (isSelectionMode) {
                                    selectedMessagesForReport = if (selectedMessagesForReport.contains(message)) {
                                        selectedMessagesForReport - message
                                    } else {
                                        selectedMessagesForReport + message
                                    }
                                } else {
                                    selectedMessage = message
                                    screenState = WaspadaScreenState.DETAIL
                                }
                            }
                        )
                    }
                }

            } else if (screenState == WaspadaScreenState.DETAIL && selectedMessage != null) {
                WaspadaMessageDetailScreen(
                    message = selectedMessage!!,
                    tabType = selectedTab,
                    onBack = { screenState = WaspadaScreenState.LIST }
                )
            }
        }

        if (screenState == WaspadaScreenState.LIST) {
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                SharedBottomNav(
                    currentScreen = currentScreen,
                    onNavigate = onNavigate
                )
            }
        }
        
        // ===== LAPORAN PDF OVERLAYS =====
        if (laporanState == LaporanFlowState.CONFIRMATION) {
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
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Cetak Laporan Nomor\nuntuk aduannomor.id?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFC0C0C0), RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .clickable { laporanState = LaporanFlowState.LOADING }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Ya, cetak laporan Nomor PDF sekarang.", color = Color.Black, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        if (laporanState == LaporanFlowState.LOADING) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(64.dp), strokeWidth = 6.dp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Membuat laporan\nnomor PDF",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (laporanState == LaporanFlowState.SUCCESS) {
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
                        .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp)),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Pencetakan Laporan\nNomor Berhasil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Mengarah ke pesan\nwaspada dalam 3 detik",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .offset(y = (-90).dp)
                        .size(64.dp)
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        if (laporanState == LaporanFlowState.PDF_VIEW) {
            // PDF VIEW Mockup
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Close button
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        Icon(
                            imageVector = Icons.Default.Close, 
                            contentDescription = "Close PDF", 
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp).clickable { laporanState = LaporanFlowState.HIDDEN }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Logo PATJOL Mock
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(48.dp).border(2.dp, Color.Black).padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("PATJOL", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("PATJOL", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color.Black)
                            Text("Pesan Aman Tanpa Judi Online", fontSize = 10.sp, color = Color.Black)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "Bukti Laporan\nPesan Judi Online",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Box containing details
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Black)
                            .padding(16.dp)
                    ) {
                        Column {
                            Box(modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp)) {
                                Text("Nomor HP: +6281234567890", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp)) {
                                Text("Waktu kejadian: 10 Juli 2026, 10:10 PM", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(12.dp)) {
                                Column {
                                    Text("Isi pesan:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "NOMOR KAMU DPT SALDO 25K UTK MALAM INI!\nDFTR & KLAIM SKRG!\nPUTER 15X DI INCES ID BARU 100% PASTI WD\nPAS WD AKU KASI BONUS 50K LG!\nDISINI : gerak.in/tLgTDH",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Catatan:\n1. Dokumen ini sebagai bukti kuat adanya pesan judi\n   online yang masuk kepada pengguna SMS.\n2. Dokumen digunakan sebagai pendukung laporan\n   kepada aduannomor.id",
                            fontSize = 10.sp,
                            color = Color.Black,
                            lineHeight = 14.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                        Text("Tanggal Cetak Laporan", fontSize = 10.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("10 Juli 2026", fontSize = 10.sp, color = Color.Black)
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun WaspadaMessageCard(
    message: SmsMessage,
    tabType: WaspadaTab,
    isSelectionMode: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelectionMode && isSelected) Color(0xFFC0C0C0) else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.sender,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = message.time, // Using time to show right top text
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (tabType == WaspadaTab.JUDI_ONLINE) {
                    Text(
                        text = "(Isi pesan tidak ditampilkan oleh sistem. Klik Pesan untuk selengkapnya)",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 14.sp
                    )
                } else {
                    Text(
                        text = message.fullBody,
                        fontSize = 12.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        lineHeight = 14.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Badge
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "8",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun WaspadaMessageDetailScreen(
    message: SmsMessage,
    tabType: WaspadaTab,
    onBack: () -> Unit
) {
    var replyText by remember { mutableStateOf("") }
    
    // Banding state
    var bandingState by remember { mutableStateOf(BandingFlowState.HIDDEN) }
    var nama by remember { mutableStateOf("") }
    var noTelp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(bandingState) {
        if (bandingState == BandingFlowState.SUCCESS_BIODATA) {
            delay(3000)
            bandingState = BandingFlowState.FORM_BANDING
        } else if (bandingState == BandingFlowState.SUCCESS_BANDING) {
            delay(3000)
            bandingState = BandingFlowState.HIDDEN
            onBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 40.dp),
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = message.sender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            // Date Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE5E5E5))
                        .padding(horizontal = 24.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Hari ini",
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
                        .background(Color(0xFF8F8F8F))
                        .padding(16.dp)
                ) {
                    Column {
                        val textContent = if (tabType == WaspadaTab.JUDI_ONLINE) {
                            "AKU KSH KM SA_LDO 100K UTK MA_IN ID BA'RU 100% P'AS'TI DIKASIH MENANG SISTEM PG'SO_FT BAGI2 MA,XWI'N MA_IN MAHJ_ONG 5 MENIT WD 2 JT DP&KL,AIM SKRG: ****************"
                        } else {
                            message.fullBody
                        }

                        Text(
                            text = textContent,
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (tabType == WaspadaTab.JUDI_ONLINE) {
                                // Eye icon alternative (Visibility is extended icon, using Info)
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Visible",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            
                            Text(
                                text = message.time,
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
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
                    color = Color.Black
                )
                Text(
                    text = "Kunjungi Aju Banding Pesan",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { bandingState = BandingFlowState.PROMPT_BIODATA }
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
                    .background(Color(0xFFF8F9FA), RoundedCornerShape(24.dp))
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

        // ===== OVERLAYS (Reusing same UI as Main Message) =====

        if (bandingState == BandingFlowState.PROMPT_BIODATA) {
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
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Anda belum mengisi\nbiodata pengguna",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1877F2),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { bandingState = BandingFlowState.FORM_BIODATA },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1877F2),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Isi Biodata Pengguna sekarang",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        if (bandingState == BandingFlowState.FORM_BIODATA) {
            val isFormValid = nama.isNotEmpty() && noTelp.isNotEmpty() && email.isNotEmpty()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Biodata Pengguna",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Nama
                        Text(
                            text = androidx.compose.ui.text.buildAnnotatedString {
                                append("Nama")
                                withStyle(androidx.compose.ui.text.SpanStyle(color = Color(0xFFDA4833))) {
                                    append("*")
                                }
                            },
                            color = Color(0xFF1877F2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        BasicTextField(
                            value = nama,
                            onValueChange = { nama = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            textStyle = LocalTextStyle.current.copy(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // No Telp
                        Text(
                            text = androidx.compose.ui.text.buildAnnotatedString {
                                append("Nomor Telepon")
                                withStyle(androidx.compose.ui.text.SpanStyle(color = Color(0xFFDA4833))) {
                                    append("*")
                                }
                            },
                            color = Color(0xFF1877F2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            BasicTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier
                                    .weight(0.25f)
                                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                    .padding(12.dp),
                                textStyle = LocalTextStyle.current.copy(color = Color.Black)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            BasicTextField(
                                value = noTelp,
                                onValueChange = { noTelp = it },
                                modifier = Modifier
                                    .weight(0.75f)
                                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                    .padding(12.dp),
                                textStyle = LocalTextStyle.current.copy(color = Color.Black)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Email
                        Text(
                            text = androidx.compose.ui.text.buildAnnotatedString {
                                append("Email")
                                withStyle(androidx.compose.ui.text.SpanStyle(color = Color(0xFFDA4833))) {
                                    append("*")
                                }
                            },
                            color = Color(0xFF1877F2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        BasicTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            textStyle = LocalTextStyle.current.copy(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(
                                onClick = {
                                    if (isFormValid) {
                                        bandingState = BandingFlowState.SUCCESS_BIODATA
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFormValid) Color(0xFF1877F2) else Color(0xFFCBD5E1),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Simpan",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        if (bandingState == BandingFlowState.SUCCESS_BIODATA) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp)),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Pengisian\nBiodata Berhasil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Mengarah ke formulir pengajuan\nbanding pesan dalam 3 detik",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .offset(y = (-90).dp)
                        .size(64.dp)
                        .background(Color(0xFF61B54F), RoundedCornerShape(8.dp))
                        .border(4.dp, Color.White, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        if (bandingState == BandingFlowState.FORM_BANDING) {
            val options = listOf(
                "Pesan mengandung judol",
                "Pesan mencurigakan (pinjol/phising)",
                "Link Judol tidak tersensor dengan baik",
                "Lainnnya (mohon isi dengan detail)"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Ada apa dengan\npesan ini?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Aju banding akan diberikan kepada\ndeveloper untuk perbaikan sistem.",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        options.forEachIndexed { index, text ->
                            val isSelected = selectedOption == index
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .border(1.dp, if (isSelected) Color(0xFF1877F2) else Color.Black, RoundedCornerShape(8.dp))
                                    .clickable { selectedOption = index }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .border(2.dp, if (isSelected) Color(0xFF1877F2) else Color.Black, CircleShape)
                                        .padding(3.dp)
                                ) {
                                    if (isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color(0xFF1877F2), CircleShape)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = text,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = { bandingState = BandingFlowState.HIDDEN },
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Batal", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = {
                                    if (selectedOption != null) {
                                        bandingState = BandingFlowState.SUCCESS_BANDING
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedOption != null) Color(0xFF1877F2) else Color(0xFFCBD5E1),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    "Ajukan banding",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        if (bandingState == BandingFlowState.SUCCESS_BANDING) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp)),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Pengajuan Banding\nPesan Berhasil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Mengarah ke pesan utama\ndalam 3 detik",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .offset(y = (-90).dp)
                        .size(64.dp)
                        .background(Color(0xFF61B54F), RoundedCornerShape(8.dp))
                        .border(4.dp, Color.White, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}
