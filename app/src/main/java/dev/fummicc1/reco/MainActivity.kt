package dev.fummicc1.reco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dev.fummicc1.reco.repository.AuthRepository
import dev.fummicc1.reco.ui.signup.SignupFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authRepository = AuthRepository(applicationContext)

        // Handle DynamicLinks
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                pendingDynamicLinkData?.let {
                    val emailLink = intent.data.toString()
                    emailLink?.let { emailLink ->
                        authRepository.getSavedEmail()?.let { email ->
                            lifecycleScope.launch {
                                authRepository.login(email, emailLink)
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("MainActivity", "getDynamicLink:onFailure", e)
            }
    }
}