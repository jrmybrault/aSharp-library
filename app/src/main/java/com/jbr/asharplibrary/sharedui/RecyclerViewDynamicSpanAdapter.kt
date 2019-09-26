package com.jbr.asharplibrary.sharedui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

object RecyclerViewDynamicSpanAdapter {

    @JvmOverloads
    fun adapt(recyclerView: RecyclerView, itemWidth: Int, minimumSpanCount: Int = 1) {
        recyclerView.post {
            val layoutManager = recyclerView.layoutManager as GridLayoutManager

            val recyclerViewWidth = recyclerView.width
            val fittingSpanCount = recyclerViewWidth / itemWidth
            val spanCount = max(fittingSpanCount, minimumSpanCount)
            layoutManager.spanCount = spanCount

            layoutManager.requestLayout()
        }
    }
}