package com.Doctoor.app.widget.bottomnavigation

import android.content.Context
import android.view.ViewGroup

abstract class ItemsLayoutContainer(context: Context) : ViewGroup(context) {

    abstract fun getSelectedIndex(): Int

    abstract fun setSelectedIndex(index: Int, animate: Boolean)

    abstract fun populate(menu: MenuParser.Menu)

    var itemClickListener: OnItemClickListener? = null

    abstract fun removeAll()

    abstract fun setItemEnabled(index: Int, enabled: Boolean)
}
