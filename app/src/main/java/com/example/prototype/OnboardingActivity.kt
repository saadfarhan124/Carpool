package com.example.prototype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.prototype.adapters.OnboardingSectionPageAdapter
import com.google.android.material.tabs.TabLayout

class OnboardingActivity: AppCompatActivity() {

    private var screenPager: ViewPager? = null
    lateinit var onboardingSectionPageAdapter: OnboardingSectionPageAdapter
    lateinit var tabIndicator: TabLayout
    lateinit var btnSkip: Button
    lateinit var btnGetStarted: Button
    lateinit var btnAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //make screen to be fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        setContentView(R.layout.activity_onboarding)

        //Views
        btnSkip = findViewById(R.id.btn_skip)
        btnGetStarted = findViewById(R.id.btn_get_started)

        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)

        //fill data description
        val mList = arrayListOf<OnboardingScreenItemActivity>()
        mList.add(
            OnboardingScreenItemActivity(
                "BINGUNG CARI\nMENTOR BELAJAR?",
                "Apa ya enaknya? saya juga bingung mau\nrekomendasi apa. Tapi tunggu, kan ada\nsosial media...",
                R.drawable.img1
            )
        )
        mList.add(
            OnboardingScreenItemActivity(
                "TAPI MENTORNYA\nJAUH & SIBUK...",
                "Wah, ribet juga ya kalo gitu. Kira-kira\nenaknya gimana ya? hmm...",
                R.drawable.img2
            )
        )
        mList.add(
            OnboardingScreenItemActivity(
                "AKHIRNYA KETEMU\nJUGA DEH...",
                "Kok cepet? owh iya kan pake aplikasi\n\"Meet The Mentor\" katanya ya...?\ncoba dulu deh….",
                R.drawable.img3
            )
        )

        //Setup ViewPager
        screenPager = findViewById(R.id.screen_viewpager)
        onboardingSectionPageAdapter = OnboardingSectionPageAdapter(this,mList)
        screenPager!!.adapter = onboardingSectionPageAdapter

        //setup tab layout with viewpager
        tabIndicator.setupWithViewPager(screenPager)


        //skip button
        btnSkip.setOnClickListener { screenPager!!.currentItem = mList.size }



        tabIndicator.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override  fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        //get started button

        btnGetStarted.setOnClickListener {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            savePrefsData()
            finish()
        }
    }

    private fun restorePrefData(): Boolean {
        val preferences = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return preferences.getBoolean("isIntroOpened", false)
    }

    private fun savePrefsData() {
        val preferences = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isIntroOpened", true)
        editor.apply()
    }

    private fun loadLastScreen() {
        btnSkip.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        tabIndicator.visibility = View.INVISIBLE
        btnGetStarted.animation = btnAnim
    }
}