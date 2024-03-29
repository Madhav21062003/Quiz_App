package com.madhavsolanki.quizapp.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.madhavsolanki.quizapp.R

object Utils {

    private var dialog: AlertDialog? = null


    fun showDialog(context:Context){
        dialog = AlertDialog.Builder(context).setView(R.layout.progress_diaog).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }

    fun showToast(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}