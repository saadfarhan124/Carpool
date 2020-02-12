package com.example.prototype.ui.wallet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.example.prototype.Utilities.Util

class WalletFragment : Fragment() {

    private lateinit var walletViewModel: WalletViewModel
    private lateinit var textAmount: TextView
    private lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        walletViewModel =
            ViewModelProviders.of(this).get(WalletViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_wallet, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        init()
        return root
    }

    private fun init(){
        textAmount = root.findViewById(R.id.textAmount)
        textAmount.text = (Util.getGlobals().userDataModel!!.amount.toString())

    }

}