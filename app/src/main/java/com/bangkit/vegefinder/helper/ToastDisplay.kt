package com.bangkit.vegefinder.helper

import android.content.Context
import android.widget.Toast

class ToastDisplay {
    companion object {
        fun displayToastWithMessage(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun displayToastWithError(context: Context){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }

        fun displayToastWithFetchError(context: Context, data: String){
            Toast.makeText(context, "Can't fetch $data data", Toast.LENGTH_SHORT).show()

        }
    }
}