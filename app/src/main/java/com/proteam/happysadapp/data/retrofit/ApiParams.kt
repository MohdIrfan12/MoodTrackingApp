package com.tribe.fitness.data.retrofit

import java.util.*

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
class ApiParams {
    private var map = HashMap<String, String>()

    /**
     * Constructor
     *
     * @param builder object of builder class of ApiParams
     */
    constructor(builder: Builder) {
        map = builder.map
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    fun getMap(): HashMap<String, String> {
        return map
    }

    /**
     * The type Builder.
     */
    class Builder {

        /**
         * The Map.
         */
        val map = HashMap<String, String>()

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
            map[key] = value.toString()
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
            map[key] = value.toString()
            return this
        }

        /**
         * Build common params.
         *
         * @return the common params
         */
        fun build(): ApiParams {
            return ApiParams(
                this
            )
        }
    }
}