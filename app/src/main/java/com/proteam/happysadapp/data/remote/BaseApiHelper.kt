package com.tribe.fitness.data

/**
 * Base Abstraction layer over remote data storage access class. In simple words it's
 * purpose is to server as abstration layer over your retrofit api call
 * All abstraction layers of remote data access class must extend it.
 *
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
interface BaseApiHelper {


    /**
     * The interface Upload file progress listener.
     */
    interface UploadFileProgressListener {
        /**
         * On progress update.
         *
         * @param progress the progress
         */
        fun onProgressUpdate(progress: Int)
    }
}