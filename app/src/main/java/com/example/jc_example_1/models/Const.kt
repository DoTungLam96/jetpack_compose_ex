package com.example.jc_example_1.models

import androidx.datastore.preferences.core.stringPreferencesKey

object Const {
    const val HOME_SCREEN = "homeScreen"
    const val DETAIL_SCREEN = "detailScreen"
    const val LOGIN_SCREEN = "loginScreen"
    const val OTHER_SCREEN = "otherScreen"
    const val BASE_URL = "https://jsonplaceholder.typicode.com"
    const val BASE_URL_PTI = "https://gateway.pti.com.vn"
    const val AUTH_SERVICE = "authService"
    const val USER_SERVICE = "userService"
//    const val ACCESS_TOKEN = "accessToken"
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
}

