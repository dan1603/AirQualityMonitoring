package com.kalashnyk.denys.main_operation.fragments.list_history_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ListDataBinding
import com.kalashnyk.denys.main_operation.base.BaseFragment
import com.kalashnyk.denys.main_operation.base.Dialog
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListDataBinding>() {

    private val viewModel: ListViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_list

    override fun setupViewLogic(binder: ListDataBinding) {

    }

    private fun deleteAllDialog() {
        activeDialog = Dialog.DialogBuilder.getDialog(getString(R.string.attention),
            text = getString(R.string.do_you_really_want_to_delete_all_the_items_this_operation_can_not_be_undone),
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

    private fun deleteDialog() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delete_all_layout.setOnClickListener {
            deleteAllDialog()
        }
    }

    companion object {
        private val TAG = ListFragment::class.java.simpleName
    }

}