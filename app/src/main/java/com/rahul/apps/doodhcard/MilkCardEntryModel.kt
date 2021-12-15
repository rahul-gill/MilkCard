package com.rahul.apps.doodhcard

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MilkCardEntryModel(
    val weight: Double = 0.0,
    val fat: Double = 0.0,
    val rate: Double = 0.0,
    val datetime: String
){
    companion object {
        fun from(item: ListEntryItem.EntryData): MilkCardEntryModel {
            return MilkCardEntryModel(
                weight = item.weight,
                fat = item.fat,
                rate = item.rate,
                datetime = item.datetime
            )
        }
    }
}

@JsonClass(generateAdapter = true)
data class ItemModelList(
    val list: MutableList<MilkCardEntryModel> = mutableListOf()
){
    companion object{
        fun from(recyclerViewData : MutableList<ListEntryItem>): ItemModelList {
            val serList = mutableListOf<MilkCardEntryModel>()
            for(i in 1 until recyclerViewData.size){
                val item = recyclerViewData[i] as ListEntryItem.EntryData
                serList.add(MilkCardEntryModel.from(item))
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