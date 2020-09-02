package com.tribe.fitness.data.retrofit

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.proteam.happysadapp.BuildConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
object RetrofitUtils {
    private val TAG = RetrofitUtils::class.java.simpleName
    private val TIME_OUT = 30

    /**
     * Forms request body from string
     *
     * @param value content which need to convert into request body
     * @return formed request body object
     */
    fun getRequestBodyFromString(value: String?): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }


    /**
     * Forms a part body from file
     *
     * @param key  parameter name
     * @param file the file to include as part of request body
     * @return formed part body
     */
    fun getPartBodyFromFile(
        key: String?,
        file: File
    ): MultipartBody.Part? { // create RequestBody instance from file
        val requestFile =
            RequestBody.create(MediaType.parse(getMimeType(file)), file)
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(key, file.name, requestFile)
    }


    /**
     * Get the mime type
     *
     * @param file file for which mime type is required
     * @return the mimeType of the passed file
     */
    fun getMimeType(file: File?): String? {
        var mimeType: String? = "image/png"
        try {
            val selectedUri = Uri.fromFile(file)
            val fileExtension =
                MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString())
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return mimeType
    }

    /**
     * Parses error from the api response
     *
     * @param mRetrofit the m retrofit
     * @param response  the api response
     * @return parsed instance of ApiError
     */
    fun parseError(mRetrofit: Retrofit, response: Response<*>): ApiError? {
        val converter =
            mRetrofit.responseBodyConverter<ApiError>(
                ApiError::class.java,
                arrayOfNulls<Annotation>(0)
            )
        val error: ApiError?
        try {
            error = if (response.errorBody() != null) {
                converter.convert(response.errorBody())
            } else {
                ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            var statusCode: Int = StatusCode.DEFAULT_STATUS_CODE
            // keeping empty string as we cannot reference direct strings here
            var message: String? = ""
            if (response.code() != 0) {
                statusCode = response.code()
            }
            if (response.message() != null && !response.message().isEmpty()) {
                message = response.message()
            }
            return ApiError(statusCode, message)
        }
        return error
    }

    /**
     * Http client ok http client . builder.
     *
     * @param isLogEnabled       the is log enabled
     * @param isIncreasedTimeOut the is increased time out
     * @return the ok http client . builder
     */
    fun httpClient(
        isLogEnabled: Boolean,
        isIncreasedTimeOut: Boolean
    ): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        // add your other interceptors … // add logging as last interceptor
        if (isLogEnabled) {
            httpClient.addInterceptor(getLoggingInterceptor())
        }
        if (isIncreasedTimeOut) {
            httpClient.readTimeout(TIME_OUT.toLong(), TimeUnit.MINUTES)
            httpClient.connectTimeout(TIME_OUT.toLong(), TimeUnit.MINUTES)
        }
        return httpClient
    }

    /**
     * Method to get object of HttpLoggingInterceptor
     *
     * @return object of HttpLoggingInterceptor
     */
    fun getLoggingInterceptor(): HttpLoggingInterceptor? {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
        }
        return logging
    }
}