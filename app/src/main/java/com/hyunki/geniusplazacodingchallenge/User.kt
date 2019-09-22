package com.hyunki.geniusplazacodingchallenge

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:Int,
    val first_name:String,
    val last_name:String,
    val email: String?,
    val avatar:String?
): Parcelable