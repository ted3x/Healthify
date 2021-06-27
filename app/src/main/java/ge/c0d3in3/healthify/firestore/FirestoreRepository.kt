package ge.c0d3in3.healthify.firestore

import ge.c0d3in3.healthify.model.Response

interface FirestoreRepository {
    suspend fun <T> getDocument(collection: String, documentId: String, clazz: Class<T>): Response<T>

    suspend fun <T : Any> addDocument(collection: String, document: T)

    suspend fun <T : Any> setDocument(collection: String, documentId: String, document: T)
}