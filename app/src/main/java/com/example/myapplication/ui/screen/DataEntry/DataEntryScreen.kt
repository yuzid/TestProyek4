package com.example.myapplication.ui.screen.DataEntry

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.ui.components.JetsnackButton
import com.example.myapplication.ui.components.JetsnackSurface


@Composable
fun DataEntryScreen(navController: NavHostController, viewModel: DataViewModel) {
    val context = LocalContext.current
    var namaProvinsi by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }

    val textColor = MaterialTheme.colorScheme.onBackground
    val borderColor = MaterialTheme.colorScheme.primary
    val labelColor = MaterialTheme.colorScheme.secondary

    JetsnackSurface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Input Data",
                style = MaterialTheme.typography.headlineMedium,
                color = textColor
            )
            OutlinedTextField(
                value = namaProvinsi,
                onValueChange = { namaProvinsi = it },
                label = { Text("Nama Provinsi", color = labelColor) },
                textStyle = LocalTextStyle.current.copy(color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = borderColor,
                    focusedLabelColor = borderColor,
                    unfocusedLabelColor = labelColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = namaKabupatenKota,
                onValueChange = { namaKabupatenKota = it },
                label = { Text("Bps Nama Kabupaten/Kota", color = labelColor) },
                textStyle = LocalTextStyle.current.copy(color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = borderColor,
                    focusedLabelColor = borderColor,
                    unfocusedLabelColor = labelColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = total,
                onValueChange = { total = it },
                label = { Text("Persentase peningkatan kapasitas sumber daya manusia kesehatan", color = labelColor) },
                textStyle = LocalTextStyle.current.copy(color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = borderColor,
                    focusedLabelColor = borderColor,
                    unfocusedLabelColor = labelColor
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = satuan,
                onValueChange = { satuan = it },
                label = { Text("Satuan", color = labelColor) },
                textStyle = LocalTextStyle.current.copy(color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = borderColor,
                    focusedLabelColor = borderColor,
                    unfocusedLabelColor = labelColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tahun,
                onValueChange = { tahun = it },
                label = { Text("Tahun", color = labelColor) },
                textStyle = LocalTextStyle.current.copy(color = textColor),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = borderColor,
                    focusedLabelColor = borderColor,
                    unfocusedLabelColor = labelColor
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            JetsnackButton(
                onClick = {
                    viewModel.insertData(
                        namaProvinsi = namaProvinsi,
                        namaKabupatenKota = namaKabupatenKota,
                        total = total,
                        satuan = satuan,
                        tahun = tahun
                    )
                    Toast.makeText(context, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    namaProvinsi = ""
                    namaKabupatenKota = ""
                    total = ""
                    satuan = ""
                    tahun = ""
                },
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(Color.Blue, Color.Cyan),
                disabledBackgroundGradient = listOf(Color.Gray, Color.DarkGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Data", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
