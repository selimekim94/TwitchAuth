package com.daredevil.mvitest.util

import android.util.Log
import retrofit2.Response

/**
 * Copied from Architecture components google sample:
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {

    companion object {
        private const val TAG: String = "AppDebug"


        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {

            Log.d(TAG, "ApiResponse: response: $response")
            Log.d(TAG, "ApiResponse: raw: ${response.raw()}")
            Log.d(TAG, "ApiResponse: headers: ${response.headers()}")
            Log.d(TAG, "ApiResponse: message: ${response.message()}")

            if (response.isSuccessful) {
                val body = response.body()
                return if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else if (response.code() == 401) {
                    ApiErrorResponse("401 Unauthorized. Token may be invalid.")
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
