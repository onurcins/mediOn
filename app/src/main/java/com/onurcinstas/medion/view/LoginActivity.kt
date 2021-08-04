package com.onurcinstas.medion.view

import android.content.Intent
import android.os.Bundle
import android.text.Selection
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.ActivityLoginBinding
import com.onurcinstas.medion.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setStatusBar(this)
        setContentView(view)

        binding.apply {
            lifecycleOwner = this@LoginActivity
            login = viewModel
        }


        observeLiveData()


    }

    private fun observeLiveData() {


        viewModel.userMutableLiveData.observe(this , { loginUser ->

            viewModel.showMessage(this@LoginActivity, loginUser.isValid()){
                if (it) {
                    val intent =  Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        })

        viewModel.username.observe(this, {

            if (!viewModel.isClickable()) {
                button(false)
            } else {
                button(true)
            }

        })

        viewModel.password.observe(this, {

            if (!viewModel.isClickable()) {
                button(false)
            } else {
                button(true)
            }

        })

        viewModel.passwordSee.observe(this, {
            if (it) {
                binding.passwordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.passwordSee.setColorFilter(ContextCompat.getColor(this, R.color.login_button))
                Selection.setSelection(binding.passwordET.text, binding.passwordET.text.length)
            } else {
                binding.passwordET.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.passwordSee.setColorFilter(ContextCompat.getColor(this, R.color.enable_false))
                Selection.setSelection(binding.passwordET.text, binding.passwordET.text.length)
            }
        })
    }

    private fun button(isEnabled: Boolean) {

        binding.apply {
            if (!isEnabled) {
                loginButton.setBackgroundResource(R.drawable.bg_login_button_non_click)
                loginButton.isEnabled = false
            } else {
                loginButton.setBackgroundResource(R.drawable.bg_login_button)
                loginButton.isEnabled = true
            }
        }

    }
}