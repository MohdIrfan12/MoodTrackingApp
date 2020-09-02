package com.tribe.fitness.data.retrofit

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class ApiError {
    @SerializedName("statusCode")
    private var statusCode = 0

    @SerializedName("message")
    private var message: String? = null


    /**
     * Constructor
     *
     * @param statusCode status code of api error response
     * @param message    message of api error response
     */
    constructor(statusCode: Int, message: String?) {
        this.message = message
        this.statusCode = statusCode
    }


    /**
     * Returns the status code of the response
     *
     * @return status code of api error response
     */
    fun getStatusCode(): Int {
        return if (statusCode == 0) {
            StatusCode.DEFAULT_STATUS_CODE
        } else statusCode
    }


    /**
     * Returns the message of the response
     *
     * @return the error message
     */
    fun getMessage(): String? {
        return message ?: ""
    }
}