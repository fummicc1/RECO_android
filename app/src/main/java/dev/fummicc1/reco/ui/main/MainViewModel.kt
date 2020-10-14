package dev.fummicc1.reco.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.fummicc1.reco.repository.AuthRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository: AuthRepository = AuthRepository(application.applicationContext)

    val isLogin: LiveData<Boolean>
        get() = Transformations.map(authRepository.firebaseUser, {
            it != null
        })
}