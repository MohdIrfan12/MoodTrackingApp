package com.tribe.fitness.data.retrofit

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class StatusCode {
    companion object
    {
        const val DEFAULT_STATUS_CODE = 900
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
        const val CONFLICT = 409
        const val GONE = 410
    }
}