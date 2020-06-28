package com.chirag.covid19india.util

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * Created by Chirag Desai
 */
object AppAnimationUtils {
    fun fadeInAnimation(view: View, animationDuration: Long, startOffset: Int) {
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = animationDuration
        fadeIn.startOffset = startOffset.toLong()
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        view.startAnimation(fadeIn)
    }
}