package com.amaze.testproject.other

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogScreen private constructor(private val context: Context) {
    companion object {
        private var mProgressDialog: ProgressDialogScreen? = null
        private var progressDialog: ProgressDialog? = null
        fun showProgressDialog(context: Context) {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialogScreen(context)
                progressDialog = ProgressDialog(context)
                progressDialog!!.setMessage("Please wait")
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setCancelable(false)
                progressDialog!!.setCanceledOnTouchOutside(false)
            }
            try {
                progressDialog!!.show()
            } catch (e: Exception) {
            }
        }

        fun hideProgressDialog() {
            if (mProgressDialog != null && progressDialog != null && progressDialog!!.isShowing) {
                //    progressDialog.hide();
//            progressDialog.dismiss();
//            progressDialog=null;
                progressDialog!!.dismiss()
                progressDialog!!.cancel()
                progressDialog = null
                mProgressDialog = null
            }
        }
    }
}