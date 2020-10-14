package dev.fummicc1.reco.ui.signup

import android.app.Application
import androidx.lifecycle.*
import dev.fummicc1.reco.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignupViewModel(application: Application): AndroidViewModel(application) {

    enum class Status {
        EXCUTE,
        WAITFORLINK,
        SUCCESS,
        FAIL
    }

    private val authRepository: AuthRepository = AuthRepository(application.applicationContext)

    val isLoggedIn: LiveData<Boolean>
        get() = Transformations.map(authRepository.firebaseUser, {
            it != null
        })
    val status: MutableLiveData<SignupViewModel.Status> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()

    fun signup() {
        if (status.value == Status.EXCUTE) {
            return
        }
        val email = email.value ?: ""
        status.postValue(Status.EXCUTE)
        viewModelScope.launch {
            try {
                authRepository.signup(email)
                status.postValue(Status.WAITFORLINK)
            } catch (e: Exception) {
                status.postValue(Status.FAIL)
            }
        }
    }

    fun onEditEmail(email: String) {
        this.email.postValue(email)
    }
}