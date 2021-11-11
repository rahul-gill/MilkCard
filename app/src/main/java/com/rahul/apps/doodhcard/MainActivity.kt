package com.rahul.apps.doodhcard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.rahul.apps.doodhcard.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "milk_data")

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ItemListAdapter
    private val moshiAdapter= Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build().adapter(ItemModel::class.java)
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = ItemListAdapter()
        binding.mainItemList.adapter = adapter
        binding.addButton.setOnClickListener{
            val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
            val meDate = MEDate(Calendar.getInstance())
            createEditItemDialog(this, sdf2.format(meDate.date) + " "+ meDate.session, null){
                adapter.data.add(ListEntryItem.EntryData.from(it))
                adapter.notifyItemChanged(adapter.data.size - 1)
                var total = 0.0
                for(i in adapter.data) if(i is ListEntryItem.EntryData) total += i.price
                binding.totalAggregate.text = "Total till now:  Rs %.2f".format(total)
            }
        }
        binding.clearButton.setOnClickListener {
            binding.totalAggregate.text = "Total till now:  Rs 0.00"
            adapter.data = mutableListOf(ListEntryItem.Header)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            save("milk_data_list", adapter.data)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val list: MutableList<ListEntryItem> = mutableListOf(ListEntryItem.Header)
            var total = 0.0
            read("milk_data_list").forEach {
                list.add(ListEntryItem.EntryData.from(it))
                total += it.price
            }
            adapter.data = list
            binding.totalAggregate.text = "Total till now:  Rs %.2f".format(total)
        }

    }

    private suspend fun save(key: String, value: MutableList<ListEntryItem>){
        val serList = mutableListOf<ItemModel>()
        for(i in 1 until value.size){
            val item = value[i] as ListEntryItem.EntryData
            serList.add(ItemModel(
                weight = item.weight,
                fat = item.fat,
                rate = item.rate,
                datetime = item.datetime

            ))
        }
        dataStore.edit{ data ->
            for(i in serList.indices) {
                val dataStoreKey = stringPreferencesKey(key + i.toString())
                data[dataStoreKey] = moshiAdapter.toJson(serList[i])
            }
            val dataStoreKey = stringPreferencesKey("$key-1")
            data[dataStoreKey] = serList.size.toString()
        }
    }
    private suspend fun read(key: String): MutableList<ItemModel> {
        val serList = mutableListOf<ItemModel>()
        val sizeStoreKey = stringPreferencesKey("$key-1")
        val preferences: Flow<Preferences> = dataStore.data
        val arrSize = preferences.first()[sizeStoreKey]?.toInt() ?: return mutableListOf()
        for(i in 0 until arrSize){
            val dataStoreKey = stringPreferencesKey(key + i.toString())
            serList.add(moshiAdapter.fromJson(preferences.first()[dataStoreKey]!!)!!)
        }
        return serList
    }
}