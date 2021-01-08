package com.kalashnyk.denys.main_operation.fragments.list_files_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ListFilesDataBinding
import com.kalashnyk.denys.main_operation.base.BaseFragment
import com.kalashnyk.denys.main_operation.base.Dialog
import kotlinx.android.synthetic.main.fragment_list_files.*


class ListFilesFragment : BaseFragment<ListFilesDataBinding>() {

    private val viewModel: ListFilesViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_list_files

    override fun setupViewLogic(binder: ListFilesDataBinding) {

    }

    private fun deleteSingleFileDialog() {
        activeDialog = Dialog.DialogBuilder.getDialog(getString(R.string.attention),
            text = getString(R.string.do_you_really_want_to_delete_nthis_operation_can_not_be_undone),
            buttons = listOf(
                Dialog.DialogButton(getString(R.string.cancel), View.OnClickListener {
                    dismissDialog()
                }),
                Dialog.DialogButton(getString(R.string.delete), View.OnClickListener {
                    showToast("deleting")
                    dismissDialog()
                })))
        activeDialog?.show(childFragmentManager, TAG)
    }

    private fun shareOrOpenFileDialog() {
        activeDialog = Dialog.DialogBuilder.getDialog(getString(R.string.choose_option),
            text = getString(R.string.do_you_want_share_file_or_nopen_file_and_watch_content),
            buttons = listOf(
                Dialog.DialogButton(getString(R.string.cancel), View.OnClickListener {
                    dismissDialog()
                }),
                Dialog.DialogButton(getString(R.string.share), View.OnClickListener {
                    showToast("sharing")
                    dismissDialog()
                }),
                Dialog.DialogButton(getString(R.string.open), View.OnClickListener {
                    showToast("open")
                    dismissDialog()
                })))
        activeDialog?.show(childFragmentManager, TAG)
    }

    private fun deleteAllFilesDialog() {
        activeDialog = Dialog.DialogBuilder.getDialog(getString(R.string.attention),
            text = getString(R.string.do_you_really_want_to_delete_all_files_this_operation_can_not_be_undone),
            buttons = listOf(
                Dialog.DialogButton(getString(R.string.cancel), View.OnClickListener {
                    dismissDialog()
                }),
                Dialog.DialogButton(getString(R.string.delete), View.OnClickListener {
                    showToast("deleting")
                    dismissDialog()
                })))
        activeDialog?.show(childFragmentManager, TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delete_all_files_layout.setOnClickListener {
            deleteAllFilesDialog()
        }
    }

    companion object {
        private val TAG = ListFilesFragment::class.java.simpleName
    }
}