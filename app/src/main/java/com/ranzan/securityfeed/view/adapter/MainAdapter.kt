package com.ranzan.securityfeed.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.PostLayoutPicBinding
import com.ranzan.securityfeed.databinding.PostLayoutTextBinding
import com.ranzan.securityfeed.model.PostData
import com.ranzan.securityfeed.view.listner.PostOnClickListener

class MainAdapter(private val postOnClickListener: PostOnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<PostData>()
    override fun getItemViewType(position: Int): Int {
        if (list[position].imageUrl == null) {
            return 1
        } else return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostLayoutTextBinding = DataBindingUtil.inflate(layoutInflater, R.layout.post_layout_text, parent, false)
            return PostTextViewHolder(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: PostLayoutPicBinding = DataBindingUtil.inflate(layoutInflater, R.layout.post_layout_pic, parent, false)
            return PostImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            (holder as PostTextViewHolder).bindData(list[position], postOnClickListener)
        } else {
            (holder as PostImageViewHolder).bindData(list[position], postOnClickListener)
        }
    }


    override fun getItemCount(): Int = list.size


    fun updateData(newList: ArrayList<PostData>) {
        val diffUtilCallBack = DiffUtilCallBack(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class PostTextViewHolder(private val postLayoutTextBinding: PostLayoutTextBinding) : RecyclerView.ViewHolder(postLayoutTextBinding.root) {
        fun bindData(postData: PostData, postOnClickListener: PostOnClickListener) {
            postLayoutTextBinding.apply {
                item = postData

                likeBtn.setOnClickListener {
                    postOnClickListener.onLike(postData)
//                    likeBtn.setBackgroundColor(getColor(it.context, R.color.purple_500))
                }
                commentBtn.setOnClickListener {
                    postOnClickListener.onComment(postData)
                }
            }
        }
    }

    class PostImageViewHolder(private val postLayoutPicBinding: PostLayoutPicBinding) : RecyclerView.ViewHolder(postLayoutPicBinding.root) {
        fun bindData(postData: PostData, postOnClickListener: PostOnClickListener) {
            postLayoutPicBinding.apply {
                item = postData
                likeBtn.setOnClickListener {
                    postOnClickListener.onLike(postData)
//                    likeBtn.setBackgroundColor(getColor(it.context, R.color.purple_500))
                }
                commentBtn.setOnClickListener {
                    postOnClickListener.onComment(postData)
                }
            }
            postLayoutPicBinding.postImage.apply {
                Glide.with(this).load(postData.imageUrl).into(this)
            }
        }
    }
}