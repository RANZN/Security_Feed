package com.ranzan.securityfeed.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ranzan.securityfeed.model.PostData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TheViewModel : ViewModel() {

    private val db = Firebase.database.getReference("posts")
    private val listLiveData = MutableLiveData<List<PostData>>()
    private var auth = Firebase.auth
    private val toastLive = MutableLiveData<String>()
    private val progressLive = MutableLiveData<Int>()
    private val storage = FirebaseStorage.getInstance()


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


    fun sendPost(description: String, imageUri: Uri? = null) {
        toastLive.postValue("Sending Post...")
        val key = db.push().key
        val database = db.child(key!!)
        val currentUser = auth.currentUser!!
        if (imageUri != null) {
            val uploader = storage.getReference(key!!)
            uploader.putFile(imageUri).addOnProgressListener {
                progressLive.postValue(1)
            }.addOnSuccessListener {
                uploader.downloadUrl.addOnSuccessListener {
                    val postData = PostData(
                        key,
                        currentUser.uid,
                        currentUser.displayName!!,
                        description,
                        it.toString(),
                        listOf(currentUser.uid),
                        null
                    )
                    database.setValue(postData).addOnSuccessListener {
                        progressLive.postValue(2)


                        CoroutineScope(Dispatchers.Main).launch {
                            toastLive.postValue("Done")
                            delay(1000)
                            progressLive.postValue(4)
                        }

                    }.addOnFailureListener {
                        toastLive.postValue("Failed")
                        progressLive.postValue(3)
                    }
                }.addOnFailureListener {
                    toastLive.postValue("Failed to upload image")
                    progressLive.postValue(3)
                }
            }

        } else {
            val postData = PostData(
                key!!,
                auth.currentUser!!.uid,
                auth.currentUser!!.displayName!!,
                description,
                null,
                listOf(currentUser.uid),
                null
            )
            database.setValue(postData).addOnSuccessListener {
                progressLive.postValue(2)
                CoroutineScope(Dispatchers.Main).launch {
                    toastLive.postValue("Done")
                    delay(700)
                    progressLive.postValue(4)
                }
            }.addOnFailureListener {
                toastLive.postValue("Failed To Post")
                progressLive.postValue(3)
            }
        }
    }

    fun getToasts() = toastLive as LiveData<String>
    fun getProgress() = progressLive as LiveData<Int>
}