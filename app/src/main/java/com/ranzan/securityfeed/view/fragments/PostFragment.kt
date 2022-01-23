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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.FragmentPostBinding
import com.ranzan.securityfeed.viewmodel.TheViewModel

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: TheViewModel
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(TheViewModel::class.java)
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
            progressObserver()
        }
    }

    private fun progressObserver() {
        viewModel.getToasts().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.getProgress().observe(viewLifecycleOwner, Observer { value ->
            if (value == 1) {
                binding.apply {
                    loadingScreen.visibility = View.VISIBLE
                    progressBar.visibility = View.VISIBLE
                    postBtn.visibility = View.INVISIBLE
                }
            } else if (value == 2) {
                binding.apply {
                    loadingScreen.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    postBtn.visibility = View.VISIBLE
                    postBtn.text = "DONE"
                    postBtn.setBackgroundColor(resources.getColor(R.color.yellow))
                }
            } else if (value == 3) {
                binding.progressBar.visibility = View.GONE
            } else if (value == 4) {
                activity?.onBackPressed()

            }
        })
    }

    private fun sendPost() {
        if (this::imageUri.isInitialized) {
            viewModel.sendPost(binding.postDescription.text.toString(), imageUri)
        } else {
            viewModel.sendPost(binding.postDescription.text.toString())
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