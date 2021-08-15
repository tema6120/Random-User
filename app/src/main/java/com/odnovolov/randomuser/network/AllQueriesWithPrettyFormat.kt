package com.odnovolov.randomuser.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AllQueriesWithPrettyFormat @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest: Request = chain.request()

        val originalHttpUrl: HttpUrl = originalRequest.url()

        val newHttpUrl: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("format", "pretty")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()

        return chain.proceed(newRequest)
    }
}