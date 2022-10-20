package com.Doctoor.app.data.remote

import com.Doctoor.app.data.remote.RemoteConstants.CONTENT_TYPE
import com.Doctoor.app.data.remote.RemoteConstants.HEADER_AUTH
import com.Doctoor.app.utils.isEmpty
import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationInterceptor(private val tokenProducer: ServiceFactory.UserTokenProducer) :
    Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder();
        requestBuilder.addHeader(CONTENT_TYPE, "application/json")
        requestBuilder.addHeader("Accept", "application/json")
        val header = request.header(HEADER_AUTH)
        val token = tokenProducer.userToken
        if (isEmpty(header) && !isEmpty(token)) {
            requestBuilder.addHeader(HEADER_AUTH, "Bearer " + tokenProducer.userToken)
        }
        return chain.proceed(requestBuilder.build())
    }

}
