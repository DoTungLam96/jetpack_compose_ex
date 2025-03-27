package com.example.jc_example_1.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val id: String, val name: String) : Parcelable


