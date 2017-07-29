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
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.readnews.app.R
import com.readnews.app.adapters.ArticleListAdapter
import com.readnews.app.beans.ArticleBean
import com.readnews.app.databinding.ActivityArticleBinding
import java.io.Serializable

class Article : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityArticleBinding = DataBindingUtil.setContentView(this, R.layout.activity_article)

        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.collapsingToolbar.title = getString(R.string.app_name)
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))

        val serializable: Serializable? = intent?.extras?.get(getString(R.string.article_intent_extra)) as Serializable?
        if (serializable is ArticleBean) {
            val list: ArrayList<ArticleBean> = ArrayList()
            list.add(serializable)

            binding.articleList.layoutManager = LinearLayoutManager(this)
            binding.articleList.setHasFixedSize(true)
            binding.articleList.adapter = ArticleListAdapter(list = list)
            binding.articleList.isNestedScrollingEnabled = false
            binding.articleList.itemAnimator = DefaultItemAnimator()
        }
    }

    /**
     * go to parent activity
     */
    private fun goUp() {
        val upIntent = NavUtils.getParentActivityIntent(this)
        if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities()
        } else {
            NavUtils.navigateUpTo(this, upIntent)
        }
    }

    override fun onBackPressed() {
        goUp()
    }
}
