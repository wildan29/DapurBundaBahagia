package com.example.dapurbundabahagia.app.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.example.dapurbundabahagia.app.di.MainViewModel
import com.example.dapurbundabahagia.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModelSplashScreen by viewModels<MainViewModel>()
    private lateinit var navControllerLogin: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModelSplashScreen.isReady.value
            }

            // Add Animation On Exit
            setOnExitAnimationListener { screen ->
                val zoomx = ObjectAnimator.ofFloat(screen.view, View.SCALE_X, 0.4f, 0.0f)
                zoomx.interpolator = OvershootInterpolator()
                zoomx.duration = 500L
                zoomx.doOnEnd { screen.remove() }

                val zoomy = ObjectAnimator.ofFloat(screen.view, View.SCALE_Y, 0.4f, 0.0f)
                zoomy.interpolator = OvershootInterpolator()
                zoomy.duration = 500L
                zoomy.doOnEnd { screen.remove() }

                // start animation
                zoomx.start()
                zoomy.start()
            }
        }


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navControllerLogin.navigateUp() || super.onSupportNavigateUp()
    }
}