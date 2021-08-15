package com.odnovolov.randomuser.network

import com.odnovolov.randomuser.network.model.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET(".")
    suspend fun getRandomUsers(
        @Query("results") number: Int,
        @Query("gender") gender: String? = null
    ): Response<RandomUserResponse>
}