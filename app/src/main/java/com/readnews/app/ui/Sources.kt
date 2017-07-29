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
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.readnews.app.R
import com.readnews.app.adapters.SourceListAdapter
import com.readnews.app.base.BaseActivity
import com.readnews.app.beans.SourceList
import com.readnews.app.beans.SourcesBean
import com.readnews.app.databinding.ActivitySourcesBinding
import com.readnews.app.io.retrofit.APICallback
import com.readnews.app.io.retrofit.APIHandler
import com.readnews.app.io.retrofit.APIs
import retrofit2.Call
import retrofit2.Response

class Sources : BaseActivity(), APICallback {
    private lateinit var binding: ActivitySourcesBinding
    private var call: Call<SourceList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sources)

        binding.newsApiAttribution.text = Html.fromHtml(getString(R.string.attributionLink))
        binding.newsApiAttribution.movementMethod = LinkMovementMethod.getInstance()

        binding.toolbarLayout.toolbar.title = getString(R.string.sources)
        binding.emptyLayout.emptyText.text = getString(R.string.emptySources)

        binding.sourceList.layoutManager = LinearLayoutManager(this)
        binding.sourceList.setHasFixedSize(true)
        binding.sourceList.adapter = SourceListAdapter(list = ArrayList())

        getSources()
    }

    /**
     * get Sources
     */
    private fun getSources() {
        binding.progress.visibility = View.VISIBLE
        call = APIHandler.getInstance().getSources(this)
    }

    override fun onSuccess(call: Call<*>?, response: Response<*>?, tag: String) {
        binding.progress.visibility = View.GONE
        when (tag) {
            APIs.TAG_SOURCES -> {
                if (response != null) {
                    if (response.isSuccessful) {
                        val sourcesList: SourceList = response.body() as SourceList
                        if (sourcesList.status.equals("ok")) {
                            binding.emptyLayout.root.visibility = View.GONE
                            binding.sourceList.visibility = View.VISIBLE

                            updateAdapter(sourcesList.sources)
                        } else {
                            binding.emptyLayout.root.visibility = View.VISIBLE
                            binding.sourceList.visibility = View.GONE
                        }
                    } else {
                        binding.emptyLayout.emptyText.text = response.errorBody()?.string()
                        binding.emptyLayout.root.visibility = View.VISIBLE
                        binding.sourceList.visibility = View.GONE
                    }
                } else {
                    showToast(getString(R.string.networkError))
                }
            }
        }
    }

    /**
     * set the adapter on Recycler View
     */
    private fun updateAdapter(sources: ArrayList<SourcesBean>?) {
        binding.sourceList.adapter = SourceListAdapter(list = sources)
    }

    override fun onFailure(call: Call<*>?, t: Throwable?, tag: String) {
        binding.progress.visibility = View.GONE
        showToast(getString(R.string.networkError))
    }

    override fun onDestroy() {
        call?.cancel()
        super.onDestroy()
    }
}