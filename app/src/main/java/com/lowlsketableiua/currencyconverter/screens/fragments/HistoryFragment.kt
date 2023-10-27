package com.lowlsketableiua.currencyconverter.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lowlsketableiua.currencyconverter.R
import com.lowlsketableiua.currencyconverter.screens.adapter.HistoryAdapter
import com.lowlsketableiua.currencyconverter.databinding.FragmentHistoryBinding
import com.lowlsketableiua.currencyconverter.navigation.CurrencyConvertorNavigationRepository
import com.lowlsketableiua.currencyconverter.screens.viewmodel.ApplicationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private val binding by lazy { FragmentHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<ApplicationViewModel>()
    private val historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationMenu()
        binding.rvHistory.adapter = historyAdapter
        lifecycleScope.launch(Dispatchers.IO) {
            val history = viewModel.getAllHistory()
            withContext(Dispatchers.Main) {
                historyAdapter.setHistoryEntities(history)
            }
        }
    }

    private fun bottomNavigationMenu() {
        binding.bottomNavigationView.menu.findItem(R.id.historyItem).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.mainItem -> {
                    viewModel.navigateToMainFragment(findNavController())
                    true
                }
                else -> false
            }
        }
    }
}