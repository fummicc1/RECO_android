package dev.fummicc1.reco.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.fummicc1.reco.repository.AuthRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupViewModel(application: Application): AndroidViewModel(application) {

    enum class Status {
        EXCUTE,
        SUCCESS,
        FAIL
    }

    private val authRepository: AuthRepository = AuthRepository

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
                val result = authRepository.signup(email)
                status.postValue(Status.SUCCESS)
            } catch (e: Exception) {
                status.postValue(Status.FAIL)
            }
        }
    }

}