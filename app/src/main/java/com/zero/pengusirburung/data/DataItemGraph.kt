package com.zero.pengusirburung.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItemGraph(
    val id: String? = "",
    val name: String? = "",
) : Parcelable