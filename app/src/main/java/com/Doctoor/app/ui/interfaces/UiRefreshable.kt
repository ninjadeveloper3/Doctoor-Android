package com.Doctoor.app.ui.interfaces

/**

 * Indicate refreshable ui objects (be able to swipe to refresh), eg. activity, fragment...
 */

interface UiRefreshable : Refreshable {
    fun doneRefresh()
    fun refreshWithUi()
    fun refreshWithUi(delay: Int)
    fun setRefreshEnabled(enabled: Boolean)
}
