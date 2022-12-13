package com.zero.pengusirburung.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.oratakashi.viewbinding.core.tools.startActivity
import com.zero.pengusirburung.ui.main.MainActivity
import com.zero.pengusirburung.R
import com.zero.pengusirburung.databinding.ActivitySplashBinding
import com.zero.zerobase.presentation.viewbinding.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val appSplash: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.app_splash)
    }

    override fun initUI() {
        super.initUI()
        binding.ivLogo.startAnimation(appSplash)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(MainActivity::class.java)
                finish()
            }, 2000L
        )
    }
}