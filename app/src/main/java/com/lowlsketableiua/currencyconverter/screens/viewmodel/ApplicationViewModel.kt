package com.lowlsketableiua.currencyconverter.screens.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lowlsketableiua.currencyconverter.database.dao.CurrencyConverterDao
import com.lowlsketableiua.currencyconverter.database.entities.HistoryRequestEntity
import com.lowlsketableiua.currencyconverter.navigation.CurrencyConvertorNavigationRepository
import com.lowlsketableiua.currencyconverter.network.CurrencyConvertorNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationViewModel(
    private val currencyConvertorNavigationRepository: CurrencyConvertorNavigationRepository,
    private val currencyConvertorNetworkService: CurrencyConvertorNetworkService,
    private val dao: CurrencyConverterDao
) : ViewModel(), CurrencyConvertorNavigationRepository {

    val valueFrom = MutableLiveData<Int>()
    val currencyFrom = MutableLiveData<String>()
    val valueTo = MutableLiveData<String>()
    val currencyTo = MutableLiveData<String>()
    val lastUpdate = MutableLiveData<String>()

    fun getAllHistory(): List<HistoryRequestEntity> {
        return dao.getAllHistory()
    }

    fun getAndCalculateCourseByCurrency(currencyFrom: String, currencyTo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyConvertorNetworkService.getCourseByCurrency(currencyFrom, currencyTo)
            withContext(Dispatchers.Main) {
               valueTo.value = String.format("%.2f", (result.body()?.conversion_rate?.let { it1 ->
                   valueFrom.value?.times(it1)
               }))

               lastUpdate.value = "Last update: ${result.body()?.time_last_update_utc?.removeSuffix("+0000")}"
           }

            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = java.util.Date(System.currentTimeMillis())
            val time = sdf.format(date)

            dao.insertHistoryRequest(
                HistoryRequestEntity(
                    valueFrom = valueFrom.value.toString(),
                    valueTo = valueTo.value.toString(),
                    currencyFrom = currencyFrom,
                    currencyTo = currencyTo,
                    time = time
                )
            )
        }
    }

    override fun navigateToHistoryFragment(navController: NavController) {
        currencyConvertorNavigationRepository.navigateToHistoryFragment(navController)
    }

    override fun navigateToMainFragment(navController: NavController) {
        currencyConvertorNavigationRepository.navigateToMainFragment(navController)
    }
}