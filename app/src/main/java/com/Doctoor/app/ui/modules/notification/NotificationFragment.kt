package com.Doctoor.app.ui.modules.notification

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentNotificationBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.Simple
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.widget.MultiStateView
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class NotificationFragment :
        BaseRecyclerViewFragment<FragmentNotificationBinding, NotificationFragmentVM, NotificationFragment.Adapter, Medicines.Notification>() {
    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<NotificationFragmentVM>


    override fun layoutRes() = R.layout.fragment_notification

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    override fun getToolBarTile() = getString(R.string.notifications)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.otificationFragmentVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.viewState = MultiStateView.ViewState.EMPTY
    }

    class Adapter(context: Context, mValue: MutableList<Medicines.Notification>, mNavidation: NavigatorHelper) :
            BaseRVFooterAdapter<Medicines.Notification, NotificationItemVM, Adapter.ViewHolder>(
                    context,
                    mValue,
                    mNavidation
            ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
                view: View,
                viewModel: NotificationItemVM,
                mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = NotificationItemVM()
        override fun getVariableId() = BR.notificationItemVM

        class ViewHolder(view: View, viewModel: NotificationItemVM, mDataBinding: ViewDataBinding) :
                BaseViewHolder<Medicines.Notification, NotificationItemVM>(view, viewModel, mDataBinding)
    }
}