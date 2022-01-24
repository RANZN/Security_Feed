package com.ranzan.securityfeed.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ranzan.securityfeed.model.PostData

class DiffUtilCallBack(
    private val oldList: List<PostData>,
    private val newList: List<PostData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].key == newList[newItemPosition].key
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}