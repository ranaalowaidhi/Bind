package com.tuwaiq.bind.domain.repos


interface AuthRepo {

    fun signup(username:String,email:String,password:String)

    fun login(email:String,password:String)

}