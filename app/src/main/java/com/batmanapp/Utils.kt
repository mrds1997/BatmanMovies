package com.batmanapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.batmanapp.data.remote.Resource
import com.google.android.material.snackbar.Snackbar

class Utils {

    companion object{
        fun Activity.handleApiError(
            failure: Resource.Failure,
            retry:(() -> Unit)? = null
            ){
            if(failure.isNetworkError){
                findViewById<ViewGroup>(R.id.layoutRoot).showSnackBar(getString(R.string.check_internet_connection))
            } else {
                findViewById<ViewGroup>(R.id.layoutRoot).showSnackBar(failure.errorMessage.toString())
            }
        }

        fun View.showSnackBar(
            message: String,
            action: (()->Unit) ?= null
        ){
            val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            action.let {
                snackbar.setAction("Ok"){
                    it
                }
            }
            snackbar.show()
        }
    }






}