package xyz.youngzz.myg_ghub.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {

            getToken(context)?.let { token ->
                addHeader("Authorization", "bearer $token")
            }

        }.build()

        return chain.proceed(request)
    }
}