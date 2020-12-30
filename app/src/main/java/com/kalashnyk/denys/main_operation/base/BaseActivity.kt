package com.kalashnyk.denys.main_operation.base

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.kalashnyk.denys.utils.*


/**
 * @author Kalashnyk Denys e-mail: kalashnyk.denys@gmail.com
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    /**
     *
     */
    protected lateinit var viewBinding: V

    /**
     *
     */
    protected lateinit var permissionManager: IPermissionManager

    /**
     *
     */
    open lateinit var messageNecessaryPermissions: String

    private var toolbar: Toolbar?=null

    companion object {
        private const val DEBUG_ENABLED=false
        var currentActivity: Class<*>? = null
    }

    /**
     * @return
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * @param binder
     */
    abstract fun setupViewLogic(binder: V)

    override fun onStart() {
        super.onStart()
        currentActivity = this::class.java
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this, getLayoutId())
        permissionManager=PermissionManagerImpl()
        setupViewLogic(viewBinding)
    }

    override fun onDestroy() {
        if (currentActivity == javaClass) {
            currentActivity = null
        }
        super.onDestroy()
    }

    /**
     *
     */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    /**
     *
     */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }

    /**
     * handle back stack with handling state of children of activity
     */
    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

    /**
     * Convenience method for adding a fragment or replacing an existing one for a specific tag
     * @param fragment Fragment
     * @param id Int
     * @param tag String
     */
    protected fun addOrReplaceFragment(fragment: Fragment, id: Int, tag: String) {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            fragmentTransaction.add(id, fragment, tag)
        } else {
            fragmentTransaction.replace(id, fragment, tag)
        }
        fragmentTransaction.commit()
    }

    protected fun setWindowFlag(bits: Int, on: Boolean) {
        val win=window
        val winParams=win.attributes
        if (on) {
            winParams.flags=winParams.flags or bits
        } else {
            winParams.flags=winParams.flags and bits.inv()
        }
        win.attributes=winParams
    }

    fun back(): Boolean {
        onBackPressed()
        return true
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun initializeToolbar(toolbar: Toolbar) {
        this.toolbar=toolbar.initializeToolbar(actionBar, this)
    }

    /**
     *
     */
    protected fun getToolbar(): Toolbar?=this.toolbar

    /**
     * check permissions and handel them state
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected fun isPermissionGranted(arrayPermission: Array<String>, requestCode: Int=0): Boolean=
        if (this.permissionManager.hasPermission(this, arrayPermission)) {
            true
        } else {
            this.permissionManager.requestPermission(this, requestCode, arrayPermission)
            false
        }

    /**
     *
     */
    open fun isDebugEnabled(): Boolean=DEBUG_ENABLED
}
