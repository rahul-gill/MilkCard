package com.rahul.apps.doodhcard

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.rahul.apps.doodhcard.data.MilkCardEntity
import com.rahul.apps.doodhcard.databinding.ItemEditDialogBinding

fun createEditItemDialog(context: Context,initData: MilkCardEntity?, callback: (MilkCardEntity?) -> Unit) {
    val binding = ItemEditDialogBinding.inflate(LayoutInflater.from(context), null, false)
    val dialog = Dialog(context)
    dialog.setContentView(binding.root)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    if(initData != null){
        binding.dialogMilkRate.setText(Util.formattedDouble( initData.rate,false))
        binding.dialogMilkWeight.setText(Util.formattedDouble(initData.weight,false))
        binding.dialogMilkFat.setText(Util.formattedDouble(initData.fat,false))
        binding.dialogDatetime.text = initData.datetime
        binding.deleteButton.visibility = View.VISIBLE
        binding.deleteButton.setOnClickListener { callback(null); dialog.dismiss() }
    }
    else binding.dialogDatetime.text = Util.getDateTimeString()

    binding.doneButton.setOnClickListener {
        try {
            val item = MilkCardEntity(
                weight = Util.stringToDouble(binding.dialogMilkWeight.text.toString()),
                fat = Util.stringToDouble(binding.dialogMilkFat.text.toString()),
                rate = Util.stringToDouble(binding.dialogMilkRate.text.toString()),
                datetime = binding.dialogDatetime.text.toString()
            )
            callback(item)
        }catch (e: Exception){
            Toast.makeText(context, "Invalid data entered.", Toast.LENGTH_SHORT).show()
        }
        dialog.dismiss()
    }
    binding.cancelButton.setOnClickListener { dialog.dismiss() }

    dialog.show()
}
