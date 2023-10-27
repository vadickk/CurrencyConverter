package com.lowlsketableiua.currencyconverter.screens.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lowlsketableiua.currencyconverter.database.entities.HistoryRequestEntity
import com.lowlsketableiua.currencyconverter.databinding.ItemHistoryRequestBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val historyRequest = mutableListOf<HistoryRequestEntity>()

    inner class HistoryViewHolder(private val binding: ItemHistoryRequestBinding) : ViewHolder(binding.root) {
        fun bind(country: HistoryRequestEntity) {
            binding.tvTime.text = country.time
            binding.tvValueFrom.text = "From ${country.valueFrom} ${country.currencyFrom}"
            binding.tvValueTo.text = "To ${country.valueTo} ${country.currencyTo}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyRequest[position])
    }

    override fun getItemCount() = historyRequest.size

    @SuppressLint("NotifyDataSetChanged")
    fun setHistoryEntities(historyRequestEntities: List<HistoryRequestEntity>) {
        historyRequest.clear()
        historyRequest.addAll(historyRequestEntities.reversed())
        notifyDataSetChanged()
    }
}