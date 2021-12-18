package com.rahul.apps.doodhcard

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rahul.apps.doodhcard.data.MilkCardEntity
import com.rahul.apps.doodhcard.databinding.ActivityMainBinding
import com.rahul.apps.doodhcard.databinding.DeleteConfirmDialogBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemListAdapter
    private lateinit var viewModel: MilkCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MilkCardViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = ItemListAdapter(onItemUpdateCallback = { data, del -> itemUpdateCallback(data, del) })
        binding.mainItemList.adapter = adapter
        binding.addButton.setOnClickListener(itemAddListener)
        binding.clearButton.setOnClickListener(itemsClearListener)

        setupHeader()

        viewModel.total.observe(this){ total ->
            binding.totalAggregate.text = getString(R.string.total_text, Util.formattedDouble(total))
        }
        viewModel.data.observe(this){ entryList ->
            adapter.submitList(
                entryList.sortedWith{ o1, o2 -> Util.compareDateTimeStrings(o1.datetime, o2.datetime) }
            )
        }
    }

    private fun itemUpdateCallback(new_data: MilkCardEntity, delete: Boolean){
            if (delete) viewModel.deleteEntry(new_data)
            else viewModel.updateEntry(new_data)
    }

    private val itemAddListener = View.OnClickListener {
        createEditItemDialog(this, null) {  new_data ->
            new_data?.let { viewModel.insertEntry(it) }
        }

    }

    private val itemsClearListener = View.OnClickListener {
        val binding = DeleteConfirmDialogBinding.inflate(LayoutInflater.from(this), null, false)
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.yesButton.setOnClickListener { viewModel.deleteAllEntries(); dialog.dismiss() }
        binding.cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun setupHeader(){
        binding.header.itemTopLayout.background = ColorDrawable(getColor(R.color.primary_color))
        binding.header.seperator.visibility = View.GONE
        binding.header.milkDateTime.text = getString(R.string.date)
        binding.header.milkWeight.text = getString(R.string.weight)
        binding.header.milkFat.text = getString(R.string.fat)
        binding.header.milkPrice.text = getString(R.string.price)
        binding.header.milkRate.text = getString(R.string.rate)
        binding.header.editButton.visibility = View.INVISIBLE
        binding.header.milkDateTime.setTypeface(null, Typeface.BOLD_ITALIC)
        binding.header.milkRate.setTypeface(null, Typeface.BOLD_ITALIC)
        binding.header.milkFat.setTypeface(null, Typeface.BOLD_ITALIC)
        binding.header.milkPrice.setTypeface(null, Typeface.BOLD_ITALIC)
        binding.header.milkWeight.setTypeface(null, Typeface.BOLD_ITALIC)
        getColor(R.color.black).let {
            binding.header.milkWeight.setTextColor(it)
            binding.header.milkRate.setTextColor(it)
            binding.header.milkFat.setTextColor(it)
            binding.header.milkDateTime.setTextColor(it)
            binding.header.milkPrice.setTextColor(it)
        }
    }
}