package com.rahul.apps.doodhcard

import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class ItemModel(
    val weight: Double = 0.0,
    val fat: Double = 0.0,
    val rate: Double = 0.0,
    val datetime: MEDate = MEDate(Calendar.getInstance())
){
    val price: Double
        get() = rate * weight
    companion object {
        fun from(item: ListEntryItem.EntryData): ItemModel {
            return ItemModel(
                weight = item.weight,
                fat = item.fat,
                rate = item.rate,
                datetime = item.datetime
            )
        }
    }
}
@JsonClass(generateAdapter = true)
class MEDate(dateTime: Calendar = Calendar.getInstance()){
    var session = "M"
    var date : Date = Calendar.getInstance().time
    init {
        val sdf1 = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        val midDay = sdf1.parse(sdf2.format(dateTime.time) + " 12:00:00")
        session =
            if(dateTime.time.after(midDay)) "E"
            else "M"
        date = Calendar.getInstance().time
    }
}

@JsonClass(generateAdapter = true)
data class ItemModelList(
    val list: MutableList<ItemModel> = mutableListOf<ItemModel>()
){
    companion object{
        fun from(recyclerViewData : MutableList<ListEntryItem>): ItemModelList {
            val serList = mutableListOf<ItemModel>()
            for(i in 1 until recyclerViewData.size){
                val item = recyclerViewData[i] as ListEntryItem.EntryData
                serList.add(ItemModel.from(item))
            }
            return ItemModelList(serList)
        }
    }
    fun toRecyclerViewData(): MutableList<ListEntryItem> {
        val retList: MutableList<ListEntryItem> = mutableListOf(ListEntryItem.Header)
        for(i in list) retList.add(ListEntryItem.EntryData.from(i))
        return retList
    }
}