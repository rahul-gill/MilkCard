package com.github.rahul_gill.doodhcard


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.rahul_gill.doodhcard.data.MilkCardEntity
import com.github.rahul_gill.doodhcard.databinding.ListItemBinding


class ItemListAdapter(
    val onItemUpdateCallback: (new_data: MilkCardEntity, delete: Boolean) -> Unit
): ListAdapter<MilkCardEntity, ItemListAdapter.ViewHolder>(MilkCardEntriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            data = getItem(position),
            onItemChange = { new_data ->
                if(new_data != null) onItemUpdateCallback(new_data, false)
                else onItemUpdateCallback(getItem(position), true)
            }
        )
    }


    class ViewHolder private constructor(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            data: MilkCardEntity,
            onItemChange: (update: MilkCardEntity?) -> Unit
        ) {
            binding.milkDateTime.text = data.datetime
            binding.milkWeight.text = Util.formattedDouble(data.weight)
            binding.milkFat.text = Util.formattedDouble(data.fat)
            binding.milkPrice.text = Util.formattedDouble(data.price)
            binding.milkRate.text = Util.formattedDouble(data.rate)
            binding.editButton.setOnClickListener {
                createEditItemDialog(binding.root.context, data) { onItemChange(it) }
            }
        }
    }
}

class MilkCardEntriesDiffCallback: DiffUtil.ItemCallback<MilkCardEntity>(){
    override fun areItemsTheSame(oldItem: MilkCardEntity, newItem: MilkCardEntity): Boolean {
        return oldItem.datetime == newItem.datetime
    }
    override fun areContentsTheSame(oldItem: MilkCardEntity, newItem: MilkCardEntity): Boolean {
        return oldItem == newItem
    }

}
