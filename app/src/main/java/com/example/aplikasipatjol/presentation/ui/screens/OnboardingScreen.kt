package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import com.example.aplikasipatjol.presentation.viewmodel.SmsViewModel
import com.example.aplikasipatjol.presentation.ui.components.SharedBottomNav

enum class AppScreen { Splash, Onboarding, SystemModeSelection, UserModeSelection, SenderModeTrial, ReceiverModeTrial, DefaultAppModeTrial, SpamMessagesTrial, StatistikTrial, PengaturanTrial, MainApp }

@Composable
fun MainScreen(viewModel: SmsViewModel? = null) {
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }
    
    val inboxMessages by viewModel?.inboxMessages?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf(emptyList()) }
    val spamMessages by viewModel?.spamMessages?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        delay(2500)
        currentScreen = AppScreen.Onboarding
    }

    when (currentScreen) {
        AppScreen.Splash -> SplashScreen()
        AppScreen.Onboarding -> OnboardingScreen(onFinish = { currentScreen = AppScreen.SystemModeSelection })
        AppScreen.SystemModeSelection -> SystemModeSelectionScreen(
            onModeSelected = { mode ->
                if (mode == "Mode Trial") {
                    currentScreen = AppScreen.UserModeSelection
                } else if (mode == "Set Default App") {
                    currentScreen = AppScreen.DefaultAppModeTrial
                } else {
                    currentScreen = AppScreen.MainApp
                }
            }
        )
        AppScreen.DefaultAppModeTrial -> DefaultAppModeTrialScreen(
            currentScreen = currentScreen,
            messages = inboxMessages,
            onNavigate = { currentScreen = it },
            onHome = { currentScreen = AppScreen.SystemModeSelection }
        )
        AppScreen.UserModeSelection -> UserModeSelectionScreen(
            onModeSelected = { userMode ->
                if (userMode == "Pengirim Pesan") {
                    currentScreen = AppScreen.SenderModeTrial
                } else if (userMode == "Penerima Pesan") {
                    currentScreen = AppScreen.ReceiverModeTrial
                } else {
                    currentScreen = AppScreen.MainApp
                }
            }
        )
        AppScreen.ReceiverModeTrial -> ReceiverModeTrialScreen(
            currentScreen = currentScreen,
            messages = inboxMessages,
            onNavigate = { currentScreen = it },
            onBack = { currentScreen = AppScreen.UserModeSelection }
        )
        AppScreen.SpamMessagesTrial -> SpamMessagesTrialScreen(
            currentScreen = currentScreen,
            messages = spamMessages,
            onNavigate = { currentScreen = it },
            onHome = { currentScreen = AppScreen.SystemModeSelection }
        )
        AppScreen.StatistikTrial -> DummyNavScreen(
            title = "Statistik",
            currentScreen = currentScreen,
            onNavigate = { currentScreen = it }
        )
        AppScreen.PengaturanTrial -> DummyNavScreen(
            title = "Pengaturan",
            currentScreen = currentScreen,
            onNavigate = { currentScreen = it }
        )
        AppScreen.SenderModeTrial -> SenderModeTrialScreen(
            onExit = { currentScreen = AppScreen.Splash }, // Go back to start or whatever is appropriate
            onHome = { currentScreen = AppScreen.UserModeSelection } // "Kembali ke beranda" -> User selection
        )
        AppScreen.MainApp -> {
            // Placeholder for the next screen after onboarding and selection
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Masuk ke Aplikasi Utama")
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Mock logo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "PATJOL",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "Pesan Aman Tanpa Judi Online",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

data class OnboardingPage(
    val titleNumber: String,
    val titleText: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit = {}) {
    val pages = listOf(
        OnboardingPage(
            titleNumber = "1",
            titleText = "Deteksi",
            description = "Sistem mendeteksi pesan judi online secara akurat."
        ),
        OnboardingPage(
            titleNumber = "2",
            titleText = "Proteksi",
            description = "Sensor tautan yang diberikan oleh pesan judol."
        ),
        OnboardingPage(
            titleNumber = "3",
            titleText = "Lapor",
            description = "Cetak laporan nomor judol kepada aduannomor.id"
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(page = pages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicators
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(pages.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(if (isActive) Color(0xFF1877F2) else Color.White)
                            .border(2.dp, Color(0xFF1877F2), CircleShape)
                    )
                }
            }

            // Next / Mulai button
            if (pagerState.currentPage == pages.size - 1) {
                OutlinedButton(
                    onClick = onFinish,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color(0xFF1877F2)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1877F2)),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
                ) {
                    Text("Mulai", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Lanjut", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = page.titleNumber,
                fontSize = 120.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1877F2),
                modifier = Modifier.offset(y = (-24).dp) // Adjust baseline alignment
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = page.titleText,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1877F2)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun DummyNavScreen(title: String, currentScreen: AppScreen, onNavigate: (AppScreen) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Halaman $title", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
        }
        SharedBottomNav(
            currentScreen = currentScreen,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
