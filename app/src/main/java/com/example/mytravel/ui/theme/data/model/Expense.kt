package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("date") val date: String,
    @SerializedName("category") val category: String,
    @SerializedName("paidBy") val paidBy: String,
    @SerializedName("isShared") val isShared: Boolean,
    @SerializedName("contributions") val contributions: List<Contribution>?
)

data class Contribution(
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("amount") val amount: Double
)
