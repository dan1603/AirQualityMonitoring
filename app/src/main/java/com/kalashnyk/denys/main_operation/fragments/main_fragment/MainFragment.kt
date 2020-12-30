package com.kalashnyk.denys.main_operation.fragments.main_fragment

import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.databinding.MainDataBinding
import com.kalashnyk.denys.main_operation.base.BaseFragment
import com.kalashnyk.denys.main_operation.base.Dialog

class MainFragment : BaseFragment<MainDataBinding>() {

    override fun getLayoutId() = R.layout.fragment_main

    override fun setupViewLogic(binder: MainDataBinding) {

    }

    private fun askForGpsDialog() {
        activeDialog = Dialog.DialogBuilder.getDialog(getString(R.string.gps_in_settings),
            text = getString(R.string.gps_is_disabled_do_you_want_to_go_to_settings_menu),
            buttons = listOf(
                Dialog.DialogButton(getString(R.string.cancel), View.OnClickListener {
                    dismissDialog()
                }),
                Dialog.DialogButton(getString(R.string.turn_on), View.OnClickListener {
                    showToast("turning on")
                    dismissDialog()
                })))
        activeDialog?.show(childFragmentManager, TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        button_1.setOnClickListener {
//            val action = MainFragmentDirections.actionListFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        button_2.setOnClickListener {
//            val action = MainFragmentDirections.actionListFilesFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        button_3.setOnClickListener {
//            val action = MainFragmentDirections.dialogsAction()
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }

}