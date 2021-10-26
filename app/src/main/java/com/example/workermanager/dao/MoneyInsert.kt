package com.example.workermanager.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money")
data class MoneyInsert(
    val Ccy: String = "",
    val CcyNm_UZ: String = "",
    val Date: String = "",
    val Diff: String = "",
    val Rate: String = "",
    @PrimaryKey
    val id: Int = 0,
    var image: String = "",
    val isliked: Boolean = false,
)