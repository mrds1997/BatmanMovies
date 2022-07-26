package com.example.filmnettest.ui.customs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.batmanapp.R


class Loading {

    private var view: View? = null
    private var view2: View? = null
    private var layout: ViewGroup? = null

    fun start() {
        if (view == null) {
            view = LayoutInflater.from(layout!!.context).inflate(R.layout.loading, layout, false)
//            (view?.findViewById<View>(R.id.prgSplash) as ProgressBar).indeterminateDrawable.setColorFilter(layout!!.context.resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)

            if(layout is LinearLayout){
                view?.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }else if(layout is CoordinatorLayout){
                view?.layoutParams = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            layout?.addView(view)
        } else {
            view?.visibility = View.VISIBLE
        }
    }

    fun startLazy() {
        if (view2 == null) {
                view2 = LayoutInflater.from(layout!!.context).inflate(R.layout.loading2, layout, false)

            if(layout is FrameLayout){
                view2?.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }else if(layout is CoordinatorLayout){
                view2?.layoutParams = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            layout?.addView(view2)
        } else {
            view2?.visibility = View.VISIBLE
        }
    }

    fun dismiss() {
        if (view != null) {
            view?.visibility = View.GONE
        }
        if (view2 != null) {
            view2?.visibility = View.GONE
        }
    }

    fun isShowing():Boolean {
       return view?.visibility == View.VISIBLE
    }

    companion object {
        fun newInstance(layout: ViewGroup?): Loading {
            val noMore = Loading()
            noMore.layout = layout
            noMore.start()
            return noMore
        }

        fun newInstance(layout: ViewGroup?, lazy: Boolean): Loading {
            val noMore = Loading()
            noMore.layout = layout
            noMore.startLazy()
            return noMore
        }
    }
}