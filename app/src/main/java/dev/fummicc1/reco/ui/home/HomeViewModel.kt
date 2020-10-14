package dev.fummicc1.reco.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.fummicc1.reco.repository.AuthRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository: AuthRepository = AuthRepository(application.applicationContext)

    val isLogin: LiveData<Boolean>
        get() = Transformations.map(authRepository.firebaseUser, {
            it != null
        })
}