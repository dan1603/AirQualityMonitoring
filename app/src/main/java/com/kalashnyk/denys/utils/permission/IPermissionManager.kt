package com.kalashnyk.denys.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList


/**
 * @author Kalashnyk Denys e-mail: kalashnyk.denys@gmail.com
 */
interface IPermissionManager {

    /**
     * @param activity
     * @param permissions
     */
    fun <A : Activity> hasPermission(activity: A, permissions: Array<String>): Boolean

    /**
     * @param activity
     * @param requestCode
     * @param permissions
     */
    fun <A : Activity> requestPermission(activity: A, requestCode: Int, permissions: Array<String>)

    /**
     * @param activity
     * @param permissions
     */
    fun <A : Activity> checkPermissionRationale(activity: A, permissions: Array<String>): Boolean
}

/**
 *
 */
class PermissionManagerImpl : IPermissionManager {

    override fun <A : Activity> hasPermission(
        activity: A,
        permissions: Array<String>
    ): Boolean {
        val list = ArrayList<Boolean>()
        permissions.forEach {
            list.add(ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED)
        }
        return list.all { it }
    }

    override fun <A : Activity> checkPermissionRationale(
        activity: A,
        permissions: Array<String>
    ): Boolean {
        val list = ArrayList<Boolean>()
        permissions.forEach {
            list.add(ActivityCompat.shouldShowRequestPermissionRationale(activity, it))
        }
        return list.all { it }
    }

    override fun <A : Activity> requestPermission(
        activity: A,
        requestCode: Int,
        permissions: Array<String>
    ): Unit = ActivityCompat.requestPermissions(activity, permissions, requestCode)

}