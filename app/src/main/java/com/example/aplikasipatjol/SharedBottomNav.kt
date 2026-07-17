package com.example.aplikasipatjol

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb

@Composable
fun SharedBottomNav(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Black Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.Black)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                label = "Pesan\nUtama",
                isActive = currentScreen == AppScreen.ReceiverModeTrial,
                onClick = { onNavigate(AppScreen.ReceiverModeTrial) }
            ) { color ->
                PesanUtamaIcon(tint = color, modifier = Modifier.size(24.dp))
            }

            NavItem(
                label = "Pesan\nWaspada",
                isActive = currentScreen == AppScreen.SpamMessagesTrial,
                onClick = { onNavigate(AppScreen.SpamMessagesTrial) }
            ) { color ->
                PesanWaspadaIcon(tint = color, modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.width(64.dp)) // Space for center FAB

            NavItem(
                label = "Statistik\n",
                isActive = currentScreen == AppScreen.StatistikTrial,
                onClick = { onNavigate(AppScreen.StatistikTrial) }
            ) { color ->
                StatistikIcon(tint = color, modifier = Modifier.size(24.dp))
            }

            NavItem(
                label = "Pengaturan\n",
                isActive = currentScreen == AppScreen.PengaturanTrial,
                onClick = { onNavigate(AppScreen.PengaturanTrial) }
            ) { color ->
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Pengaturan", tint = color, modifier = Modifier.size(24.dp))
            }
        }

        // Center Floating Action Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset(y = (-16).dp)
                .clickable { onNavigate(AppScreen.DefaultAppModeTrial) }
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                DeteksiPesanIcon(tint = Color.Black, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Deteksi\nPesan",
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun NavItem(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    icon: @Composable (Color) -> Unit
) {
    val color = if (isActive) Color.White else Color.Gray
    val weight = if (isActive) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(60.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.drawBehind {
                if (isActive) {
                    val composePaint = Paint().apply {
                        asFrameworkPaint().apply {
                            this.color = android.graphics.Color.argb(70, 255, 255, 255)
                            this.maskFilter = android.graphics.BlurMaskFilter(25f, android.graphics.BlurMaskFilter.Blur.NORMAL)
                        }
                    }
                    drawIntoCanvas { canvas ->
                        canvas.drawCircle(center, size.width / 1.5f, composePaint)
                    }
                }
            },
            contentAlignment = Alignment.Center
        ) {
            icon(color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = color,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = weight,
            lineHeight = 12.sp,
            modifier = Modifier.drawBehind {
                if (isActive) {
                    val composePaint = Paint().apply {
                        asFrameworkPaint().apply {
                            this.color = android.graphics.Color.argb(50, 255, 255, 255)
                            this.maskFilter = android.graphics.BlurMaskFilter(20f, android.graphics.BlurMaskFilter.Blur.NORMAL)
                        }
                    }
                    drawIntoCanvas { canvas ->
                        canvas.drawRect(0f, 0f, size.width, size.height, composePaint)
                    }
                }
            }
        )
    }
}

@Composable
fun PesanUtamaIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.5.dp.toPx()
        val path = Path().apply {
            val w = size.width
            val h = size.height * 0.8f
            addRoundRect(RoundRect(0f, 0f, w, h, CornerRadius(w / 2, h / 2)))
            moveTo(w * 0.2f, h)
            lineTo(w * 0.2f, size.height)
            lineTo(w * 0.4f, h)
        }
        drawPath(path, color = tint, style = Stroke(strokeWidth))
        // 3 dots
        drawCircle(color = tint, radius = 1.5.dp.toPx(), center = Offset(size.width * 0.3f, size.height * 0.4f))
        drawCircle(color = tint, radius = 1.5.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.4f))
        drawCircle(color = tint, radius = 1.5.dp.toPx(), center = Offset(size.width * 0.7f, size.height * 0.4f))
    }
}

@Composable
fun PesanWaspadaIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.5.dp.toPx()
        val path = Path().apply {
            val w = size.width
            val h = size.height * 0.75f
            addRoundRect(RoundRect(0f, 0f, w, h, CornerRadius(8.dp.toPx())))
            moveTo(w * 0.25f, h)
            lineTo(w * 0.25f, size.height)
            lineTo(w * 0.45f, h)
        }
        drawPath(path, color = tint, style = Stroke(strokeWidth))
        // Warning triangle inside
        val triPath = Path().apply {
            moveTo(size.width / 2, size.height * 0.15f)
            lineTo(size.width * 0.75f, size.height * 0.55f)
            lineTo(size.width * 0.25f, size.height * 0.55f)
            close()
        }
        drawPath(triPath, color = tint, style = Stroke(strokeWidth))
        drawLine(color = tint, start = Offset(size.width / 2, size.height * 0.25f), end = Offset(size.width / 2, size.height * 0.4f), strokeWidth = strokeWidth)
        drawCircle(color = tint, radius = 1.dp.toPx(), center = Offset(size.width / 2, size.height * 0.48f))
    }
}

@Composable
fun StatistikIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.5.dp.toPx()
        // base line
        drawLine(color = tint, start = Offset(0f, size.height), end = Offset(size.width, size.height), strokeWidth = strokeWidth)
        // bars
        drawRect(color = tint, topLeft = Offset(size.width * 0.1f, size.height * 0.4f), size = Size(size.width * 0.2f, size.height * 0.6f), style = Stroke(strokeWidth))
        drawRect(color = tint, topLeft = Offset(size.width * 0.4f, size.height * 0.1f), size = Size(size.width * 0.2f, size.height * 0.9f), style = Stroke(strokeWidth))
        drawRect(color = tint, topLeft = Offset(size.width * 0.7f, size.height * 0.55f), size = Size(size.width * 0.2f, size.height * 0.45f), style = Stroke(strokeWidth))
    }
}

@Composable
fun DeteksiPesanIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val cornerLength = size.width * 0.25f
        
        // Corners
        drawLine(color = tint, start = Offset(0f, cornerLength), end = Offset(0f, 0f), strokeWidth = strokeWidth)
        drawLine(color = tint, start = Offset(0f, 0f), end = Offset(cornerLength, 0f), strokeWidth = strokeWidth)
        
        drawLine(color = tint, start = Offset(size.width - cornerLength, 0f), end = Offset(size.width, 0f), strokeWidth = strokeWidth)
        drawLine(color = tint, start = Offset(size.width, 0f), end = Offset(size.width, cornerLength), strokeWidth = strokeWidth)
        
        drawLine(color = tint, start = Offset(0f, size.height - cornerLength), end = Offset(0f, size.height), strokeWidth = strokeWidth)
        drawLine(color = tint, start = Offset(0f, size.height), end = Offset(cornerLength, size.height), strokeWidth = strokeWidth)
        
        drawLine(color = tint, start = Offset(size.width, size.height - cornerLength), end = Offset(size.width, size.height), strokeWidth = strokeWidth)
        drawLine(color = tint, start = Offset(size.width - cornerLength, size.height), end = Offset(size.width, size.height), strokeWidth = strokeWidth)
        
        // Chat bubble inside
        val inset = size.width * 0.2f
        val w = size.width - inset * 2
        val h = size.height - inset * 2
        val path = Path().apply {
            addRoundRect(RoundRect(inset, inset, size.width - inset, size.height - inset - h * 0.2f, CornerRadius(w / 2, h / 2)))
            moveTo(inset + w * 0.2f, size.height - inset - h * 0.2f)
            lineTo(inset + w * 0.2f, size.height - inset)
            lineTo(inset + w * 0.4f, size.height - inset - h * 0.2f)
        }
        drawPath(path, color = tint, style = Stroke(strokeWidth))
        
        val dotRadius = 1.2.dp.toPx()
        drawCircle(color = tint, radius = dotRadius, center = Offset(size.width / 2 - w * 0.2f, size.height / 2 - h * 0.1f))
        drawCircle(color = tint, radius = dotRadius, center = Offset(size.width / 2, size.height / 2 - h * 0.1f))
        drawCircle(color = tint, radius = dotRadius, center = Offset(size.width / 2 + w * 0.2f, size.height / 2 - h * 0.1f))
    }
}
