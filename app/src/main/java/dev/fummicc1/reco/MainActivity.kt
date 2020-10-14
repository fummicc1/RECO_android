package dev.fummicc1.reco

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dev.fummicc1.reco.repository.SHAREDPREFERENCESNAME
import dev.fummicc1.reco.ui.signup.SignupFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle DynamicLinks
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                pendingDynamicLinkData?.let {
                    val emailLink = intent.data.toString()
                    emailLink?.let { emailLink ->
                        val fragment =
                            supportFragmentManager.findFragmentById(R.id.signupFragment) as? SignupFragment
                        fragment?.let {
                            it.onFindEmailLink(emailLink)
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("MainActivity", "getDynamicLink:onFailure", e)
            }
    }
}