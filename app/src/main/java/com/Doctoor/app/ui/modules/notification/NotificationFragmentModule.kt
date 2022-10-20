package com.Doctoor.app.ui.modules.notification

import com.Doctoor.app.base.BaseListDataViewModel
import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class NotificationFragmentModule : BaseFragmentModule<NotificationFragment>() {

    @Provides
    @ViewModelInjection
    fun provideNotificationVM(
            fragment: NotificationFragment,
            viewModelProvider: InjectionViewModelProvider<NotificationFragmentVM>
    ) = viewModelProvider.get(fragment, NotificationFragmentVM::class)

    @Provides
    fun provideNotificationFragmentAdapter(fragment: NotificationFragment) =
            NotificationFragment.Adapter(fragment.mActivity, ArrayList(), fragment.navigatorHelper)
}