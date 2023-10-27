package com.lowlsketableiua.currencyconverter.application

import android.app.Application
import com.lowlsketableiua.currencyconverter.di.CurrencyConverterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(CurrencyConverterModule)
        }
    }
}