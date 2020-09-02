package com.tribe.fitness.data.retrofit

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class ProgressRequestBody:RequestBody {

    private val DEFAULT_BUFFER_SIZE = 2048
    private var mFile: File? = null
    private var mListener: UploadCallbacks? = null
//    private var apiListener: BaseApiHelper.ApiListener? = null

    /**
     * Instantiates a new Progress request body.
     *
     * @param file        the file
     * @param apiListener the api listener
     * @param listener    the listener
     */
    constructor(file: File?, /*apiListener: BaseApiHelper.ApiListener?,*/ listener: UploadCallbacks?) {
        mFile = file
        mListener = listener
//        this.apiListener = apiListener
    }

    override fun contentType(): MediaType? {
        return try {
            MediaType.parse(RetrofitUtils.getMimeType(mFile))
        } catch (e: Exception) {
//            if (apiListener != null) {
//                apiListener!!.onFailure(null, CustomException(CustomException.FILE_TYPE_EXCEPTION))
//            }
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile!!.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile!!.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = FileInputStream(mFile)
        var uploaded: Long = 0
        try {
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (`in`.read(buffer).also { read = it } != -1) { // update progress on UI thread
                handler.post(ProgressUpdater(uploaded, fileLength,mListener))
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        } finally {
            `in`.close()
        }
    }

    /**
     * The interface Upload callbacks.
     */
    interface UploadCallbacks {
        /**
         * On progress update.
         *
         * @param percentage the percentage
         */
        fun onProgressUpdate(percentage: Int)

        /**
         * On error.
         */
        fun onError()

        /**
         * On finish.
         */
        fun onFinish()
    }

    /**
     * The type Progress updater.
     */
    private class ProgressUpdater : Runnable {
        private val HUNDRED = 100
        private var mUploaded: Long = 0
        private var mTotal: Long = 0
        private var mListener: UploadCallbacks?;

        constructor(uploaded: Long, total: Long, callback: UploadCallbacks?) {
            mUploaded = uploaded
            mTotal = total
            this.mListener = callback;
        }

        override fun run() {
            if (mListener != null) {
                mListener!!.onProgressUpdate((HUNDRED * mUploaded / mTotal) as Int)
            }
        }
    }
}