package com.example.crud_user

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    var password: String,
    @DrawableRes val avatar: Int
) : Parcelable
