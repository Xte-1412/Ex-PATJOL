package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SystemModeSelectionScreen(onModeSelected: (String) -> Unit = {}) {
    var expandedMode by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Pilihan mode sistem",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1877F2),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        ExpandableCard(
            title = "Mode Trial",
            description = buildAnnotatedString {
                append("Simulasi cara kerja sistem aplikasi (sistem tetap bekerja secara real-time.)")
            },
            isExpanded = expandedMode == "Mode Trial",
            onClick = {
                expandedMode = if (expandedMode == "Mode Trial") null else "Mode Trial"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableCard(
            title = "Set Default App",
            description = buildAnnotatedString {
                append("Mode aplikasi secara nyata ")
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("(masih dalam tahap pengembangan)")
                }
            },
            isExpanded = expandedMode == "Set Default App",
            onClick = {
                expandedMode = if (expandedMode == "Set Default App") null else "Set Default App"
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
            Text("Pilih mode sistem ini", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ExpandableCard(
    title: String,
    description: androidx.compose.ui.text.AnnotatedString,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isExpanded) Color(0xFFE8F0FE) else Color.White
    val titleColor = if (isExpanded) Color(0xFF1877F2) else Color.Black
    val arrowText = if (isExpanded) "v" else ">"
    val arrowColor = if (isExpanded) Color(0xFF1877F2) else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(2.dp, Color(0xFF1877F2), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .animateContentSize()
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = titleColor
                )
                Text(
                    text = arrowText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = arrowColor
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = Color.Black,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
