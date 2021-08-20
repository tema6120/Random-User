package com.odnovolov.randomuser.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AllQueriesWithPrettyFormat implements Interceptor {
    @Inject
    public AllQueriesWithPrettyFormat() {
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("format", "pretty")
                .build();

        Request newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build();

        return chain.proceed(newRequest);
    }
}