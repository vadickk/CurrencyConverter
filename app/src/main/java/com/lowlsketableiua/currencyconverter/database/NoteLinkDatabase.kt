package com.lowlsketableiua.currencyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lowlsketableiua.currencyconverter.database.dao.CurrencyConverterDao
import com.lowlsketableiua.currencyconverter.database.entities.HistoryRequestEntity

@Database(entities = [HistoryRequestEntity::class], version = 1)
abstract class CurrencyConverterDatabase : RoomDatabase() {

    abstract fun currencyConverterDao(): CurrencyConverterDao
}
