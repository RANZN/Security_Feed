package com.ranzan.securityfeed.model


data class PostData(
    val key: String,
    val userUid: String,
    val userName: String,
    val postDescription: String,
    val imageUrl: String? = null,
    val likes: Int,
    val comments: List<String>? = null
) {
    constructor() : this("", "", "", "", null, 0, null)
}