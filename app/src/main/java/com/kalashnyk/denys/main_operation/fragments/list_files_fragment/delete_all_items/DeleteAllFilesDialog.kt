package com.kalashnyk.denys.main_operation.fragments.list_files_fragment.delete_all_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_delete_all_files_dialog.*
import kotlinx.android.synthetic.main.fragment_main.*


class DeleteAllFilesDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_all_files_dialog, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        btn_dialog_cancel_all_files.setOnClickListener {
//            val action = DeleteAllFilesDialogDirections.toMainFragmentAction()
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        btn_dialog_delete_all_files.setOnClickListener {
//            val action = DeleteAllFilesDialogDirections.allItemsAction()
//            Navigation.findNavController(it).navigate(action)
//        }
//
//
//    }

}