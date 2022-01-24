package com.ranzan.securityfeed.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranzan.securityfeed.R
import com.ranzan.securityfeed.databinding.FragmentHomeBinding
import com.ranzan.securityfeed.model.PostData
import com.ranzan.securityfeed.view.adapter.MainAdapter
import com.ranzan.securityfeed.view.listner.PostOnClickListener
import com.ranzan.securityfeed.viewmodel.TheViewModel

class HomeFragment : Fragment(), PostOnClickListener{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewmodel: TheViewModel
    private lateinit var mainAdapter: MainAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProvider(requireActivity()).get(TheViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        viewmodel.getData().observe(viewLifecycleOwner, Observer {list->
            mainAdapter.updateData(list as ArrayList<PostData>)
            binding.progBar.visibility = View.GONE
        })
        binding.addPostBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragment_container, PostFragment()).addToBackStack("post").commit()
        }
    }

    private fun setRecyclerView() {
        mainAdapter = MainAdapter(this)
        binding.recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onLike(postData: PostData) {
        val key = postData.key
        viewmodel.like(key)
    }

    override fun onComment(postData: PostData) {

    }
}