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
            text = "Pilih mode user",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        ExpandableCard(
            title = "Penerima Pesan",
            description = "Lorem ipsum sit dolor amet sit amet amet",
            isExpanded = expandedMode == "Penerima Pesan",
            onClick = {
                expandedMode = if (expandedMode == "Penerima Pesan") null else "Penerima Pesan"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableCard(
            title = "Pengirim Pesan",
            description = "Lorem ipsum sit dolor amet sit amet amet",
            isExpanded = expandedMode == "Pengirim Pesan",
            onClick = {
                expandedMode = if (expandedMode == "Pengirim Pesan") null else "Pengirim Pesan"
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        if (expandedMode != null) {
            Button(
                onClick = { onModeSelected(expandedMode!!) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Mulai", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
