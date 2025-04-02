package com.example.jc_example_1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    val identityNo: String,
    val phoneNumbers: List<ContactData>,
    val emails: List<ContactData>,

) : Parcelable

@Parcelize
data class ContactData(
    val id: Int,
    val value: String
) : Parcelable