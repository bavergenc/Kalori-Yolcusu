package com.example.healtapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "kcal_items")
data class Kcal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Otomatik artan birincil anahtar
    val name: String,
    val image: String,
    val calories: String,
    val carbohydrateContent: String,
    val proteinContent: String,
    val fatContent: String,
    val servingSize: String)