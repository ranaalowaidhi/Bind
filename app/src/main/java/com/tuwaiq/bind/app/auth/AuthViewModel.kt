package com.tuwaiq.bind.app.auth

import androidx.lifecycle.ViewModel
import com.tuwaiq.bind.domain.use_cases.auth_use_cases.LoginUseCase
import com.tuwaiq.bind.domain.use_cases.auth_use_cases.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signupUseCase:SignupUseCase,
    private val loginUseCase:LoginUseCase
): ViewModel() {

    fun signup(userName:String,email:String,password:String) =
        signupUseCase(userName,email,password)

    fun login(email: String,password: String) =
        loginUseCase(email, password)

}