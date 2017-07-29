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

import retrofit2.Call
import retrofit2.Response

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
interface APICallback {
    fun onSuccess(call: Call<*>?, response: Response<*>?, tag: String)

    fun onFailure(call: Call<*>?, t: Throwable?, tag: String)
}