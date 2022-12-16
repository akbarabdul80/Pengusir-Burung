package com.zero.pengusirburung.root

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RootViewModel : ViewModel() {

    // live data total (int)
    private val _dataDay = MutableLiveData<MutableList<Int>>()
    val dataDay: LiveData<MutableList<Int>>
        get() = _dataDay

    // set data total
    fun setDataDay(data: MutableList<Int>) {
        _dataDay.value = data
    }

    // Add data total
    fun addDataDay(data: Int, index: Int) {
        _dataDay.value?.set(index, data)
        _dataDay.value = _dataDay.value
    }

    // live data total (int)
    private val _dataTotal = MutableLiveData<MutableList<Int>>()
    val dataTotal: LiveData<MutableList<Int>>
        get() = _dataTotal

    // set data total
    fun setDataTotal(data: MutableList<Int>) {
        _dataTotal.value = data
    }

    // Add data total
    fun addDataTotal(data: Int, index: Int) {
        _dataTotal.value?.set(index, data)
        _dataTotal.value = _dataTotal.value
    }
}