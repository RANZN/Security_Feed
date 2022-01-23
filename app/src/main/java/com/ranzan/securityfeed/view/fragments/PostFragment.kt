package com.ranzan.securityfeed.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.FragmentPostBinding
import com.ranzan.securityfeed.model.PostData
import com.ranzan.securityfeed.viewmodel.TheViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: TheViewModel
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.database.getReference("posts")
    private val storage = FirebaseStorage.getInstance()
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(TheViewModel::class.java)
        auth = Firebase.auth
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            postBackgroundLayout.setOnClickListener {

            }
            cancelPost.setOnClickListener {
                activity?.onBackPressed()
            }

            attachImage.setOnClickListener {
                //Opening Gallery to select image
                val galleryIntent = Intent()
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                galleryIntent.type = "image/*"
                startActivityForResult(galleryIntent, 1)
            }

            postBtn.setOnClickListener {
                sendPost()
            }
        }
    }

    private fun sendPost() {
        Toast.makeText(context, "Sending Post..", Toast.LENGTH_SHORT).show()
        val key = db.push().key
        val currentUser = auth.currentUser!!
        if (this::imageUri.isInitialized) {
            val uploader = storage.getReference(key!!)
            uploader.putFile(imageUri).addOnProgressListener {
                binding.apply {
                    loadingScreen.visibility = View.VISIBLE
                    progressBar.visibility = View.VISIBLE
                    postBtn.visibility = View.INVISIBLE
                }
            }.addOnSuccessListener {
                uploader.downloadUrl.addOnSuccessListener {
                    val postData = PostData(
                        key,
                        currentUser.uid,
                        currentUser.displayName!!,
                        binding.postDescription.text.toString(),
                        it.toString(),
                        listOf(currentUser.uid),
                        null
                    )
                    db.push().setValue(postData).addOnSuccessListener {
                        binding.apply {
                            loadingScreen.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            postBtn.visibility = View.VISIBLE
                            postBtn.text = "DONE"
                            postBtn.setBackgroundColor(resources.getColor(R.color.yellow))
                        }

                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                            delay(1000)
                            activity?.onBackPressed()
                        }

                    }.addOnFailureListener {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            val postData = PostData(
                key!!,
                auth.currentUser!!.uid,
                auth.currentUser!!.displayName!!,
                binding.postDescription.text.toString(),
                null,
                listOf(currentUser.uid),
                null
            )
            db.push().setValue(postData).addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                binding.postBtn.apply {
                    text = "DONE"
                    setBackgroundColor(resources.getColor(R.color.yellow))
                }
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                    delay(700)
                    activity?.onBackPressed()
                }
            }.addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Failed To Post", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.selectedImage.apply {
                visibility = View.VISIBLE
                setImageURI(imageUri)
            }
        }
    }
}