package com.lowlsketableiua.currencyconverter.navigation

import androidx.navigation.NavController
import com.lowlsketableiua.currencyconverter.R

interface CurrencyConvertorNavigationRepository {

    fun navigateToHistoryFragment(navController: NavController)

    fun navigateToMainFragment(navController: NavController)

    class Base : CurrencyConvertorNavigationRepository {
        override fun navigateToHistoryFragment(navController: NavController) {
            navController.navigate(R.id.actionFromMainFragmentToHistoryFragment)
        }

        override fun navigateToMainFragment(navController: NavController) {
            navController.navigate(R.id.actionFromHistoryFragmentToMainFragment)
        }
    }
}