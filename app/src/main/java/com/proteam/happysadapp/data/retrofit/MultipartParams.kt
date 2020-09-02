package com.tribe.fitness.data.retrofit

import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class MultipartParams {
    private var map = HashMap<String, RequestBody>()

    /**
     * Constructor
     *
     * @param builder object of builder class of MultipartParams
     */
    private constructor(builder: Builder) {
        map = builder.map
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    fun getMap(): HashMap<String, RequestBody>? {
        return map
    }


    /**
     * The type Builder.
     */
    class Builder
    /**
     * Instantiates a new Builder.
     */
    {
        val map = HashMap<String, RequestBody>()

        /**
         * Add builder.
         *
         * @param key   the key
         * @param value the value
         * @return the builder
         */
        fun add(key: String, value: Any?): Builder {
            if (value == null || value.toString().isEmpty()) {
                return this
            }
            map[key] = RetrofitUtils.getRequestBodyFromString(value.toString())
            return this
        }

        /**
         * Add builder.
         *
         * @param key          the key
         * @param value        the value
         * @param isAllowEmpty the is allow empty
         * @return the builder
         */
        fun add(key: String, value: Any?, isAllowEmpty: Boolean): Builder {
            if (!isAllowEmpty && (value == null || value.toString().isEmpty())) {
                return this
            }
            map[key] = RetrofitUtils.getRequestBodyFromString(value.toString())
            return this
        }

        /**
         * Add file builder.
         *
         * @param key   the key
         * @param mFile the m file
         * @return the builder
         */
        fun addFile(key: String, mFile: File?): Builder {
            if (mFile == null) {
                return this
            }
            map[key + "\"; filename=\"" + mFile.name] =
                RequestBody.create(MediaType.parse(RetrofitUtils.getMimeType(mFile)), mFile)
            return this
        }

        /**
         * Add file with progress builder.
         *
         * @param key       the key
         * @param mFile     the m file
         * @param mListener the m listener
         * @param mCallBack the m call back
         * @return the builder
         */
        fun addFileWithProgress(
            key: String, mFile: File?,
            mCallBack: ProgressRequestBody.UploadCallbacks?
        ): Builder {
            if (mFile == null) {
                return this
            }
            map.put(
                key + "\"; filename=\"" + mFile.name,
                ProgressRequestBody(mFile, /*mListener,*/ mCallBack)
            )
            return this
        }

        /**
         * Add array of files builder.
         *
         * @param key            the key
         * @param mFileArrayList the m file array list
         * @return the builder
         */
        fun addArrayOfFiles(key: String, mFileArrayList: ArrayList<File?>?): Builder {
            if (mFileArrayList == null || mFileArrayList.size == 0) {
                return this
            }
            for (i in mFileArrayList.indices) {
                if (mFileArrayList[i] != null) {
                    map[key + "\"; filename=\"" + mFileArrayList[i]!!.name] = RequestBody.create(
                        MediaType.parse(RetrofitUtils.getMimeType(mFileArrayList[i])),
                        mFileArrayList[i]
                    )
                }
            }
            return this
        }

        /**
         * Build multipart params.
         *
         * @return the multipart params
         */
        fun build(): MultipartParams {
            return MultipartParams(
                this
            )
        }
    }
}