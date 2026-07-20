package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign

@Composable
fun UserModeSelectionScreen(onModeSelected: (String) -> Unit = {}) {
    var expandedMode by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Pilihan mode user",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1877F2),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        ExpandableCard(
            title = "Penerima Pesan",
            description = buildAnnotatedString { append("Simulasi untuk terima, deteksi, proteksi, dan lapor pesan") },
            isExpanded = expandedMode == "Penerima Pesan",
            onClick = {
                expandedMode = if (expandedMode == "Penerima Pesan") null else "Penerima Pesan"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableCard(
            title = "Pengirim Pesan",
            description = buildAnnotatedString { append("Simulasi untuk pengiriman pesan kepada penerima pesan. (hanya untuk di aplikasi saja)") },
            isExpanded = expandedMode == "Pengirim Pesan",
            onClick = {
                expandedMode = if (expandedMode == "Pengirim Pesan") null else "Pengirim Pesan"
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { expandedMode?.let { onModeSelected(it) } },
            enabled = expandedMode != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1877F2),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFCBD5E1),
                disabledContentColor = Color(0xFFA6A8AC)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Mulai mode user ini", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
