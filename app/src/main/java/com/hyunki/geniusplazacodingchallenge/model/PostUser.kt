package com.hyunki.geniusplazacodingchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostUser(
    val first_name:String,
    val last_name:String,
    val email: String?
): Parcelable