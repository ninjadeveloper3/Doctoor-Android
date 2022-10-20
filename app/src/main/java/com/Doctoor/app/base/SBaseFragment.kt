package com.Doctoor.app.base

import androidx.fragment.app.Fragment


abstract class SBaseFragment : Fragment(), OnBackPressed {
    abstract fun getToolBarTile(): String
    override fun onBackPressed() {
    }
}