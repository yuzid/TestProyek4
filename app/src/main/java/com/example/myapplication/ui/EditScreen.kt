package com.example.myapplication.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.DataEntity
import com.example.myapplication.viewmodel.DataViewModel

@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: DataViewModel,
    dataId: Int
) {
    val context = LocalContext.current

    var kodeProvinsi by remember { mutableStateOf("") }
    var namaProvinsi by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }

    LaunchedEffect(dataId) {
        viewModel.getDataById(dataId)?.let { data ->

            namaProvinsi = data.namaProvinsi
            namaKabupatenKota = data.bpsnamaKabupatenKota
            total = data.persentasePeningkatanKapasitasSumberDayaKesehatanManusia.toString()
            satuan = data.satuan
            tahun = data.tahun.toString()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Edit Data",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = namaProvinsi,
                onValueChange = { namaProvinsi = it },
                label = { Text("Nama Provinsi") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = namaKabupatenKota,
                onValueChange = { namaKabupatenKota = it },
                label = { Text("Bps Nama Kabupaten/Kota") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = total,
                onValueChange = { total = it },
                label = { Text("Persentase peningkatan kapasitas sumber daya manusia kesehatan") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = satuan,
                onValueChange = { satuan = it },
                label = { Text("Satuan") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tahun,
                onValueChange = { tahun = it },
                label = { Text("Tahun") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val updatedData = DataEntity(
                        id = dataId,
                        namaProvinsi = namaProvinsi,
                        bpsnamaKabupatenKota = namaKabupatenKota,
                        persentasePeningkatanKapasitasSumberDayaKesehatanManusia = total.toDoubleOrNull() ?: 0.0,
                        satuan = satuan,
                        tahun = tahun.toIntOrNull() ?: 0
                    )
                    viewModel.updateData(updatedData)
                    Toast.makeText(context, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update Data")
            }

            }
        }
    }

