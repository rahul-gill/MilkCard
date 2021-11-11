package com.rahul.apps.doodhcard

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rahul.apps.doodhcard.databinding.ItemEditDialogBinding

fun createEditItemDialog(context: Context, dateTime: String, init: ListEntryItem.EntryData?, callback: (ItemModel) -> Unit) {
    val binding = ItemEditDialogBinding.inflate(LayoutInflater.from(context), null, false)
    binding.dialogDatetime.text = dateTime
    if(init != null){
        binding.dialogMilkRate.setText("%.2f".format( init.rate))
        binding.dialogMilkWeight.setText( "%.2f".format(init.weight))
        binding.dialogMilkFat.setText("%.2f".format(init.fat))
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

fun stringToDouble(value: String): Double{
    return if(value.isBlank()) 0.0
    else value.toDouble()
}