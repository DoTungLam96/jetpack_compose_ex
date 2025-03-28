package com.example.jc_example_1.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class LoginRequest(
    val identityNo: String,
    val password: String,
    val partnerName: String = "pticare",
    val source: String = "pti"
) : Parcelable

@Parcelize
data class LoginResponse(
    val code: Int,
    val description: String,
    val data: String,
) : Parcelable
