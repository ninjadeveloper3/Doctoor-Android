package com.Doctoor.app.base;

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.data.source.*
import com.Doctoor.app.model.ErrorEntity
import com.Doctoor.app.model.ErrorEntity.NO_INTERNET
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.UserResponse
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainAction
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.SafeMutableLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel : ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private var isFirstTimeUiCreate = true

    internal var storeManager: DataStoreManager? = null

    internal var navigatorHelper: NavigatorHelper? = null

    var bundle: Bundle? = null

    var mUserLiveData: MutableLiveData<UserResponse.User>? = MutableLiveData()

    var prescriptionId = 0

    fun getCalculatedPrice(price: Double?, quantity: Int?): Double {
        return quantity?.let { price?.times(it) }!!
    }

    companion object {
        var isLogin = MutableLiveData<Boolean>()
        var totalOrderPrice = MutableLiveData<Double>().apply { value = 0.0 }
        var totalOrderPriceIncludesDelivery = MutableLiveData<Double>().apply { value = 0.0 }
        var discountedOrderPrice = MutableLiveData<Double>().apply { value = 0.0 }
        var discountPercentage = MutableLiveData<Double>().apply { value = 0.0 }

        fun updateCartPrice(price: Double, orderQuantity: Int, isIncrement: Boolean) {
            if (isIncrement) {
                totalOrderPrice.value = totalOrderPrice.value?.plus(price.times(orderQuantity))
                /// round price
                totalOrderPrice.value = Math.round(totalOrderPrice.value!! * 100.0) / 100.0
            } else {
                if (totalOrderPrice.value!! > 0) {
                    totalOrderPrice.value = totalOrderPrice.value?.minus(price.times(orderQuantity))
                    /// round price
                    totalOrderPrice.value = Math.round(totalOrderPrice.value!! * 100.0) / 100.0
                }
            }
        }

        var cartItemsCount = MutableLiveData<Int>().apply { value = 0 }

        lateinit var shipping: ServiceRequest.Shipping

        lateinit var billing: ServiceRequest.Billing

    }

    @NonNull
    private var mCompositeDisposable = CompositeDisposable()

    public var stateLiveData: SafeMutableLiveData<State> = SafeMutableLiveData()

    private var mIsLoading = MutableLiveData<Boolean>()

    var isInCart = false


    /**
     * called after fragment / activity is created with input bundle arguments
     *
     * @param bundle argument data
     */
    @CallSuper
    open fun onCreate(bundle: Bundle?, navigatorHelper: NavigatorHelper) {
        this.navigatorHelper = navigatorHelper
        if (isFirstTimeUiCreate) {
            onFirsTimeUiCreate(bundle)
            isFirstTimeUiCreate = false
            mUserLiveData?.value = storeManager?.currentUser
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     *
     * @param bundle
     */
    abstract fun onFirsTimeUiCreate(bundle: Bundle?)

    open fun publishState(state: State) {
        stateLiveData.setValue(state)
    }

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    @CallSuper
    open fun onDestroyView() {
        navigatorHelper = null
    }

    fun disposeAllExecutions() {
        mCompositeDisposable.dispose();
        mCompositeDisposable = CompositeDisposable();
        publishState(State.success(null))
    }

    /**
     * Add and execute an resource flowable created by
     * [RestHelper.createRemoteSourceMapper]
     * Loading, error, success status will be updated automatically via [.stateLiveData] which should be observed
     * by fragments / activities to update UI appropriately
     * @param showProgress true if should show progress when executing, false if not
     * @param resourceFlowable flowable resource, see [com.Doctoor.app.data.source.SimpleRemoteSourceMapper]
     * @param responseConsumer consume response data
     * @param <T> type of response
    </T> */
    protected fun <T> execute(
        showProgress: Boolean, resourceFlowable: Flowable<Resource<T>>,
        @Nullable responseConsumer: PlainConsumer<T>?
    ) {
        val disposable = resourceFlowable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe { resource ->
                if (resource != null) {
                    if (resource.data != null && responseConsumer != null) {
                        responseConsumer.accept(resource.data)
                    }
                    if (resource.state.status === Status.LOADING && !showProgress) {
                        // do nothing if progress showing is not allowed
                    } else {
                        publishState(resource.state)
                    }
                }
            }
        mCompositeDisposable.add(disposable)
    }

    protected fun <T> execute(
        showProgress: Boolean,
        request: Single<T>, @NonNull responseConsumer: PlainConsumer<T>
    ) {
        execute(showProgress, true, request, responseConsumer, null)
    }

    protected fun <T> execute(
        showProgress: Boolean,
        request: Single<T>,
        @NonNull responseConsumer: PlainConsumer<T>,
        @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
    ) {
        execute(showProgress, true, request, responseConsumer, errorConsumer)
    }

    private fun <T> execute(
        showProgress: Boolean, publishState: Boolean, request: Single<T>,
        @NonNull responseConsumer: PlainConsumer<T>,
        @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
    ) {
        if (showProgress) {
            publishState(State.loading(null))
        }
        val disposable = RestHelper.makeRequest(request, true, PlainConsumer { response ->
            responseConsumer.accept(response)
            if (publishState) {
                publishState(State.success(null))
            }
        }, PlainConsumer { errorEntity ->
            errorConsumer?.accept(errorEntity)
            if (publishState) {
                if (errorEntity.httpCode == NO_INTERNET) {
                    publishState(State.network(errorEntity.getMessage()))
                } else {
                    publishState(State.error(errorEntity.getMessage()))
                }
            }
        })
        mCompositeDisposable.add(disposable)
    }

    /** It will check either the request response is success or not
     * @param code
     * */
    fun isSuccess(code: Int? = 204): Boolean {
        return code == ResponseCode.SUCCESS.value
    }

    /**
     * ensure we are in user scope (has saved user - user logged in)
     * should be called when activity / fragment is created
     * @param userConsumer consume user live data which would be observed to update UI
     * @param onError will be run if user data isn't exist
     * (show no login button, or navigate user to login page...)
     */
    fun ensureInUserScope(
        userConsumer: PlainConsumer<LiveData<UserResponse.User>>,
        onError: PlainAction
    ) {
        if (storeManager?.checkForSavedUserAndStartSessionIfHas()!!) {
            userConsumer.accept(storeManager?.dataStore?.userLiveData!!)
        } else {
            onError.run()
        }
    }

    fun navigateToLogin(view: View) {
        AlertUtils.showSnackBarLongMessage(
            view, "Please log in to continue", "Login", {
                login()
            }, null
        )
    }

    fun navigateToLoginWithRoot(view: View) {
        AlertUtils.showSnackBarLongMessage(
            view.rootView, "Please log in to continue", "Login", {
                login()
            }, null
        )
    }


    fun login() {
        navigatorHelper?.startFragment<LandingFragment>(LandingFragment::class.java.name, false)
    }
}


