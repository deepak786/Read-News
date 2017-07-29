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

import android.content.Intent
import android.view.View
import com.readnews.app.R
import com.readnews.app.base.BaseRecyclerAdapter
import com.readnews.app.beans.SourcesBean
import com.readnews.app.databinding.ListItemSourceBinding
import com.readnews.app.ui.Articles

/**
 * Created by Deepak
 * Project: ReadNews
 * Created At: 27/7/17
 */
class SourceListAdapter(private var list: ArrayList<SourcesBean>?) : BaseRecyclerAdapter() {

    override fun getObjForPosition(position: Int): Any {
        return list!![position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.list_item_source
    }

    override fun setClickListeners(holder: Holder) {
        if (holder.getBinding() is ListItemSourceBinding) {
            holder.getBinding().root.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    val intent: Intent = Intent(p0?.context, Articles::class.java)
                    intent.putExtra(p0?.context?.getString(R.string.source_intent_extra), list?.get(holder.adapterPosition))
                    p0?.context?.startActivity(intent)
                }

            })
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}