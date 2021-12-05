package com.rahul.apps.doodhcard


import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahul.apps.doodhcard.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ItemListAdapter: RecyclerView.Adapter<ItemListAdapter.ViewHolder>(){
    var data = mutableListOf<ListEntryItem>(ListEntryItem.Header)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = data[position]
        val binding = holder.binding
        if(player is ListEntryItem.Header){
            binding.milkDateTime.text = "Date   "
            binding.milkWeight.text = "Weight "
            binding.milkFat.text = "FAT    "
            binding.milkPrice.text = "Price  "
            binding.milkRate.text = "Rate   "
            binding.editButton.visibility = View.INVISIBLE
            binding.milkRate.setTypeface(null, Typeface.BOLD_ITALIC)
            binding.milkFat.setTypeface(null, Typeface.BOLD_ITALIC)
            binding.milkPrice.setTypeface(null, Typeface.BOLD_ITALIC)
            binding.milkWeight.setTypeface(null, Typeface.BOLD_ITALIC)
        }
        else {
            val pl = (player as ListEntryItem.EntryData)
            binding.milkDateTime.text = pl.showFormattedDateTime()
            binding.milkWeight.text = showFormattedDouble(pl.weight)
            binding.milkFat.text = showFormattedDouble(pl.fat)
            binding.milkPrice.text = showFormattedDouble(pl.price)
            binding.milkRate.text = showFormattedDouble(pl.rate)
            binding.editButton.setOnClickListener {
                createEditItemDialog(binding.root.context, pl.showFormattedDateTime(),pl){
                    data[position] = ListEntryItem.EntryData.from(it)
                    notifyItemChanged(position)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

sealed class ListEntryItem{
    object Header: ListEntryItem()
    class EntryData(
        val weight: Double = 0.0,
        val fat: Double = 0.0,
        val rate: Double = 0.0,
        val datetime: MEDate
    ): ListEntryItem(){
        val price: Double
            get() = rate * weight
        companion object{
            fun from(item: ItemModel) = EntryData(item.weight, item.fat, item.rate, item.datetime)
        }
        fun showFormattedDateTime(): String{
            val sdf = SimpleDateFormat("MMMdd:", Locale.getDefault())
            return sdf.format(datetime.date) + "" + datetime.session
        }
    }
}
