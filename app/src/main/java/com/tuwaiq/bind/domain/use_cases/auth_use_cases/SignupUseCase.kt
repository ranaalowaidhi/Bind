package com.tuwaiq.bind.domain.use_cases.auth_use_cases

import com.tuwaiq.bind.domain.repos.AuthRepo
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repo:AuthRepo
){

    operator fun invoke(username:String,email:String,password:String)=
        repo.signup(username,email,password)

}