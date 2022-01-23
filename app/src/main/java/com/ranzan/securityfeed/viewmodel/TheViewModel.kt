package com.ranzan.securityfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ranzan.securityfeed.model.PostData

class TheViewModel : ViewModel() {

    private val db = Firebase.database.getReference("posts")
    private val listLiveData = MutableLiveData<List<PostData>>()
    private var auth = Firebase.auth


    fun fetchData() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<PostData>()
                for (snapshot in snapshot.children) {
                    val data: PostData? = snapshot.getValue(PostData::class.java)
                    list.add(data!!)
                }
                list.reverse()
                listLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun getData() = listLiveData as LiveData<List<PostData>>


    private lateinit var data: PostData
    fun like(key: String) {

        db.child(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = snapshot.getValue(PostData::class.java) as PostData
                val likesList = data.likes
                val set=HashSet<String>(likesList)
                set.add(auth.currentUser!!.uid)
                data.likes = ArrayList<String>(set)
                db.child(key).setValue(data)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
}