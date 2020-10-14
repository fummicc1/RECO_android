package dev.fummicc1.reco.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.firebase.auth.FirebaseUser
import dev.fummicc1.reco.firebase.Auth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val SHAREDPREFERENCESNAME = "SharedPreferences"

class AuthRepository(context: Context): CoroutineScope {
    private val auth: Auth = Auth
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)

    private val _firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    public val firebaseUser: LiveData<FirebaseUser?>
        get() = _firebaseUser
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    init {
        launch {
            val user = auth.addAuthListener().currentUser
            _firebaseUser.postValue(user)
        }
    }

    public suspend fun signup(email: String): Unit {
        return suspendCoroutine<Unit> { continuation ->
            auth.createUser(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    val editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.apply()
                    continuation.resume(Unit)
                }
                it.exception?.let {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    public suspend fun login(email: String, emailLink: String) : FirebaseUser {
        return suspendCoroutine<FirebaseUser> { continuation ->
            auth.signInWithEmaiLink(email, emailLink).addOnCompleteListener {
                it.result?.user?.let {
                    continuation.resume(it)
                }
                it.exception?.let {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    public fun getSavedEmail(): String? {
        return sharedPreferences.getString("email", null)
    }
}