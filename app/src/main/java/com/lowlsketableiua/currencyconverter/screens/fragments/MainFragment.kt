package com.lowlsketableiua.currencyconverter.screens.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.lowlsketableiua.currencyconverter.R
import com.lowlsketableiua.currencyconverter.databinding.FragmentMainBinding
import com.lowlsketableiua.currencyconverter.screens.viewmodel.ApplicationViewModel
import com.lowlsketableiua.currencyconverter.utils.currencies
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<ApplicationViewModel>()

    private val listenerFromCurrency = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val result = p0.toString().toIntOrNull()
            if (result != null){
                viewModel.valueFrom.value = result
            }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationMenu()

        binding.button.setOnClickListener {
            val from = viewModel.currencyFrom.value
            val to = viewModel.currencyTo.value
            val value = viewModel.valueFrom.value
            if (value == null) {
                binding.tvInputRangeFrom.error = "Please write a value"
            } else if(from == null) {
                binding.currencyFrom.errorText = "please choose the item"
                binding.tvInputRangeFrom.error = null
            } else if (to == null) {
                binding.tvInputRangeFrom.error = null
                binding.currencyFrom.errorText = null
                binding.currencyTo.errorText = "please choose the item"
            } else {
                binding.tvInputRangeFrom.error = null
                binding.currencyTo.errorText = null
                binding.currencyFrom.errorText = null
                viewModel.getAndCalculateCourseByCurrency(viewModel.currencyFrom.value.toString(), viewModel.currencyTo.value.toString())
            }
        }

        binding.inputRangeFromField.addTextChangedListener(listenerFromCurrency)

        binding.currencyFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
                viewModel.currencyFrom.value = currencies[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                binding.currencyFrom.errorText = "please choose the item"
            }
        }

        binding.currencyTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
                viewModel.currencyTo.value = currencies[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                binding.currencyTo.errorText = "please choose the item"
            }
        }

        binding.currencyFrom.item = currencies
        binding.currencyTo.item = currencies

        viewModel.valueTo.observe(viewLifecycleOwner) {
            binding.tvResult.visibility = View.VISIBLE
            binding.tvResult.text = "$it ${viewModel.currencyTo.value}"
        }

        viewModel.lastUpdate.observe(viewLifecycleOwner) {
            binding.tvResult.visibility = View.VISIBLE
            binding.textView2.text = it
        }
    }

    private fun bottomNavigationMenu() {
        binding.bottomNavigationView.menu.findItem(R.id.mainItem).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.historyItem -> {
                    viewModel.navigateToHistoryFragment(findNavController())
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.inputRangeFromField.removeTextChangedListener(listenerFromCurrency)
    }
}