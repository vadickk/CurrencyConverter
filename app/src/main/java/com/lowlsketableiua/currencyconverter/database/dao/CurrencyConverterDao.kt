package com.lowlsketableiua.currencyconverter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lowlsketableiua.currencyconverter.database.entities.HistoryRequestEntity

@Dao
interface CurrencyConverterDao {

    @Query("SELECT * FROM history_table")
    fun getAllHistory(): List<HistoryRequestEntity>

    @Insert
    fun insertHistoryRequest(historyRequestEntity: HistoryRequestEntity)

}