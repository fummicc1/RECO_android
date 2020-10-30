package dev.fummicc1.reco.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import dev.fummicc1.reco.data.User
import dev.fummicc1.reco.firebase.Auth
import dev.fummicc1.reco.firebase.Firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository(listen: Boolean) : CoroutineScope {

    private val firestore: Firestore = Firestore
    private val job: Job = Job()
    private val auth: Auth = Auth

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    init {
        if (listen)
            getMyUID()?.let {
                val flow = firestore.listenDocument<User>("users", it)
                launch {
                    flow.collect {
                        _user.postValue(it)
                    }
                }
            }
    }

    fun getMyUID(): String? = auth.getUserID()

    fun getMeRef(): DocumentReference? {
        val uid = getMyUID()
        return if (uid != null) firestore.getReference("users", uid) else null
    }

    suspend fun updateUser(user: User) = suspendCoroutine<Void> { continuation ->
        getMyUID()?.let {
            firestore.updateDocument("users", it, user).addOnCompleteListener {
                it.result?.let {
                    continuation.resume(it)
                }
                it.exception?.let {
                    continuation.resumeWithException(it)
                }
            }
        }
    }
}