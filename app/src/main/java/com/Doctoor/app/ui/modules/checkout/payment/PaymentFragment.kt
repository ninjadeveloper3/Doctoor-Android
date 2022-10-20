package com.Doctoor.app.ui.modules.checkout.payment

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentPaymentBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class PaymentFragment : BaseViewModelFragment<FragmentPaymentBinding, PaymentFragmentVM>() {

    override fun layoutRes() = R.layout.fragment_payment

    companion object {
        fun newInstance(): PaymentFragment {
            return PaymentFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<PaymentFragmentVM>

    override fun getToolBarTile() = getString(R.string.checkout)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.paymentFragmentVM

}