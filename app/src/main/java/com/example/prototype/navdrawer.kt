package com.example.prototype

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_navdrawer.*
import kotlinx.android.synthetic.main.nav_header_navdrawer.view.*



class navdrawer : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navdrawer)

        Util.downloadDisplayPicture()

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        var globals = Companion.Globals
        val headerView: View = nav_view.getHeaderView(0)
        headerView.txtUsername.text = globals.user!!.displayName
        headerView.txtEmail.text = globals.user!!.email


        if(Util.getGlobals().userImage != null){
            headerView.usr_pic.setImageBitmap(Util.getGlobals().userImage)
        }

        headerView.usr_pic.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        demo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.navdrawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

    private fun demo(){

        var location = Location("")
        location.latitude =24.9160175
        location.longitude = 67.0308146

        var locationtwo = Location("")
        locationtwo.latitude =24.8202381
        locationtwo.longitude = 67.0287875

        Log.d("HomeScene",location.distanceTo(locationtwo).toString())

    }

}
