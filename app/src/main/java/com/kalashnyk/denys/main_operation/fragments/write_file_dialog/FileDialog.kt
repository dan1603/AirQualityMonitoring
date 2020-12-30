package com.kalashnyk.denys.main_operation.fragments.write_file_dialog

import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.kalashnyk.denys.main_operation.base.Dialog
import kotlinx.android.synthetic.main.layout_file.*

class FileDialog : Dialog() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_write_xml.setOnClickListener {

        }
        btn_write_csv.setOnClickListener {

        }
        btn_write_kml.setOnClickListener {

        }
    }

    companion object {
        fun getInstance() = DialogBuilder.getDialog(
            "Choose type file",
            customLayout = R.layout.layout_file
        )
    }

}