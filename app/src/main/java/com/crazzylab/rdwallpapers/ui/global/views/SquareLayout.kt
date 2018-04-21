package com.crazzylab.rdwallpapers.ui.global.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by Михаил on 10.08.2017.
 */
class SquareLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}