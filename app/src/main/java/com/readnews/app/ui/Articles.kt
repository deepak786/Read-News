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

package com.readnews.app.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.readnews.app.BuildConfig
import com.readnews.app.R
import com.readnews.app.adapters.ArticleListAdapter
import com.readnews.app.base.BaseActivity
import com.readnews.app.beans.ArticleBean
import com.readnews.app.beans.ArticleList
import com.readnews.app.beans.SourcesBean
import com.readnews.app.databinding.ActivityArticlesBinding
import com.readnews.app.io.retrofit.APICallback
import com.readnews.app.io.retrofit.APIHandler
import com.readnews.app.io.retrofit.APIs
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable

class Articles : BaseActivity(), APICallback {
    private lateinit var binding: ActivityArticlesBinding
    private var call: Call<ArticleList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_articles)

        binding.emptyLayout.emptyText.text = getString(R.string.emptyArticles)

        binding.articleList.layoutManager = LinearLayoutManager(this)
        binding.articleList.setHasFixedSize(true)
        binding.articleList.adapter = ArticleListAdapter(list = ArrayList())
        binding.articleList.isNestedScrollingEnabled = false
        binding.articleList.itemAnimator = DefaultItemAnimator()

        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val serializable: Serializable? = intent?.extras?.getSerializable(getString(R.string.source_intent_extra))
        if (serializable is SourcesBean) {

            binding.collapsingToolbar.title = serializable.name
            binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
            binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))

            getArticles(serializable.id)
        } else {
            throw RuntimeException("required source serializable object")
        }
    }

    /**
     * get articles
     */
    private fun getArticles(source: String?) {
        binding.progress.visibility = View.VISIBLE
        call = APIHandler.getInstance().getArticles(source, BuildConfig.newsApiKey, this)
    }

    override fun onSuccess(call: Call<*>?, response: Response<*>?, tag: String) {
        binding.progress.visibility = View.GONE
        when (tag) {
            APIs.TAG_ARTICLES -> {
                if (response != null) {
                    if (response.isSuccessful) {
                        val articleList: ArticleList = response.body() as ArticleList
                        if (articleList.status.equals("ok")) {
                            binding.emptyLayout.root.visibility = View.GONE
                            binding.articleList.visibility = View.VISIBLE

                            updateAdapter(articleList.articles)
                        } else {
                            binding.emptyLayout.root.visibility = View.VISIBLE
                            binding.articleList.visibility = View.GONE
                        }
                    } else {
                        binding.emptyLayout.emptyText.text = response.errorBody()?.string()
                        binding.emptyLayout.root.visibility = View.VISIBLE
                        binding.articleList.visibility = View.GONE
                    }
                } else {
                    showToast(getString(R.string.networkError))
                }
            }
        }
    }

    override fun onFailure(call: Call<*>?, t: Throwable?, tag: String) {
        binding.progress.visibility = View.GONE
        showToast(getString(R.string.networkError))
    }

    /**
     * set the adapter on Recycler View
     */
    private fun updateAdapter(sources: ArrayList<ArticleBean>?) {
        binding.articleList.adapter = ArticleListAdapter(list = sources)
    }

    override fun onDestroy() {
        call?.cancel()
        super.onDestroy()
    }
}
