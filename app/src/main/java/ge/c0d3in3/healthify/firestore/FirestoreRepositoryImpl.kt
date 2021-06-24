package ge.c0d3in3.healthify.firestore

import com.google.firebase.firestore.FirebaseFirestore
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.model.Response

class FirestoreRepositoryImpl(val firestore: FirebaseFirestore) {

    suspend inline fun <reified T> getDocument(collection: String, documentId: String): Response<T>{
        val result = firestore.collection(collection).document(documentId).get().execute()
        return when(result){
            is Response.Success -> {
                if(result.data.data == null)
                    Response.Fail("Document not found!")
                else
                    Response.Success(result.data.toObject(T::class.java)!!)
            }
            is Response.Fail -> Response.Fail(result.message)
        }
    }

    suspend fun <T: Any> addDocument(collection: String, document: T) {
        firestore.collection(collection).add(document).execute()
    }

    suspend fun <T: Any> setDocument(collection: String, documentId: String, document: T) {
        firestore.collection(collection).document(documentId).set(document).execute()
    }
}