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

import com.readnews.app.beans.ArticleList
import com.readnews.app.beans.SourceList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
interface APIs {
    companion object {
        const val BASE_URL: String = "https://newsapi.org/v1/"
        const val TAG_SOURCES: String = "sources"
        const val TAG_ARTICLES: String = "articles"
    }

    @GET("sources")
    fun getSources(): Call<SourceList>

    @GET("articles")
    fun getArticles(@Query("source") source: String?, @Query("apiKey") apiKey: String): Call<ArticleList>
}