package com.onurcinstas.medion.model.login

import java.util.regex.Pattern


class LoginUserItem(private val username: String, private val password: String) {

    fun isValid(): LoginDialogItem {

        return if (usernameValidation() && passwordValidation()) {
            LoginDialogItem.NO_PROBLEM
        } else if (!usernameValidation() && passwordValidation()) {
            LoginDialogItem.USERNAME_PROBLEM
        } else if (usernameValidation() && !passwordValidation()) {
            LoginDialogItem.PASSWORD_PROBLEM
        } else {
            LoginDialogItem.BOTH_PROBLEM
        }

    }

    private fun passwordValidation(): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                ".{6,}" +               //at least 6 characters
                "$")
        return passwordREGEX.matcher(password).matches()
    }
    private fun usernameValidation():Boolean{
        return username.length > 2
    }
}