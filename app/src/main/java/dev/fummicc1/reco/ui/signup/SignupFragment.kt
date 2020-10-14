package dev.fummicc1.reco.ui.signup

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.fummicc1.reco.R
import dev.fummicc1.reco.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater,
            R.layout.fragment_signup,
            container,
            false
        )
        val viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {
            editTextTextEmailAddress.doAfterTextChanged {
                it?.toString()?.let {
                    viewModel.onEditEmail(it)
                }
            }
            viewModel.status.observe(viewLifecycleOwner, Observer {
                when (it) {
                    SignupViewModel.Status.EXCUTE -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.GONE
                }
                if (it == SignupViewModel.Status.WAITFORLINK) {
                    AlertDialog.Builder(context)
                        .setTitle("確認用メールを送信しました。")
                        .setMessage("メール内のリンクをタップしてアプリを起動してください。")
                        .setPositiveButton("OK", { dialog, which ->  })
                        .show()
                }
                if (it == SignupViewModel.Status.FAIL) {
                    Snackbar.make(requireView(), "エラーが発生しました", Snackbar.LENGTH_SHORT).show()
                }
            })
            viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToHomeFragment())
                }
            })
        }
        this.viewModel = viewModel
        return binding.root
    }
}