package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.DataProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).profileDao()
    val dataList: LiveData<List<DataProfile>> = dao.getAll()

    private val _profileStateFlow = MutableStateFlow<DataProfile?>(null)
    val profileStateFlow: StateFlow<DataProfile?> = _profileStateFlow

    init {
        loadProfile()
    }

    fun insertData(username: String, uid: String, email: String) {
        viewModelScope.launch {
            dao.insert(
                DataProfile(
                    username = username,
                    uid = uid,
                    email = email
                )
            )
        }
    }

    fun updateData(data: DataProfile) {
        viewModelScope.launch {
            dao.update(data)
            _profileStateFlow.value = data // Ensure recomposition
        }
    }



    suspend fun getDataById(id: Int): DataProfile? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteData(data: DataProfile) {
        viewModelScope.launch {
            dao.delete(data)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val existingProfile = dao.getProfile()
            if (existingProfile == null) {
                val defaultProfile = DataProfile()
                dao.insert(defaultProfile)
                _profileStateFlow.value = defaultProfile
            } else {
                _profileStateFlow.value = existingProfile
            }
        }
    }

    // **NEW FUNCTION: Update only the profile image**
    fun updateProfileImage(imageUri: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateProfileImage(imageUri)
            val currentProfile = _profileStateFlow.value
            if (currentProfile != null) {
                val updatedProfile = currentProfile.copy(profileImageUri = imageUri)
                withContext(Dispatchers.Main) {
                    _profileStateFlow.value = updatedProfile
                }
            }
        }
    }

}

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    val fileName = "profile_image.jpg" // Bisa pakai UUID jika ingin unik
    val file = File(context.filesDir, fileName)

    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath // Kembalikan path untuk disimpan di database
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

