package com.tuwaiq.bind.data.repos


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.domain.repos.AuthRepo

private lateinit var auth: FirebaseAuth
private const val TAG = "AuthRepoImp"

class AuthRepoImp:AuthRepo {

    override fun signup(username: String, email: String, password: String) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:success")
                }else{
                    Log.e(TAG,"something goose wrong")
                }
            }

        val updateProfile = userProfileChangeRequest{
            displayName = username
        }

        auth.currentUser?.updateProfile(updateProfile)
    }

    override fun login(email: String, password: String) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    Log.e(TAG, "something goose wrong")
                }
            }
    }
}