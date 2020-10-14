package dev.fummicc1.reco.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object Auth {

    private val auth: FirebaseAuth = Firebase.auth

    fun sendSignInLink(email: String): Task<Void> {
        val actionCodeSettings = actionCodeSettings {
            url = "https://github.com/fummicc1/RECO_android"
            handleCodeInApp = true
            iosBundleId = "dev.fummicc1.RECO"
            setAndroidPackageName(
                "dev.fummicc1.reco",
                true,
                "21"
            )
        }
        auth.sendSignInLinkToEmail(email, actionCodeSettings).addOnCompleteListener {
            print(it)
        }
        return auth.sendSignInLinkToEmail(email, actionCodeSettings)
    }

    fun signInWithEmaiLink(email: String, emailLink: String): Task<AuthResult?> {
        return auth.signInWithEmailLink(email, emailLink)
    }

    // to catch multi-shot response, use `callbackFlow`
    fun addAuthStateListener(): Flow<FirebaseAuth> = channelFlow {
        val listener = FirebaseAuth.AuthStateListener {
            offer(it)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }
}