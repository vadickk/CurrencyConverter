package com.lowlsketableiua.currencyconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history_table")
data class HistoryRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "valueFrom") val valueFrom: String,
    @ColumnInfo(name = "valueTo") val valueTo: String,
    @ColumnInfo(name = "currencyFrom") val currencyFrom: String,
    @ColumnInfo(name = "currencyTo") val currencyTo: String,
    @ColumnInfo(name = "time") val time: String,
)