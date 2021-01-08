package com.kalashnyk.denys.main_operation.base

import android.app.ActionBar
import android.os.Bundle
import androidx.annotation.DrawableRes

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.kalashnyk.denys.utils.*

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var viewBinder: V

    private val appBar: ActionBar? = activity?.actionBar

    abstract fun getLayoutId(): Int

    abstract fun setupViewLogic(binder : V)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragmentManager?.popBackStackImmediate()
        }
        return super.onOptionsItemSelected(item)
    }

    protected var activeDialog: Dialog? = null

    protected fun dismissDialog() {
        activeDialog?.dismiss()
        activeDialog = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        setupViewLogic(this.viewBinder)
        return viewBinder.root
    }

    open fun reset() {}

    protected fun disableHomeAsUp() = appBar?.setDisplayHomeAsUpEnabled(false)

    protected fun initializeNavigationBar(title: String, showBackButton: Boolean, @DrawableRes resId: Int) {
        appBar?.apply {
            this.setDisplayHomeAsUpEnabled(showBackButton)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                this.setHomeAsUpIndicator(resId)
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                this.elevation = 4f
            }
        }
    }

    protected fun getBaseActivity() : BaseActivity<*> = activity as BaseActivity<*>

    /**
     *
     */
    protected fun showToast(text: String) = activity?.showToast(text)
    /**
     *
     */
    protected fun showSnack(text: String) = activity?.showSnack(text)
    /**
     *
     */
    protected fun hideKeyboard() = activity?.hideKeyboard()
}