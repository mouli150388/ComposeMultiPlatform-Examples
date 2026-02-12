package com.example.geofencecmp.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class AuthService {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    suspend fun signIn(email: String, pass: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass)
            Result.success(result.user?.uid ?: "Success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(name:String, email: String, mobile:String, emp_id:String, designation:String, pass: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass)
            val user_id = result.user?.uid ?: return Result.failure(Exception("User ID is null"))
            println("User created: $user_id")

            val userRef = db.collection("users").document(user_id)

            val document = userRef.get()
            if (!document.exists) {
                val profileData = mapOf(
                    "name" to name,
                    "mobile" to mobile,
                    "empId" to emp_id,
                    "designation" to designation,
                    "email" to email,
                    "password" to pass,
                    "userId" to user_id,
                )
                userRef.set(profileData)
                Result.success("User created and profile saved")
            } else {
                println("User document already exists.")
                Result.success("User created, profile already existed")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            Result.failure(e)
        }
    }

    fun getCurrentUser() = auth.currentUser
}