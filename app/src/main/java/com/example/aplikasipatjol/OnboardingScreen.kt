package com.example.aplikasipatjol

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Lock
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

enum class AppScreen { Splash, Onboarding, SystemModeSelection, UserModeSelection, SenderModeTrial, ReceiverModeTrial, DefaultAppModeTrial, SpamMessagesTrial, MainApp }

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }

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
            onBack = { currentScreen = AppScreen.UserModeSelection },
            onWaspadaClick = { currentScreen = AppScreen.SpamMessagesTrial }
        )
        AppScreen.SpamMessagesTrial -> SpamMessagesTrialScreen(
            onHome = { currentScreen = AppScreen.ReceiverModeTrial }
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
    val titleIcon: ImageVector,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit = {}) {
    val pages = listOf(
        OnboardingPage(
            titleNumber = "1",
            titleText = "Deteksi",
            titleIcon = Icons.Default.Search,
            description = "Jelasin fitur pertama kita seperti apa ya pokoknya begitulah ya"
        ),
        OnboardingPage(
            titleNumber = "2",
            titleText = "Proteksi",
            titleIcon = Icons.Default.Lock,
            description = "Jelasin fitur kedua kita seperti apa ya pokoknya begitulah ya"
        ),
        OnboardingPage(
            titleNumber = "3",
            titleText = "Lapor",
            titleIcon = Icons.Default.Info,
            description = "Jelasin fitur ketiga kita seperti apa ya pokoknya begitulah ya"
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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(pages.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(if (isActive) Color.Black else Color.Transparent)
                            .border(2.dp, Color.Black, CircleShape)
                    )
                }
            }

            // Next / Mulai button
            if (pagerState.currentPage == pages.size - 1) {
                Button(
                    onClick = onFinish,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
                ) {
                    Text("Mulai", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            } else {
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text("Next ->", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                color = Color.Black,
                modifier = Modifier.offset(y = (-24).dp) // Adjust baseline alignment
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = page.titleText,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = page.titleIcon,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp
                )
            }
        }
    }
}
