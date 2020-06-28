package com.chirag.covid19india.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chirag.covid19india.R
import com.chirag.covid19india.databinding.ActivitySplashBinding
import com.chirag.covid19india.util.AppAnimationUtils

class SplashActivity : AppCompatActivity() {
    lateinit var mBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        val rotateAnimation = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 300
        rotateAnimation.repeatCount = Animation.INFINITE

        //mBinding.ivAppIcon.setAnimation(rotateAnimation);
        AppAnimationUtils.fadeInAnimation(mBinding.ivAppIcon, 1200, 1000)
        runSplash()
    }

    private fun runSplash() {
        Handler().postDelayed({ animateImage() }, 3000)
    }

    private fun animateImage() {
        startActivity()
    }

    private fun startActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {}
}