package com.crypto.model

data class CryptoPrice(
    val description: String,
    val name: String,
    val period: String,
    val status: String,
    val unit: String,
    val values: List<Value>
)