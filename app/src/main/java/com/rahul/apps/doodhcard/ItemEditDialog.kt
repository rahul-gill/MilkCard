package com.rahul.apps.doodhcard

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rahul.apps.doodhcard.databinding.ItemEditDialogBinding

fun createEditItemDialog(context: Context, dateTime: String, initData: ListEntryItem.EntryData?, callback: (ItemModel) -> Unit) {
    val binding = ItemEditDialogBinding.inflate(LayoutInflater.from(context), null, false)
    binding.dialogDatetime.text = dateTime
    if(initData != null){
        binding.dialogMilkRate.setText(showFormattedDouble( initData.rate))
        binding.dialogMilkWeight.setText(showFormattedDouble(initData.weight))
        binding.dialogMilkFat.setText(showFormattedDouble(initData.fat))
    }

    AlertDialog.Builder(context)
        .setView(binding.root)
        .setPositiveButton("DONE") { _, _ ->
            try {
                val item = ItemModel(
                    weight = stringToDouble(binding.dialogMilkWeight.text.toString()),
                    fat = stringToDouble(binding.dialogMilkFat.text.toString()),
                    rate = stringToDouble(binding.dialogMilkRate.text.toString())
                )
                callback(item)
            }catch (e: Exception){
                Toast.makeText(context, "Invalid data entered.", Toast.LENGTH_SHORT).show()
            }
        }
        .setNegativeButton("CANCEL", null)
        .create()
        .show()
}
