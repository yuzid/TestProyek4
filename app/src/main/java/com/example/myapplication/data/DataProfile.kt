package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_profile")
data class DataProfile (
    @PrimaryKey val id: Int = 1,
    var username: String = "Yazid Fauzan Prasatria",
    var uid: String = "231511032",
    var email: String = "yazid.fauzan.tif23@polban.ac.id",
    var profileImageUri: String? = null
)
