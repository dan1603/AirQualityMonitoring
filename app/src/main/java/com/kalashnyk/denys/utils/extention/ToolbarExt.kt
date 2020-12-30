package com.kalashnyk.denys.utils

import android.app.ActionBar
import android.app.Activity
import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi

/**
 * @param actionBar
 * @param activity
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.initializeToolbar(actionBar : ActionBar?, activity : Activity) : Toolbar {
    this.apply {
        setNavigationOnClickListener { activity.onBackPressed() }
        activity.setActionBar(this)
        actionBar?.title = ""
    }
    return this
}

/**
 * @param title
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.setToolbarTitle(title: CharSequence)  {
    this.apply {
        this.title = title
    }
}