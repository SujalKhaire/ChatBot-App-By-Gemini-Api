package com.example.mychatroom

import com.example.mychatroom.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositary(private val auth:FirebaseAuth,private val firestore:FirebaseFirestore){
    suspend fun signup(email:String,password:String,firstname:String,lastname:String){
        try {
                auth.createUserWithEmailAndPassword(email,password).await()
            val user = User(firstname,lastname, email)
            saveUserToFirestore(user)
                Result.Success(true)

        }catch (e:Exception){
                Result.Error(e)
        }
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }
}


