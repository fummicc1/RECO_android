package dev.fummicc1.sample.taskcalendar.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import dev.fummicc1.reco.repository.AuthRepository

class TaskListViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

    val isLoggedIn: LiveData<Boolean>
        get() = Transformations.map(authRepository.firebaseUser, { user ->
            return@map user != null
        })
}