package com.rahul.apps.doodhcard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "milk_data")

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemListAdapter
    private val moshiListAdapter= Moshi.Builder().build().adapter(ItemModelList::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = ItemListAdapter(onItemUpdateCallback = { totalUpdater() } )
        binding.mainItemList.adapter = adapter
        binding.addButton.setOnClickListener(itemAddListener)
        binding.clearButton.setOnClickListener(itemsClearListener)
        lifecycleScope.launch {
            preferenceRead("milk_data_list")
            totalUpdater()
        }
    }


    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            preferenceSave("milk_data_list")
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("milk_list_data", moshiListAdapter.toJson(ItemModelList.from(adapter.data)))
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("milk_list_data")?.let {
            adapter.data = moshiListAdapter.fromJson(it)!!.toRecyclerViewData()
            totalUpdater()
        }

    }

    private fun totalUpdater(){
        var total = 0.0
        for(i in 1 until adapter.data.size){
            total += (adapter.data[i] as ListEntryItem.EntryData).price
        }
        binding.totalAggregate.text = "Total:%.2f".format(total)
    }

    private suspend fun preferenceSave(key: String){
        dataStore.edit{ data ->
            data[stringPreferencesKey(key)] = moshiListAdapter.toJson(ItemModelList.from(adapter.data))
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private suspend fun preferenceRead(key: String){
        val preferences: Flow<Preferences> = dataStore.data
        val gottenData = preferences.first()[stringPreferencesKey(key)]
        if(gottenData != null){
            adapter.data =
                moshiListAdapter.fromJson(gottenData)!!.toRecyclerViewData()
            adapter.notifyDataSetChanged()
        }
    }

    private val itemAddListener = View.OnClickListener {
        createEditItemDialog(this, null) {  new_data ->
            adapter.data.add(ListEntryItem.EntryData.from(new_data!!))
            adapter.notifyItemChanged(adapter.data.size - 1)
            totalUpdater()
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    private val itemsClearListener = View.OnClickListener {
        binding.totalAggregate.text = "Total till now:  Rs 0.00"
        adapter.data = mutableListOf(ListEntryItem.Header)
        adapter.notifyDataSetChanged()
    }
}