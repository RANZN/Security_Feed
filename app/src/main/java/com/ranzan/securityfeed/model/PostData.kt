package com.ranzan.securityfeed.model


data class PostData(
    val key: String,
    val userUid: String,
    val userName: String,
    val postDescription: String,
    val imageUrl: String? = null,
    var likes: List<String>,
    var comments: List<Comments>? = null
) {
    constructor() : this("", "", "", "", null, mutableListOf(), null)
}

data class Comments(
    val userName: String,
    val comment: String
)