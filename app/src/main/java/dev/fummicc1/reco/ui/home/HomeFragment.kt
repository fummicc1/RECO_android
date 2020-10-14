package dev.fummicc1.reco.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.fummicc1.reco.R
import dev.fummicc1.reco.databinding.FragmentMainBinding
import kotlinx.coroutines.Job

class HomeFragment : Fragment() {

    private val job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)
        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.apply {
            viewModel.isLogin.observe(viewLifecycleOwner, Observer {
                if (!it) {
                    findNavController().navigate(HomeFragmentDirections.actionMainFragmentToSignupFragment())
                }
            })
        }
        return binding.root
    }
}