package com.amaze.testproject.other

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat

object Utilities {

    fun isNetworkAvailable(context: Context): Boolean {
        var HaveConnectedWifi = false
        var HaveConnectedMobile = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) HaveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) HaveConnectedMobile = true
        }
        return HaveConnectedWifi || HaveConnectedMobile
    }

    fun showMessage(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun noInternetDialog(context: Context?) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Alert!")
        alertDialog.setMessage("Internet not available, Check your internet connectivity and try again")
        alertDialog.setIcon(R.drawable.ic_dialog_alert)
        alertDialog.setButton("OK") { dialog, which -> alertDialog.dismiss() }
        alertDialog.show()
    }

    fun noPermissionDialog(context: Context?,msg:String) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Alert!")
        alertDialog.setMessage(msg)
        alertDialog.setIcon(R.drawable.ic_dialog_alert)
        alertDialog.setButton("OK") { dialog, which -> alertDialog.dismiss() }
        alertDialog.show()
    }

    fun setStatusBarColor(activity: Activity){
        var isInDarkMode= checkDarkMode(activity)
        val window = activity.window
        if (isInDarkMode) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, R.color.holo_red_light)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, R.color.holo_red_light)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
    fun checkDarkMode(context: Context): Boolean {
        val nightModeFlags: Int =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                return true
            }
        }
        return false
    }


}