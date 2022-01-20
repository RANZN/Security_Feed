package com.ranzan.securityfeed.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ranzan.securityfeed.databinding.FragmentDetailedBinding
import com.ranzan.securityfeed.view.activity.LoginActivity
import com.ranzan.securityfeed.view.listner.OnClickProfile


class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var onClickProfile: OnClickProfile

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickProfile = context as OnClickProfile
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        binding = FragmentDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            profileName.text = auth.currentUser!!.displayName
            profileEmail.text = auth.currentUser!!.email
        }

        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onClickProfile.detailedBackPressed()
    }
}