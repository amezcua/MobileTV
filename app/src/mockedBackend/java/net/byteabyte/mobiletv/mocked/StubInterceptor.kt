package net.byteabyte.mobiletv.mocked

import net.byteabyte.mobiletv.mocked.responses.configurationResult
import net.byteabyte.mobiletv.mocked.responses.showDetailsJsonResponse
import net.byteabyte.mobiletv.mocked.responses.similarShowsResponse
import net.byteabyte.mobiletv.mocked.responses.topRatedShowsResult
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Stub OkHttp interceptor that returns local responses instead of going to the network for matching
 * requests.
 * This interceptor would normally be defined only in an internal staging build and released
 * in private builds (by using a build flavor for example).
 * It is defined here in this sample for simplicity.
 */
class StubInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return stubbedResponseFor(chain.request())
    }

    private fun stubbedResponseFor(request: Request): Response = when {
        request.url.toString().contains("configuration") ->
            aResponse(configurationResult(), request).build()

        request.url.toString().contains("top_rated") ->
            aResponse(
                topRatedShowsResult(
                    1
                ), request).build()

        request.url.toString().contains("similar") ->
            aResponse(similarShowsResponse(), request).build()

        request.url.toString().contains("/3/tv/(d+)?".toRegex()) ->
            aResponse(showDetailsJsonResponse(), request).build()

        else -> anErrorResponse(request).build()
    }

    private fun aResponse(body: String, request: Request): Response.Builder =
        aResponseBuilder(request, 200, body, "Stub - ${request.method.padEnd(5, ' ')} served.")

    private fun anErrorResponse(request: Request): Response.Builder = aResponseBuilder(
        request,
        500,
        stubbedServerError,
        "Stub - ${request.method} not implemented?"
    )

    private fun aResponseBuilder(
        request: Request,
        code: Int,
        body: String,
        message: String
    ): Response.Builder =
        Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(code)
            .message(message)
            .body(jsonResponseBody(body))

    private val stubbedServerError = "{\"message\": \"Endpoint not yet stubbed!\"}"

    private fun jsonResponseBody(body: String) =
        body.toResponseBody("application/json".toMediaType())
}