package com.example.prototype.Utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.prototype.LoginActivity
import com.example.prototype.companion.Companion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Util{
    companion object{

        fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
            val transaction = manager.beginTransaction()
            transaction.add(fragment, null)
            transaction.commit()

        }

        fun logout(context: Context) : Intent{
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }

        fun getGlobals(): com.example.prototype.companion.Companion.Globals{
            var globals = com.example.prototype.companion.Companion.Globals
            return globals
        }

        fun getFirebaseFireStore():FirebaseFirestore{
            return FirebaseFirestore.getInstance()
        }

        fun getImageRequest():Int{
            return 71
        }

        fun getStorageRef():StorageReference{
            return FirebaseStorage.getInstance()
                .reference.child("images/${getGlobals().user!!.uid}")
        }
    }
}