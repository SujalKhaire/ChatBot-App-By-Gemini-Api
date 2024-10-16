package com.example.mychatroom


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){


    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<AuthState>()
    val authState:LiveData<AuthState> = _authstate











    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if (auth.currentUser == null){
            _authstate.value = AuthState.unauthenticated
        }
        else{
            _authstate.value = AuthState.authenticated
        }
    }

    fun signup(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _authstate.value = AuthState.error("Email or Password cant be empty")
            return
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            Task->
            if (Task.isSuccessful){
                _authstate.value = AuthState.authenticated
            }
            else{
                _authstate.value = AuthState.error(Task.exception?.message?:"Something went wrong")
            }
        }


    }
    fun login(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _authstate.value = AuthState.error("Email or Password cant be empty")
            return
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                Task->
            if (Task.isSuccessful){
                _authstate.value = AuthState.authenticated
            }
            else{
                _authstate.value = AuthState.error(Task.exception?.message?:"Something went wrong")
            }
        }


    }

    sealed class AuthState(){
        object authenticated:AuthState()
        object unauthenticated:AuthState()
        object loading:AuthState()
        data class error(val message:String):AuthState()
    }
}