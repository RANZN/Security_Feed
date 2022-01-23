package com.ranzan.securityfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ranzan.securityfeed.model.PostData

class TheViewModel : ViewModel() {

    private val db = Firebase.database.getReference("posts")
    private val list = mutableListOf<PostData>()
    private val liveData = MutableLiveData<List<PostData>>()
    fun getData(): LiveData<List<PostData>> {

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children) {
                    val data = snapshot.getValue(PostData::class.java)
                    list.add(data!!)
                }
                liveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return liveData
    }
}