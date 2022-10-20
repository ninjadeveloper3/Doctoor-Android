package com.Doctoor.app.ui.adapters


import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.Doctoor.app.navigation.NavigatorHelper
import java.util.*
import javax.inject.Inject

class SectionsPagerAdapter @Inject constructor(
    private val mContext: AppCompatActivity,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {
    private val mFragmentInfoList: MutableList<FragmentInfo> = ArrayList();

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    fun addFragmentInfo(fragmentName: String, title: String, bundle: Bundle) {
        addFragmentInfo(fragmentName, title, 0, bundle)
    }

    fun addFragmentInfo(fragmentName: String, title: String, icon: Int, bundle: Bundle) {
//
//        bundle.putInt(INDEX, mFragmentInfoList.size)
        mFragmentInfoList.add(FragmentInfo(fragmentName, title, icon, bundle))
    }

    override fun getItem(position: Int): Fragment {
        val info = getFragmentInfo(position)
        return Fragment.instantiate(
            mContext,
            info.fragmentName, info.args
        )
    }

    fun getFragmentInfo(position: Int): FragmentInfo {
        return mFragmentInfoList[position]
    }

    override fun getCount(): Int {
        return mFragmentInfoList.size
    }

//    override fun getItemPosition(fragment: T): Int {
//
//        val fragment = `object` as BaseViewModelFragment<*, *>
//        //        if (fragment != null && fragment instanceof UpdateableFragment) {
//        //            ((UpdateableFragment) fragment).update();
//        //        }
//        return super.getItemPosition(`object`)
//    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentInfoList[position].title
    }

    fun getTabIndex(fragmentName: String): Int {
        if (TextUtils.isEmpty(fragmentName)) {
            return -1
        }
        for (i in mFragmentInfoList.indices) {
            val info = mFragmentInfoList[i]
            if (info.fragmentName == fragmentName) {
                return i
            }
        }
        return -1
    }

    data class FragmentInfo(
        internal var fragmentName: String,
        internal var title: String,
        internal var icon: Int,
        internal var args: Bundle
    )

}
