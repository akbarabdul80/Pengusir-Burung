package com.zero.pengusirburung.ui.main.adapter

import android.graphics.Color
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zero.pengusirburung.R
import com.zero.pengusirburung.data.DataDetected
import com.zero.pengusirburung.data.DataItemGraph
import com.zero.pengusirburung.databinding.ItemGraphBinding
import com.zero.pengusirburung.root.RootViewModel
import com.zero.pengusirburung.utils.formatDate
import com.zero.pengusirburung.utils.formatSimpleDate
import com.zero.pengusirburung.utils.getDate
import im.dacer.androidcharts.LineView

class GraphAdapter(
    val viewModel: RootViewModel
) : BaseQuickAdapter<DataItemGraph, BaseDataBindingHolder<ItemGraphBinding>>(
    R.layout.item_graph
) {
    private lateinit var database: DatabaseReference

    override fun convert(holder: BaseDataBindingHolder<ItemGraphBinding>, item: DataItemGraph) {
        database = Firebase.database.reference

        holder.dataBinding?.let {
            it.tvName.text = item.name

            it.lineView.setDrawDotLine(true) //optional
            it.lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY) //optional

            it.lineView.setColorArray(
                intArrayOf(
                    Color.RED, Color.GRAY, Color.CYAN
                )
            )

            val dataItem: MutableList<Int> = mutableListOf()
            val dataDetectedItem: MutableList<DataDetected> = mutableListOf()

            database.child(item.id.toString()).child("detected")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataItem.clear()
                        dataDetectedItem.clear()

                        dataSnapshot.children.forEach {
                            val data = it.getValue(DataDetected::class.java)!!
                            dataDetectedItem.add(data)
                        }

                        for (i in 0..7) {
                            dataItem.add(dataDetectedItem.count {
                                it.datetime!!.formatDate() == getDate(
                                    i * -1
                                ).formatDate()
                            })
                        }

                        viewModel.addDataDay(dataItem[0], getItemPosition(item))
                        viewModel.addDataTotal(dataItem.sum(), getItemPosition(item))

                        val dataMonthList: MutableList<String> = mutableListOf()

                        for (i in 0..7) {
                            dataMonthList.add(getDate(i * -1).formatSimpleDate())
                        }

                        val monthList: ArrayList<String> = ArrayList(dataMonthList.reversed())
                        it.lineView.setBottomTextList(
                            monthList
                        )

                        val monthDataList: ArrayList<Int> = ArrayList(dataItem.reversed())

                        it.lineView.setDataList(
                            arrayListOf(
                                monthDataList
                            )
                        )

                        it.lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY)

                        Log.e("Data", dataItem[0].toString())
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

        }
    }

}