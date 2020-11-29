package dev.fummicc1.sample.taskcalendar.ui.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import dev.fummicc1.reco.repository.AuthRepository
import kotlinx.coroutines.launch

class SignInViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository: AuthRepository = AuthRepository(application)
    val user: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun getSignInWithGoogleIntent(): Intent = authRepository.getSignInIntent()

    fun signInWithGoole(account: GoogleSignInAccount) {
        viewModelScope.launch {
            account.idToken?.let {
                val result = authRepository.signInWithGoogle(it)
                user.postValue(result)
            }
        }
    }
}