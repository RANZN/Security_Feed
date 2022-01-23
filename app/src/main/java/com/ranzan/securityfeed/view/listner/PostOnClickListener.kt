package com.ranzan.securityfeed.view.listner

import com.ranzan.securityfeed.model.PostData

interface PostOnClickListener {
    fun onLike(postData: PostData)
    fun onComment(postData: PostData)
}