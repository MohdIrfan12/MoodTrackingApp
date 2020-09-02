package com.tribe.fitness.data.retrofit

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
interface ApiInterface {

    /**
     * Gets call.
     *
     * @param url the url
     * @return the call
     */
    @GET
    fun getCall(@Url url: String): Call<ApiResponse>

    /**
     * Gets call with header.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call with header
     */
    @GET
    fun getCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<ApiResponse>


    /**
     * Gets call without header.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call without header
     */
    @GET
    fun getCallWithoutHeader(@Url url: String, @QueryMap headerMap: Map<String, String>): Call<ApiResponse>

    /**
     * Gets call with query.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param queryMap  the query map
     * @return the call with query
     */
    @GET
    fun getCall(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>, @QueryMap queryMap: Map<String, String>
    ): Call<ApiResponse>

    /**
     * Post call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param fieldMap  the field map
     * @return the call
     */
    @FormUrlEncoded
    @POST
    fun postCall(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>, @FieldMap fieldMap: HashMap<String, String>
    ): Call<ApiResponse>

    /**
     * Post call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call
     */
    @POST
    fun postCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<ApiResponse>

    /**
     * Gets call with query.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param object    the object
     * @return the call with query
     */
    @POST
    fun postCall(@Url url: String, @HeaderMap headerMap: Map<String, String>, @Body `object`: Any): Call<ApiResponse>

    /**
     * Put call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call
     */
    @PUT
    fun putCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<ApiResponse>

    /**
     * Put call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param fieldMap  the field map
     * @return the call
     */
    @FormUrlEncoded
    @PUT
    fun putCall(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>, @FieldMap fieldMap: HashMap<String, String>
    ): Call<ApiResponse>

    /**
     * Post multipart call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param partMap   the part map
     * @return the call
     */
    @Multipart
    @POST
    fun postMultipartCall(
        @Url url: String, @HeaderMap headerMap: Map<String, String>,
        @PartMap partMap: HashMap<String, RequestBody>?
    ): Call<ApiResponse>

    /**
     * Put multipart call call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param partMap   the part map
     * @return the call
     */
    @Multipart
    @PUT
    fun putMultipartCall(
        @Url url: String, @HeaderMap headerMap: Map<String, String>,
        @PartMap partMap: HashMap<String, RequestBody>
    ): Call<ApiResponse>

    /**
     * Put call with out field call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call
     */
    @PUT
    fun putCallWithOutField(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<ApiResponse>

    /**
     * Delete with body call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param options   the options
     * @return the call
     */
    @HTTP(method = "DELETE", hasBody = true)
    fun deleteCallWithBody(
        @Url url: String, @HeaderMap headerMap: Map<String, String>,
        @Body options: Map<String, Any>
    ): Call<ApiResponse>

    /**
     * Post call call.
     *
     * @param url      the url
     * @param fieldMap the field map
     * @return the call
     */
    @POST
    fun postCallWithoutHeader(@Url url: String, @Body fieldMap: HashMap<String, String>): Call<ApiResponse>


    /**
     * delete call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @return the call
     */
    @DELETE
    fun deleteCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<ApiResponse>

    /**
     * Delete image call.
     *
     * @param url       the url
     * @param headerMap the header map
     * @param fieldMap  the field map
     * @return the call
     */
    @FormUrlEncoded
    @PUT
    fun deleteCall(
        @Url url: String, @HeaderMap headerMap: HashMap<String, String>,
        @FieldMap fieldMap: HashMap<String, String>
    ): Call<ApiResponse>
}