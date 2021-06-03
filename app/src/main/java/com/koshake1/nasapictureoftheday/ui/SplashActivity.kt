package com.koshake1.nasapictureoftheday.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.utils.LOADING_TIME_MS
import com.koshake1.nasapictureoftheday.utils.START_ANIMATION_ROTATION
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splash_image.animate().rotationBy(START_ANIMATION_ROTATION)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(
                LOADING_TIME_MS
            ).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            })

    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}