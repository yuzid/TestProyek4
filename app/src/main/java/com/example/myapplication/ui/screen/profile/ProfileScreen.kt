package com.example.myapplication.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.viewmodel.DataViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.myapplication.ui.components.JetsnackButton
import com.example.myapplication.viewmodel.saveImageToInternalStorage
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    val profile by viewModel.profileStateFlow.collectAsState()

    var studentName by rememberSaveable { mutableStateOf(profile?.username ?: "Muhammad Reivan Naufal Mufid") }
    var studentId by rememberSaveable { mutableStateOf(profile?.uid ?: "231511021") }
    var studentEmail by rememberSaveable { mutableStateOf(profile?.email ?: "muhammad.reivan.tif23@gmail.com") }

    var profileImageUri by rememberSaveable { mutableStateOf(profile?.profileImageUri) }

    val context = LocalContext.current
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val imagePath = saveImageToInternalStorage(context, uri)
            if (imagePath != null) {
                profileImageUri = imagePath
                viewModel.updateProfileImage(imagePath) // Simpan path ke database
            }
        }
    }



    // Update fields when profile changes
    LaunchedEffect(profile) {
        profile?.let {
            studentName = it.username
            studentId = it.uid
            studentEmail = it.email
            profileImageUri = it.profileImageUri // Pastikan URI diperbarui dari database
        }
    }


    if (profile == null) {
        Text("Loading profile...", modifier = Modifier.padding(16.dp))
        return
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .background(Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = profileImageUri?.let { Uri.fromFile(File(it)) }, // Gambar default jika belum ada
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            if (isEditing) {
                // Edit mode: Display input fields
                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name",color = Color.White) },
                    textStyle = LocalTextStyle.current.copy(color = Color.White), // Ubah warna teks input
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Cyan, // Warna border saat fokus
                        unfocusedBorderColor = Color.Gray, // Warna border saat tidak fokus
                        cursorColor = Color.Cyan, // Warna kursor
                        focusedLabelColor = Color.Cyan, // Warna label saat fokus
                        unfocusedLabelColor = Color.White, // Warna label saat tidak fokus
                        disabledBorderColor = Color.Gray, // Warna border saat disabled
                        disabledTextColor = Color.LightGray // Warna teks saat disabled
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Student ID",color = Color.White) },
                    textStyle = LocalTextStyle.current.copy(color = Color.White), // Ubah warna teks input
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Cyan, // Warna border saat fokus
                        unfocusedBorderColor = Color.Gray, // Warna border saat tidak fokus
                        cursorColor = Color.Cyan, // Warna kursor
                        focusedLabelColor = Color.Cyan, // Warna label saat fokus
                        unfocusedLabelColor = Color.White, // Warna label saat tidak fokus
                        disabledBorderColor = Color.Gray, // Warna border saat disabled
                        disabledTextColor = Color.LightGray // Warna teks saat disabled
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentEmail,
                    onValueChange = { studentEmail = it },
                    label = { Text("Student Email",color = Color.White) },
                    textStyle = LocalTextStyle.current.copy(color = Color.White), // Ubah warna teks input
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Cyan, // Warna border saat fokus
                        unfocusedBorderColor = Color.Gray, // Warna border saat tidak fokus
                        cursorColor = Color.Cyan, // Warna kursor
                        focusedLabelColor = Color.Cyan, // Warna label saat fokus
                        unfocusedLabelColor = Color.White, // Warna label saat tidak fokus
                        disabledBorderColor = Color.Gray, // Warna border saat disabled
                        disabledTextColor = Color.LightGray // Warna teks saat disabled
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Upload Profile Photo Button
                JetsnackButton(
                    onClick = { pickImageLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundGradient = listOf(Color.Blue, Color.Cyan),
                    disabledBackgroundGradient = listOf(Color.Gray, Color.DarkGray),
                ) {
                    Text("Upload Photo")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                JetsnackButton(
                    onClick = {
                        val currentProfile = profile // Simpan dalam variabel lokal

                        if (currentProfile != null) {
                            val updatedData = currentProfile.copy( // Sekarang Kotlin yakin currentProfile tidak null
                                username = studentName,
                                uid = studentId,
                                email = studentEmail,
                                profileImageUri = profileImageUri // Simpan path gambar yang baru
                            )
                            isEditing = false
                            viewModel.updateData(updatedData)
                        }
                    }, modifier = Modifier.fillMaxWidth(),
                    backgroundGradient = listOf(Color.Green, Color.Cyan),
                    disabledBackgroundGradient = listOf(Color.Gray, Color.DarkGray),
                ) {
                    Text("Save")
                }

            } else {
                // Display Mode
                Text(
                    text = studentName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ID: $studentId",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = studentEmail,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Edit Profile Button
                JetsnackButton(
                    onClick = { isEditing = true },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundGradient = listOf(Color.Yellow, Color.Cyan),
                    disabledBackgroundGradient = listOf(Color.Gray, Color.DarkGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Edit Profile")
                }
            }
        }
    }
}
