package com.example.myapplication.ui.screen.DataList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.components.JetsnackButton
import com.example.myapplication.ui.components.JetsnackCard
import com.example.myapplication.viewmodel.DataViewModel

@Composable
fun DataListScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    var selectedYear by remember { mutableStateOf("All Data") }
    val yearRange = listOf("All Data") + (2007..2011).map { it.toString() }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val filteredData = if (selectedYear == "All Data") {
        dataList
    } else {
        dataList.filter { it.tahun.toString() == selectedYear }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            JetsnackButton(onClick = { isDropdownExpanded = !isDropdownExpanded },
                backgroundGradient = listOf(Color.Blue, Color.Cyan),
                disabledBackgroundGradient = listOf(Color.Gray, Color.DarkGray),
                shape = RoundedCornerShape(32.dp)) {
                Text("Pilih Tahun: $selectedYear")
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")

            }
        }

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.heightIn(max = 300.dp) // Membatasi tinggi dropdown agar tidak penuh dari atas ke bawah
        ) {
            yearRange.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year) },
                    onClick = {
                        selectedYear = year
                        isDropdownExpanded = false
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (filteredData.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Data Available", style = MaterialTheme.typography.headlineMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredData) { item ->
                        JetsnackCard(
                            shape = RoundedCornerShape(12.dp),
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Kabupaten/Kota: ${item.bpsnamaKabupatenKota}", style = MaterialTheme.typography.bodyMedium)
                                Text("Total: ${item.persentasePeningkatanKapasitasSumberDayaKesehatanManusia} ${item.satuan}", style = MaterialTheme.typography.bodyMedium)
                                Text("Tahun: ${item.tahun}", style = MaterialTheme.typography.bodyMedium)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    JetsnackButton(onClick = { navController.navigate("edit/${item.id}") }, shape = RoundedCornerShape(8.dp), backgroundGradient = listOf(Color.Green, Color.Yellow)) {
                                        Text("Edit")
                                    }
                                    JetsnackButton(onClick = { viewModel.deleteData(item) }, shape = RoundedCornerShape(8.dp), backgroundGradient = listOf(Color.Red, Color.Red)) {
                                        Text("Hapus")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { if (!isLoading) viewModel.fetchDataAndInsert() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clickable(enabled = !isLoading) {},
                containerColor = Color.Blue,
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Sync Icon")
                }
            }
        }
    }
}
