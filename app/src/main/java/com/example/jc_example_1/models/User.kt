package com.example.jc_example_1.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId: Long,       // Bắt buộc có giá trị
    val fullName: String?,
    val gender: String?,
    val identityNo: String?,
    val issueDate: String?,
    val issuePlace: String?,
    val expiryDate: String?,
    val dob: String?,
    val address: String?,
    val username: String?,
    val status: String?,
    val email: String?,
    val mobilePhone: String?
) : Parcelable