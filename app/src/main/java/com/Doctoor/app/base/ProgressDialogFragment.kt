package com.Doctoor.app.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.Doctoor.app.R


class ProgressDialogFragment : DialogFragment() {
    var rotateTest: Float = 0.toFloat()
    private val mHandler = Handler()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments
        val activity = activity
        val builder = Dialog(activity!!, R.style.ProgressDialogFragment)
        val view = View.inflate(getActivity(), R.layout.fragment_progress_dialog, null)
        val titleTv = view.findViewById<TextView>(R.id.title)

        val title = args!!.getString(KEY_TITLE)
        if (TextUtils.isEmpty(title)) {
            titleTv.visibility = View.GONE
        } else {
            titleTv.text = title
        }

        builder.setContentView(view)

        val autoHideTime = args.getInt(KEY_AUTO_HIDE, -1)
        if (autoHideTime != -1) {
            mHandler.postDelayed({ hide(activity) }, autoHideTime.toLong())
        }

        return builder
    }

    companion object {
        private val KEY_AUTO_HIDE = "key_auto_hide_dialog"
        private val KEY_TITLE = "key_title"

        fun show(activity: FragmentActivity?, title: String) {
            if (activity == null) {
                return
            }
            show(activity, title, -1)
        }

        fun show(
            activity: FragmentActivity,
            title: String,
            autoHideTimeOutMillis: Int
        ): ProgressDialogFragment {

            val fm = activity.supportFragmentManager
            val ft = fm.beginTransaction()
            hideInternal(ft, fm)
            // Create and show the dialog.
            val newFragment = ProgressDialogFragment()
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            if (autoHideTimeOutMillis != -1) {
                args.putInt(KEY_AUTO_HIDE, autoHideTimeOutMillis)
            }
            newFragment.arguments = args
            newFragment.show(ft, ProgressDialogFragment::class.java.name)
            return newFragment
        }

        private fun hideInternal(ft: FragmentTransaction, fm: FragmentManager) {
            val prev = fm.findFragmentByTag(ProgressDialogFragment::class.java.name)
            if (prev != null) {
                ft.remove(prev)
            }
        }

        fun show(activity: FragmentActivity?, title: String, cancelable: Boolean) {
            if (activity == null) {
                return
            }
            show(activity, title, -1, cancelable)
        }

        fun show(
            activity: FragmentActivity,
            title: String,
            autoHideTimeOutMillis: Int,
            cancelable: Boolean
        ): ProgressDialogFragment {

            val fm = activity.supportFragmentManager
            val ft = fm.beginTransaction()
            hideInternal(ft, fm)
            // Create and show the dialog.
            val newFragment = ProgressDialogFragment()
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            if (autoHideTimeOutMillis != -1) {
                args.putInt(KEY_AUTO_HIDE, autoHideTimeOutMillis)
            }
            newFragment.arguments = args
            newFragment.isCancelable = cancelable
            newFragment.show(ft, ProgressDialogFragment::class.java.name)
            return newFragment
        }

        fun show(activity: FragmentActivity?, cancelable: Boolean) {
            if (activity == null) {
                return
            }
            show(activity, "", -1, cancelable)
        }

        fun show(activity: FragmentActivity?) {
            if (activity != null) {
                show(activity, "", -1)
            }

        }

        fun hide(activity: FragmentActivity?) {
            if (activity == null) {
                return
            }
            if (!activity.isFinishing) {
                val fm = activity.supportFragmentManager
                val ft = fm.beginTransaction()
                hideInternal(ft, fm)
                ft.commitAllowingStateLoss()
            }
        }
    }
}
