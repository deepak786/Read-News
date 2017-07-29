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

package com.readnews.app.utils

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.readnews.app.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
@BindingAdapter("bind:font")
fun setFont(textView: TextView, fontName: String) {
    textView.typeface = Typeface.createFromAsset(textView.context.assets, "fonts/" + fontName)
}

@BindingAdapter("bind:setImageURL")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.default_pic))
    if (!TextUtils.isEmpty(imageUrl)) {
        val metrics = imageView.context.resources.displayMetrics
        val width = metrics.widthPixels

        Picasso.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .resize(width, (imageView.context.resources.getDimension(R.dimen.image_size).toInt()))
                .into(imageView)
    }
}

@BindingAdapter(value = *arrayOf("author", "publishedDate"), requireAll = false)
fun setAuthor(textView: TextView, author: String?, publishedDate: String?) {
    if (TextUtils.isEmpty(author) && TextUtils.isEmpty(publishedDate)) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        if (TextUtils.isEmpty(author)) {
            textView.text = getTime(textView.context, publishedDate)
        } else if (TextUtils.isEmpty(publishedDate)) {
            textView.text = textView.context.getString(R.string.by).plus(" ").plus(author)
        } else {
            textView.text = textView.context.getString(R.string.by).plus(" ").plus(author).plus(" ").plus(getTime(textView.context, publishedDate))
        }
    }
}

/**
 * convert the published date to local date
 */
private fun getTime(context: Context, publishedDate: String?): String {
    val dateFormats: ArrayList<String> = arrayListOf("yyyy-MM-dd'T'hh:mm:ss'Z'", "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
    for (dateFormat in dateFormats) {
        try {
            val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")

            val requiredFormat: SimpleDateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            return context.getString(R.string.at).plus(" ").plus(requiredFormat.format(simpleDateFormat.parse(publishedDate)))
        } catch (e: Exception) {

        }
    }
    return ""
}