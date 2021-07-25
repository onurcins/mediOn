package com.onurcinstas.medion.viewmodel

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.DialogBinding
import com.onurcinstas.medion.model.login.LoginDialogItem
import com.onurcinstas.medion.model.login.LoginUserItem
import com.onurcinstas.medion.util.App


class LoginViewModel(application: Application) : BaseViewModel(application) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    var passwordSee = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    val userMutableLiveData = MutableLiveData<LoginUserItem>()


    fun loginButtonClicked(view: View){

        //Extra precaution
        if (username.value != null && password.value != null) {
            val loginUser = LoginUserItem(username.value!!, password.value!!)
            userMutableLiveData.value = loginUser

        }
        Log.d("usernameValue", username.value.toString())
    }

    fun passwordSeeClicked(view: View) {
        passwordSee.value = !passwordSee.value!!
    }

    fun isClickable(): Boolean {

        if (!username.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun setStatusBar(activity: Activity) {
        val window: Window = activity.window
        val background = activity.resources.getDrawable(R.drawable.bg_login, activity.theme)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = activity.resources.getColor(
            android.R.color.transparent,
            activity.theme
        )
        window.navigationBarColor = activity.resources.getColor(
            android.R.color.transparent,
            activity.theme
        )
        window.setBackgroundDrawable(background)

    }

    fun showMessage(context: Context, loginDialogItem: LoginDialogItem, complete: (Boolean) -> Unit){

        val binding = DialogBinding.inflate(LayoutInflater.from(context))

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        binding.dialogLayout.layoutParams.width = Resources.getSystem().displayMetrics.widthPixels - 100

        when (loginDialogItem) {
            LoginDialogItem.USERNAME_PROBLEM -> binding.dialogTitle.text = context.getString(R.string.username_err)
            LoginDialogItem.PASSWORD_PROBLEM -> binding.dialogTitle.text = context.getString(R.string.password_err)
            LoginDialogItem.BOTH_PROBLEM -> binding.dialogTitle.text = context.getString(R.string.both_err)
            LoginDialogItem.NO_PROBLEM -> {
                binding.dialogTitle.text = context.getString(R.string.welcome)
                binding.dialogButton.visibility = View.GONE
                binding.dialogProgressbar.visibility = View.VISIBLE
                //Simulate server response
                val timer = object: CountDownTimer(2000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        App.prefs.setLoggedIn(true)
                        App.prefs.setUsername(username.value!!)
                        complete(true)
                        dialog.dismiss()
                    }
                }
                timer.start()
            }
        }

        binding.dialogButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }
}