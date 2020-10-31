package dev.fummicc1.sample.taskcalendar.data

import com.google.firebase.firestore.DocumentReference
import io.grpc.android.BuildConfig
import java.sql.Timestamp

data class User(
    val goalRefArray: List<DocumentReference>,
    val createdAt: Timestamp
) {
    companion object {
        val COLLECTION_NAME = if (BuildConfig.DEBUG) "d_users" else "users"
    }
}