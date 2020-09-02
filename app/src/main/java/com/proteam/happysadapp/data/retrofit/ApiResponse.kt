package com.tribe.fitness.data.retrofit

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class ApiResponse {
    /**
     * The LastRequestData.
     */
    @SerializedName("data")
    @Expose
    private var data: Any? = null
    @SerializedName("statusCode")
    @Expose
    private val statusCode: String? = null
    @SerializedName("message")
    @Expose
    private val message: String? = null

    private var apiError: ApiError? = null;

    private var apihitInProgress = false;

    /**
     * Get message from api response
     *
     * @return message message
     */
    fun getMessage(): String? {
        return message
    }

    /**
     * Get status code of api response
     *
     * @return statusCode status code
     */
    fun getStatusCode(): String? {
        return statusCode
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    fun setData(data: Any?) {
        this.data = data
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    fun getData(): Any? {
        return data
    }

    fun getApiError(): ApiError? = apiError;

    fun setApiError(error: ApiError?) {
        apiError = error;
    }

    override fun toString(): String {
        return "$statusCode $message\n$data"
    }

    fun enableApiHit() {
        apihitInProgress = true;
    }

    fun disableApiHit() {
        apihitInProgress = false;
    }

    /**
     * Get data model.
     *
     * @param <T>      the type parameter
     * @param classRef the class ref
     * @return t t
    </T> */
    fun <T> toResponseModel(classRef: Class<T>?): T {
        return Gson().fromJson(Gson().toJson(data), classRef)
    }
}