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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostData

        if (key != other.key) return false
        if (userUid != other.userUid) return false
        if (userName != other.userName) return false
        if (postDescription != other.postDescription) return false
        if (imageUrl != other.imageUrl) return false
        if (likes != other.likes) return false
        if (comments != other.comments) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + userUid.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + postDescription.hashCode()
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + likes.hashCode()
        result = 31 * result + (comments?.hashCode() ?: 0)
        return result
    }


}

data class Comments(
    val userName: String,
    val comment: String
)