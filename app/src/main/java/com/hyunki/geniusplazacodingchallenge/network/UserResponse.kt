package com.hyunki.geniusplazacodingchallenge.network

import android.os.Parcelable
import com.hyunki.geniusplazacodingchallenge.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
): Parcelable