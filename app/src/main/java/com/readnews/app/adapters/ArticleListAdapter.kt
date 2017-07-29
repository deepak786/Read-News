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

package com.readnews.app.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.gson.Gson
import com.readnews.app.R
import com.readnews.app.base.BaseRecyclerAdapter
import com.readnews.app.beans.ArticleBean
import com.readnews.app.databinding.ListItemArticleBinding
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.LinkProperties

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
class ArticleListAdapter(private var list: ArrayList<ArticleBean>?) : BaseRecyclerAdapter() {
    override fun getObjForPosition(position: Int): Any {
        return list!![position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.list_item_article
    }

    override fun setClickListeners(holder: Holder) {
        if (holder.getBinding() is ListItemArticleBinding) {
            (holder.getBinding() as ListItemArticleBinding).share.setOnClickListener { p0 ->
                generateLink(p0.context, list?.get(holder.adapterPosition) as ArticleBean)
            }

            (holder.getBinding() as ListItemArticleBinding).readMore.setOnClickListener { p0 ->
                val webIntent: Intent = Intent(Intent.ACTION_VIEW)
                webIntent.data = Uri.parse((list?.get(holder.adapterPosition) as ArticleBean).url)
                p0?.context?.startActivity(webIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    /**
     * generate branch link
     */
    private fun generateLink(context: Context, articleBean: ArticleBean) {
        val branchUniversalObject = BranchUniversalObject()
                .setCanonicalIdentifier("item/12345")
                .setTitle(articleBean.title ?: "")
                .setContentDescription(articleBean.description ?: "")
                .setContentImageUrl(articleBean.urlToImage ?: "")
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)

        val linkProperties = LinkProperties()
        linkProperties.addControlParameter("article", Gson().toJson(articleBean))

        branchUniversalObject.generateShortUrl(context, linkProperties) { url, error ->
            if (error == null) {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_SUBJECT, articleBean.title)
                share.putExtra(Intent.EXTRA_TEXT, url)
                if (share.resolveActivity(context.packageManager) != null) {
                    context.startActivity(Intent.createChooser(share, context.getString(R.string.share)))
                }
            } else {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}