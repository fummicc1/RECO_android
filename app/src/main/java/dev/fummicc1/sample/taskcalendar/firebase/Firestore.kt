package dev.fummicc1.reco.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object Firestore {

    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getReference(collectionName: String, documentID: String): DocumentReference {
        return firestore.collection(collectionName).document(documentID)
    }

    fun getDocument(collectionName: String, documentID: String): Task<DocumentSnapshot> {
        return firestore.collection(collectionName).document(documentID).get()
    }

    fun getDocuments(collectionName: String): Task<QuerySnapshot> {
        return firestore.collection(collectionName).get()
    }

    fun <T : Any> createDocument(collectionName: String, data: T): Task<Void> {
        return firestore.collection(collectionName).document().set(data)
    }

    inline fun <reified T> listenDocument(collectionName: String, documentID: String) = callbackFlow<T> {
        val listener = firestore.collection(collectionName).document(documentID).addSnapshotListener { value, error ->
            value?.let {
                val data = it.toObject(T::class.java)
                data?.let {
                    this.offer(it)
                }
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    inline fun <reified T: Any> listenCollection(collectionName: String) = callbackFlow<List<T>> {
        val listener = firestore.collection(collectionName).addSnapshotListener { value, error ->
            value?.let {
                // mapNotNull make T subclass of `Any`.
                val data = it.documents.mapNotNull { it.toObject(T::class.java) }
                this.offer(data)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    inline fun <reified T: Any> listenCollectionWithQuery(collectionName: String, fieldPath: FieldPath, value: Any) = callbackFlow<List<T>> {
        val listener = firestore.collection(collectionName).whereEqualTo(fieldPath, value).addSnapshotListener { value, error ->
            value?.let {
                // mapNotNull make T subclass of `Any`.
                val data = it.documents.mapNotNull { it.toObject(T::class.java) }
                this.offer(data)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    inline fun <reified T: Any> updateDocument(collectionName: String, documentID: String, data: T): Task<Void> {
        return firestore.collection(collectionName).document(documentID).set(data, SetOptions.merge())
    }
}