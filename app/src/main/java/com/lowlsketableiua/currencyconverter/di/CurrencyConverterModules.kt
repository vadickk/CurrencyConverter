package com.lowlsketableiua.currencyconverter.di

import androidx.room.Room
import com.lowlsketableiua.currencyconverter.database.CurrencyConverterDatabase
import com.lowlsketableiua.currencyconverter.database.dao.CurrencyConverterDao
import com.lowlsketableiua.currencyconverter.navigation.CurrencyConvertorNavigationRepository
import com.lowlsketableiua.currencyconverter.network.CurrencyConvertorNetworkService
import com.lowlsketableiua.currencyconverter.screens.viewmodel.ApplicationViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

val CurrencyConverterModule = module {
    viewModel {
        ApplicationViewModel(get(), get(), get())
    }
    single<CurrencyConvertorNavigationRepository> {
        CurrencyConvertorNavigationRepository.Base()
    }
    single<CurrencyConvertorNetworkService> {
        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(Duration.ofMillis(3600 * 24))
            .build()

        Retrofit.Builder()
            .baseUrl(CurrencyConvertorNetworkService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyConvertorNetworkService::class.java)
    }

    single<CurrencyConverterDatabase> {
        Room.databaseBuilder(
            context = get(),
            CurrencyConverterDatabase::class.java,
            "currency_converter_database"
        ).fallbackToDestructiveMigration().build()
    }

    single<CurrencyConverterDao> { get<CurrencyConverterDatabase>().currencyConverterDao() }
}