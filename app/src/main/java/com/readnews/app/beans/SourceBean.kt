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

package com.readnews.app.beans

import java.io.Serializable

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
data class SourcesBean(
        /**
         * id : abc-news-au
         * name : ABC News (AU)
         * description : Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.
         * url : http://www.abc.net.au/news
         */

        var id: String? = null,
        var name: String? = null,
        var description: String? = null,
        var url: String? = null,
        var category: String? = null
) : Serializable