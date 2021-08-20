package com.odnovolov.randomuser.network;

import com.odnovolov.randomuser.network.model.RandomUserResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserService {
    @GET(".")
    Observable<Response<RandomUserResponse>> getRandomUsers(
        @Query("results") int number,
        @Query("gender") String gender
    );
}