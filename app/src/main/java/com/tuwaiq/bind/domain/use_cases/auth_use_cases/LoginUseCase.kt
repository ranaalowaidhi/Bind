package com.tuwaiq.bind.domain.use_cases.auth_use_cases

import com.tuwaiq.bind.domain.repos.AuthRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepo
) {

    operator fun invoke(email:String,password:String) =
        repo.login(email, password)

}