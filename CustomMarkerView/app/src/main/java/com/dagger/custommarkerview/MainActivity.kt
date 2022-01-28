package com.dagger.custommarkerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by inject()

    private lateinit var transAction        : FragmentTransaction
    private val fragmentManager             : FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragment = supportFragmentManager.findFragmentById(R.id.mainFrameLayout) as MainFragment?
            ?: MainFragment()
        transAction = fragmentManager.beginTransaction()
        transAction.replace(R.id.mainFrameLayout, mFragment).commitNow()

    }
}