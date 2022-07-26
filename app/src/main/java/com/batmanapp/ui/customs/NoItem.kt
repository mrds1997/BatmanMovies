package com.batmanapp.ui.customs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.batmanapp.R

class NoItem {
    private var view: View? = null
    private var txt: TextView? = null
    private var img: ImageView? = null
    private var btn: Button? = null
    private var tvDescription: TextView? = null

    fun setTitle(title: String?): NoItem {
        txt?.visibility = View.VISIBLE
        txt?.text = title
        return this
    }

    fun setDescription(description: String?): NoItem {
        tvDescription?.visibility = View.VISIBLE
        tvDescription?.text = description
        return this
    }

    fun setButton(title: String?, listener: View.OnClickListener?): NoItem {
        btn?.visibility = View.VISIBLE
        btn?.text = title
        btn?.setOnClickListener(listener)
        return this
    }




    fun setImage(id: Int): NoItem {
        img?.visibility = View.VISIBLE
        img?.setImageResource(id)
        return this
    }

    fun show() {
        view?.visibility = View.VISIBLE
    }

    fun dismiss() {
        view?.visibility = View.GONE
    }

    companion object {
        fun newInstance(layout: ViewGroup?): NoItem {
            val noMore = NoItem()
            noMore.view = LayoutInflater.from(layout?.context).inflate(R.layout.no_item, layout, false)
            when (layout) {
                is LinearLayout -> {
                    noMore.view?.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
                is CoordinatorLayout -> {
                    noMore.view?.layoutParams = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
                is FrameLayout -> {
                    noMore.view?.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            }
            noMore.btn = noMore.view?.findViewById(R.id.btn)
            noMore.txt = noMore.view?.findViewById(R.id.txt)
            noMore.img = noMore.view?.findViewById(R.id.img)
            noMore.img = noMore.view?.findViewById(R.id.img)
            noMore.tvDescription = noMore.view?.findViewById(R.id.tvDesc)
            layout?.addView(noMore.view)
            return noMore
        }
    }
}