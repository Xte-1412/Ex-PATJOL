package com.example.aplikasipatjol.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IsiBiodataScreen(
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Validation: name and phone cannot be empty
    val isFormValid = name.isNotBlank() && phone.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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
                Text(
                    text = "Isi Biodata",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Name Field
            Text(text = "Name*", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Phone Field
            Text(text = "Phone no.*", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = "+62",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.width(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Email Field
            Text(text = "Email*", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Simpan Button
            Button(
                onClick = {
                    if (isFormValid) {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Simpan", fontSize = 16.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Success Dialog
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0E0E0)) // Light grey
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Checkmark box
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Success",
                                tint = Color.Black,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Penyimpanan\nBiodata Berhasil",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
