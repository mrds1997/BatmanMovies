package com.batmanapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.batmanapp.BuildConfig
import com.batmanapp.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        Handler().postDelayed({
            showMainActivity()
        }, 3000)
    }

    private fun showMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}