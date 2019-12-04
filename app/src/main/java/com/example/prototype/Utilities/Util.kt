package com.example.prototype.Utilities

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.prototype.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class Util{
    companion object{
        fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {

            val transaction = manager.beginTransaction()
            transaction.add(frameId, fragment)
            transaction.commit()

        }

        fun logout(context: Context) : Intent{
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}