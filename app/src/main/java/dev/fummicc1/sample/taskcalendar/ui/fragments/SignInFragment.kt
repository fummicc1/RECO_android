package dev.fummicc1.sample.taskcalendar.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dev.fummicc1.sample.taskcalendar.R
import dev.fummicc1.sample.taskcalendar.databinding.FragmentSignInBinding
import dev.fummicc1.sample.taskcalendar.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewModel: SignInViewModel by viewModels()
        val binding = DataBindingUtil.inflate<FragmentSignInBinding>(inflater, R.layout.fragment_sign_in, container, false)
        binding.apply {
            signInWithGoogleButton.setOnClickListener {
                startActivityForResult(viewModel.getSignInWithGoogleIntent(), 100)
            }
        }
        viewModel.user.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val viewModel: SignInViewModel by viewModels()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("SignInFragment", "firebaseAuthWithGoogle" + account.id)
                viewModel.signInWithGoole(account)
            } catch (e: ApiException) {
                Log.w("SignInFragment", "Failed to Google SignIn", e)
            }
        }
    }
}