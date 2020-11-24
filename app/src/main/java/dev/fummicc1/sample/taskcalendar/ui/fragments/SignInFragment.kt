package dev.fummicc1.sample.taskcalendar.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dev.fummicc1.sample.taskcalendar.R
import dev.fummicc1.sample.taskcalendar.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSignInBinding>(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }
}