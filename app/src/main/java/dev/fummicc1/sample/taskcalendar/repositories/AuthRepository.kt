package dev.fummicc1.reco.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import dev.fummicc1.reco.firebase.Auth
import dev.fummicc1.sample.taskcalendar.utils.SHAREDPREFERENCESNAME
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository(context: Context): CoroutineScope {
    private val auth: Auth = Auth
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)

    private val _firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val firebaseUser: LiveData<FirebaseUser?>
        get() = _firebaseUser
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    init {
        launch {
            auth.addAuthStateListener().collect {
                _firebaseUser.postValue(it.currentUser)
            }
        }
    }

    suspend fun signInAnonymously(): FirebaseUser = suspendCoroutine { continuation ->
        auth.signInAnonymously().addOnCompleteListener {
            val user = it.result?.user
            if (user != null) {
                continuation.resume(user)
            }
            it.exception?.let {
                continuation.resumeWithException(it)
            }
        }
    }

    suspend fun login(email: String, emailLink: String) : FirebaseUser {
        return suspendCoroutine<FirebaseUser> { continuation ->
        }
    }

    fun getSavedEmail(): String? {
        return sharedPreferences.getString("email", null)
    }
}