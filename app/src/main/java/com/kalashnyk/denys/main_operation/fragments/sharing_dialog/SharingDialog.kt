package com.kalashnyk.denys.main_operation.fragments.sharing_dialog

import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.kalashnyk.denys.main_operation.base.Dialog
import kotlinx.android.synthetic.main.layout_social.*

class SharingDialog : Dialog() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sharing_facebook.setOnClickListener {

        }
        btn_sharing_instagram.setOnClickListener {

        }
        btn_sharing_twitter.setOnClickListener {

        }
    }

    companion object {
        fun getInstance() = DialogBuilder.getDialog(
            "Choose social for sharing",
            customLayout = R.layout.layout_social
        )
    }

}