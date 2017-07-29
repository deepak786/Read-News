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

package com.readnews.app

import android.app.Application
import io.branch.referral.Branch

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 28/7/17
 */
class ReadNews : Application() {
    override fun onCreate() {
        super.onCreate()
        Branch.getAutoInstance(this)
    }
}