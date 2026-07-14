package com.example.aplikasipatjol

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
            text = "Pilih mode sistem",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        ExpandableCard(
            title = "Mode Trial",
            description = "Lorem ipsum sit dolor amet sit amet amet",
            isExpanded = expandedMode == "Mode Trial",
            onClick = {
                expandedMode = if (expandedMode == "Mode Trial") null else "Mode Trial"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableCard(
            title = "Set Default App",
            description = "Lorem ipsum sit dolor amet sit amet amet",
            isExpanded = expandedMode == "Set Default App",
            onClick = {
                expandedMode = if (expandedMode == "Set Default App") null else "Set Default App"
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
                Text("Pilih", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ExpandableCard(
    title: String,
    description: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isExpanded) Color.Black else Color.White
    val contentColor = if (isExpanded) Color.White else Color.Black
    val icon = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
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
                    fontSize = 18.sp,
                    color = contentColor
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = contentColor,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
