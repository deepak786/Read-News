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

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import com.google.gson.Gson
import com.readnews.app.R
import com.readnews.app.base.BaseActivity
import com.readnews.app.beans.ArticleBean
import io.branch.referral.Branch

class SplashScreen : BaseActivity() {
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    /**
     * open sources screen
     */
    fun openSources() {
        val sourcesIntent: Intent = Intent(this, Sources::class.java)
        sourcesIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(sourcesIntent)
        finish()
    }

    /**
     * start timer
     */
    fun startTimer() {
        stopTimer()
        timer = object : CountDownTimer(1000, 500) {
            override fun onFinish() {
                val branch = Branch.getInstance()
                branch.initSession(Branch.BranchReferralInitListener { referringParams, error ->
                    if (error == null) {
                        // params are the deep linked params associated with the link that the user clicked before showing up
                        // params will be empty if no data found
                        showOutput(referringParams.toString())
                        var article = referringParams.optString("article", "")
                        article = article.replace("\\\"", "\"")
                        println(">>>>" + article)
                        if (!TextUtils.isEmpty(article)) {
                            val data = Gson().fromJson(article, ArticleBean::class.java)

                            val articleIntent: Intent = Intent(this@SplashScreen, Article::class.java)
                            articleIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            articleIntent.putExtra(getString(R.string.article_intent_extra), data)
                            startActivity(articleIntent)
                            finish()

                            return@BranchReferralInitListener
                        }
                    }
                    openSources()
                }, intent.data, this@SplashScreen)
            }

            override fun onTick(p0: Long) {
            }
        }.start()
    }

    /**
     * stop timer
     */
    fun stopTimer() {
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    override fun onBackPressed() {
        stopTimer()
        finish()
    }
}