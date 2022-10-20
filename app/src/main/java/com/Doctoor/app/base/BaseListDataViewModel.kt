package com.Doctoor.app.base;

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.Doctoor.app.model.Simple
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.interfaces.OnItemClickListener


abstract class BaseListDataViewModel<ITEM> : BaseViewModel(), OnItemClickListener<ITEM> {
    abstract fun setItem(item: ITEM)
    abstract fun getItem(): ITEM
    override fun onFirsTimeUiCreate(bundle: Bundle?) {

    }

    @CallSuper
    override fun onCreate(bundle: Bundle?, navigatorHelper: NavigatorHelper) {
        super.onCreate(bundle, navigatorHelper)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
    }

    @LayoutRes
    public abstract fun layoutRes(): Int

    companion object {
        fun getDumyListItems(): MutableList<Simple> {
            val mutableList: MutableList<Simple> = ArrayList()
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            mutableList.add(Simple("", 0))
            return mutableList
            //  var list =MutableList<Simple>(10)
        }
    }
}