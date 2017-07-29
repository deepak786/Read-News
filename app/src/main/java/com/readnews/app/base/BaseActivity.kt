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

package com.readnews.app.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * show the output on console
     */
    fun showOutput(message: String) {
        println(">>>>" + message)
    }

    /**
     * display a toast message
     */
    fun showToast(message: String?) {
        Toast.makeText(this, message ?: "", Toast.LENGTH_SHORT).show()
    }
}