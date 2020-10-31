package dev.fummicc1.reco.firebase

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.fummicc1.sample.taskcalendar.utils.DEFAULT_WEB_CLIENT_ID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object Auth {

    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient

    fun configureGoogleSignInClient(context: Context) {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(DEFAULT_WEB_CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getUserID(): String? {
       return auth.currentUser?.uid
    }

    fun checkHasGoogleSignedInAccount(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null
    }

    fun getSignInIntent(context: Context): Intent {
        if (!this::googleSignInClient.isInitialized) {
            configureGoogleSignInClient(context)
        }
        return googleSignInClient.signInIntent
    }

    fun signInAnonymously(): Task<AuthResult> {
        return auth.signInAnonymously()
    }

    // to catch multi-shot response, use `callbackFlow`
    fun addAuthStateListener(): Flow<FirebaseAuth> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener {
            offer(it)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }
}