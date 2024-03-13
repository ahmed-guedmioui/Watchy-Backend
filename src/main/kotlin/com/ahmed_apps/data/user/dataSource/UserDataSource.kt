package com.ahmed_apps.data.user.dataSource

import com.ahmed_apps.data.user.model.User

interface UserDataSource {

    suspend fun getUserByEmail(email: String): User?

    suspend fun insertUser(user: User): Boolean

    suspend fun updateUser(user: User): Boolean

}