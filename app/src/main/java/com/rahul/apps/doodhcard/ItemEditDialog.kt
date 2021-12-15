package com.rahul.apps.doodhcard

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rahul.apps.doodhcard.databinding.ItemEditDialogBinding

fun createEditItemDialog(context: Context,initData: ListEntryItem.EntryData?, callback: (MilkCardEntryModel?) -> Unit) {
    val binding = ItemEditDialogBinding.inflate(LayoutInflater.from(context), null, false)
    if(initData != null){
        binding.dialogMilkRate.setText(formattedDouble( initData.rate))
        binding.dialogMilkWeight.setText(formattedDouble(initData.weight))
        binding.dialogMilkFat.setText(formattedDouble(initData.fat))
        binding.dialogDatetime.text = initData.datetime
    }else{
        binding.dialogDatetime.text = getDateTimeString()
    }

    val dialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .setPositiveButton("DONE") { _, _ ->
            try {
                val item = MilkCardEntryModel(
                    weight = stringToDouble(binding.dialogMilkWeight.text.toString()),
                    fat = stringToDouble(binding.dialogMilkFat.text.toString()),
                    rate = stringToDouble(binding.dialogMilkRate.text.toString()),
                    datetime = binding.dialogDatetime.text.toString()
                )
                callback(item)
            }catch (e: Exception){
                Toast.makeText(context, "Invalid data entered.", Toast.LENGTH_SHORT).show()
            }
        }
        .setNeutralButton("CANCEL", null)
    if(initData != null)
        dialog.setNegativeButton("DELETE"){ _, _ ->
            callback(null)
        }
    dialog.create().show()
}
