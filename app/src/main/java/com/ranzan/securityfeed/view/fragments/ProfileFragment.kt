package com.ranzan.securityfeed.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.FragmentProfileBinding
import com.ranzan.securityfeed.model.PostData
import com.ranzan.securityfeed.view.adapter.MainAdapter
import com.ranzan.securityfeed.view.listner.OnClickProfile
import com.ranzan.securityfeed.view.listner.PostOnClickListener
import com.ranzan.securityfeed.viewmodel.TheViewModel

class ProfileFragment : Fragment(), PostOnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var onClickProfile: OnClickProfile
    private lateinit var viewmodel: TheViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickProfile = context as OnClickProfile
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProvider(requireActivity()).get(TheViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvName.text = auth.currentUser!!.displayName

            menuBtn.setOnClickListener {
                onClickProfile.detailedView()
            }

            addPost.setOnClickListener {
                onClickProfile.addPost()
            }
        }

        viewmodel.getData().observe(viewLifecycleOwner, Observer {
            val list = mutableListOf<PostData>()
            it.forEach {
                if (it.userUid == auth.currentUser!!.uid) {
                    list.add(it)
                }
            }
            setRecyclerView(list as ArrayList<PostData>)
        })
    }

    private fun setRecyclerView(list: ArrayList<PostData>) {
        binding.postRecyclerView.apply {
            adapter = MainAdapter(list,this@ProfileFragment)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onLike() {

    }

    override fun onComment() {

    }
}