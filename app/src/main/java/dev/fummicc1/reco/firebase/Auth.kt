package dev.fummicc1.reco.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object Auth {

    private val auth: FirebaseAuth = Firebase.auth

    fun createUser(email: String): Task<Void> {
        val actionCodeSettings = actionCodeSettings {
            url = "https://fummicc1.page.link/zXbp"
            setAndroidPackageName(
                "dev.fummicc1.reco",
                true,
                "21"
            )
        }
        return auth.sendSignInLinkToEmail(email, actionCodeSettings)
    }

    fun signInWithEmaiLink(email: String, emailLink: String): Task<AuthResult?> {
        return auth.signInWithEmailLink(email, emailLink)
    }

    suspend fun addAuthListener(): FirebaseAuth {
        return suspendCoroutine { continuation ->
            auth.addAuthStateListener {
                continuation.resume(it)
            }
        }
    }
}