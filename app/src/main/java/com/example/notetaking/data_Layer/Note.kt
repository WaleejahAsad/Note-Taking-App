package com.example.notetaking.data_Layer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    var title : String,
    var description : String,
    var date : Long
)
