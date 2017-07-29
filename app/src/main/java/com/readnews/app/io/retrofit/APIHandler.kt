/*
 * Copyright (c) 2017, Mobilyte Tech India Pvt. Ltd. and/or its affiliates. All rights reserved.
 *     Redistribution and use in source and binary forms, with or without
 *     modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *   -->
 */

package com.readnews.app.io.retrofit

import com.readnews.app.BuildConfig
import com.readnews.app.beans.ArticleList
import com.readnews.app.beans.SourceList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
class APIHandler private constructor() {
    private var api: APIs? = null

    /**
     * declare static variables
     */
    companion object {
        private var handler: APIHandler? = null
        fun getInstance(): APIHandler {
            if (handler == null)
                handler = APIHandler()

            return handler as APIHandler
        }


    }

    /**
     * get the @Retrofit instance
     */
    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
                .client(client)
                .baseUrl(APIs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    /**
     * get the @APIs instance
     */
    private fun getAPIs(): APIs {
        if (api == null)
            api = getRetrofit().create(APIs::class.java)

        return api as APIs
    }

    /**
     * get the sources for news
     */
    fun getSources(callback: APICallback?): Call<SourceList> {
        val apiCall: Call<SourceList> = getAPIs().getSources()
        apiCall.enqueue(object : Callback<SourceList> {
            override fun onResponse(call: Call<SourceList>?, response: Response<SourceList>?) {
                callback?.onSuccess(call, response, APIs.TAG_SOURCES)
            }

            override fun onFailure(call: Call<SourceList>?, t: Throwable?) {
                callback?.onFailure(call, t, APIs.TAG_SOURCES)
            }

        })
        return apiCall
    }

    /**
     * get the articles list of particular source
     */
    fun getArticles(source: String?, apiKey: String, callback: APICallback?): Call<ArticleList> {
        val apiCall: Call<ArticleList> = getAPIs().getArticles(source, apiKey)
        apiCall.enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>?, response: Response<ArticleList>?) {
                callback?.onSuccess(call, response, APIs.TAG_ARTICLES)
            }

            override fun onFailure(call: Call<ArticleList>?, t: Throwable?) {
                callback?.onFailure(call, t, APIs.TAG_ARTICLES)
            }

        })
        return apiCall
    }
}