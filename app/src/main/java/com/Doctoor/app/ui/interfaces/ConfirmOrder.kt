package com.Doctoor.app.ui.interfaces

interface ConfirmOrder {
    fun onConfirm(message: String, isRental: Boolean, isService: Boolean)

}