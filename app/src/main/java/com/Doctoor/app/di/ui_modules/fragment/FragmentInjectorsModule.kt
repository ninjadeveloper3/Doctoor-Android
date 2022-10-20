package com.Doctoor.app.di.ui_modules.fragment

import com.Doctoor.app.ui.modules.about_us.AboutUsFragment
import com.Doctoor.app.ui.modules.about_us.AboutUsModule
import com.Doctoor.app.ui.modules.cart.CartFragment
import com.Doctoor.app.ui.modules.cart.CartModule
import com.Doctoor.app.ui.modules.checkout.billing.BillingFragment
import com.Doctoor.app.ui.modules.checkout.billing.BillingModule
import com.Doctoor.app.ui.modules.checkout.complete_order.CompleteOrderFragment
import com.Doctoor.app.ui.modules.checkout.complete_order.CompleteOrderModule
import com.Doctoor.app.ui.modules.checkout.payment.PaymentFragment
import com.Doctoor.app.ui.modules.checkout.payment.PaymentModule
import com.Doctoor.app.ui.modules.checkout.shipping.ShippingFragment
import com.Doctoor.app.ui.modules.checkout.shipping.ShippingModule
import com.Doctoor.app.ui.modules.code_verification.CodeVerificationFragment
import com.Doctoor.app.ui.modules.code_verification.CodeVerificationModule
import com.Doctoor.app.ui.modules.corporate_signup.CorporateSignupFragment
import com.Doctoor.app.ui.modules.corporate_signup.CorporateSignupModule
import com.Doctoor.app.ui.modules.dashboard.HomeFragment
import com.Doctoor.app.ui.modules.dashboard.HomeFragmentModule
import com.Doctoor.app.ui.modules.doctor_profile.DoctorProfileFragment
import com.Doctoor.app.ui.modules.doctor_profile.DoctorProfileModule
import com.Doctoor.app.ui.modules.easypaisa.EasypaisaPaymentFragment
import com.Doctoor.app.ui.modules.easypaisa.EasypaisaPaymentModule
import com.Doctoor.app.ui.modules.forgot_password.ForgotPasswordFragment
import com.Doctoor.app.ui.modules.forgot_password.ForgotPasswordModule
import com.Doctoor.app.ui.modules.in_app_browser.InAppBrowserFragment
import com.Doctoor.app.ui.modules.in_app_browser.InAppBrowserModule
import com.Doctoor.app.ui.modules.lab_reports.LabReportsFragment
import com.Doctoor.app.ui.modules.lab_reports.LabReportsFragmentModule
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.ui.modules.landing.LandingFragmentModule
import com.Doctoor.app.ui.modules.login.LoginFragment
import com.Doctoor.app.ui.modules.login.LoginFragmentModule
import com.Doctoor.app.ui.modules.medicine.category.MedicineCategoryFragment
import com.Doctoor.app.ui.modules.medicine.category.MedicineCategoryFragmentModule
import com.Doctoor.app.ui.modules.medicine.category.popular_medicines.PopularMedicinesFragment
import com.Doctoor.app.ui.modules.medicine.category.popular_medicines.PopularMedicinesModule
import com.Doctoor.app.ui.modules.medicine.details.MedicineDetailsFragment
import com.Doctoor.app.ui.modules.medicine.details.MedicineDetailsModule
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineModule
import com.Doctoor.app.ui.modules.my_prescriptions.MyPrescriptionsFragment
import com.Doctoor.app.ui.modules.my_prescriptions.MyPrescriptionsModule
import com.Doctoor.app.ui.modules.myorder.MyOrderFragment
import com.Doctoor.app.ui.modules.myorder.MyOrderFragmentModule
import com.Doctoor.app.ui.modules.myorder.MyOrderPagerFragment
import com.Doctoor.app.ui.modules.myorder.MyOrderPagerFragmentModule
import com.Doctoor.app.ui.modules.myorder.rental_history.RentalEquipmentsHistoryFragment
import com.Doctoor.app.ui.modules.myorder.rental_history.RentalEquipmentsHistoryModule
import com.Doctoor.app.ui.modules.myorder.services_history.ServicesHistoryFragment
import com.Doctoor.app.ui.modules.myorder.services_history.ServicesHistoryFragmentModule
import com.Doctoor.app.ui.modules.new_password.NewPasswordFragment
import com.Doctoor.app.ui.modules.new_password.NewPasswordModule
import com.Doctoor.app.ui.modules.notification.NotificationFragment
import com.Doctoor.app.ui.modules.notification.NotificationFragmentModule
import com.Doctoor.app.ui.modules.others.OtherCategoriesFragment
import com.Doctoor.app.ui.modules.others.OtherCategoriesModule
import com.Doctoor.app.ui.modules.popular_services.PopularServicesFragment
import com.Doctoor.app.ui.modules.popular_services.PopularServicesModule
import com.Doctoor.app.ui.modules.product_details.ProductDetailsFragment
import com.Doctoor.app.ui.modules.product_details.ProductDetailsModule
import com.Doctoor.app.ui.modules.profile.UserProfileFragment
import com.Doctoor.app.ui.modules.profile.UserProfileModule
import com.Doctoor.app.ui.modules.profile.edit_profile.EditProfileFragment
import com.Doctoor.app.ui.modules.profile.edit_profile.EditProfileModule
import com.Doctoor.app.ui.modules.register_phone.RegisterPhoneFragment
import com.Doctoor.app.ui.modules.register_phone.RegisterPhoneModule
import com.Doctoor.app.ui.modules.select_city.SelectCityFragment
import com.Doctoor.app.ui.modules.select_city.SelectCityModule
import com.Doctoor.app.ui.modules.select_equipment.SelectEquipmentFragment
import com.Doctoor.app.ui.modules.select_equipment.SelectEquipmentModule
import com.Doctoor.app.ui.modules.select_equipment.popular_equipments.PopularEquipmentsFragment
import com.Doctoor.app.ui.modules.select_equipment.popular_equipments.PopularEquipmentsModule
import com.Doctoor.app.ui.modules.select_equipment.rental_equipments.RentalEquipmentsFragment
import com.Doctoor.app.ui.modules.select_equipment.rental_equipments.RentalEquipmentsModule
import com.Doctoor.app.ui.modules.select_lab.SelectLabCategoryFragment
import com.Doctoor.app.ui.modules.select_lab.SelectLabCategoryFragmentModule
import com.Doctoor.app.ui.modules.select_lab.SelectLabFragment
import com.Doctoor.app.ui.modules.select_lab.SelectLabFragmentModule
import com.Doctoor.app.ui.modules.select_lab.test.SelectTestFragment
import com.Doctoor.app.ui.modules.select_lab.test.SelectTestFragmentModule
import com.Doctoor.app.ui.modules.select_lab.test.popular_tests.PopularTestsCategoryFragment
import com.Doctoor.app.ui.modules.select_lab.test.popular_tests.PopularTestsCategoryModule
import com.Doctoor.app.ui.modules.service_type.MedicalServicesFragment
import com.Doctoor.app.ui.modules.service_type.MedicalServicesFragmentModule
import com.Doctoor.app.ui.modules.service_type.ServiceTypeFragment
import com.Doctoor.app.ui.modules.service_type.ServiceTypeModule
import com.Doctoor.app.ui.modules.signup.SignupFragment
import com.Doctoor.app.ui.modules.signup.SignupFragmentModule
import com.Doctoor.app.ui.modules.test_details.TestDetailsFragment
import com.Doctoor.app.ui.modules.test_details.TestDetailsModule
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionModule
import com.Doctoor.app.ui.modules.upload_prescription_submit.UploadPrescriptionSubmitFragment
import com.Doctoor.app.ui.modules.upload_prescription_submit.UploadPrescriptionSubmitModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun mainFragmentInjector(): HomeFragment

    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    abstract fun loginFragmentInjector(): LoginFragment

    @ContributesAndroidInjector(modules = [SelectLabFragmentModule::class])
    abstract fun selectLabFragmentInjector(): SelectLabFragment

    @ContributesAndroidInjector(modules = [SignupFragmentModule::class])
    abstract fun signupFragmentInjector(): SignupFragment

    @ContributesAndroidInjector(modules = [LandingFragmentModule::class])
    abstract fun landingFragmentInjector(): LandingFragment

    @ContributesAndroidInjector(modules = [ForgotPasswordModule::class])
    abstract fun forgotPasswordFragmentInjector(): ForgotPasswordFragment

    @ContributesAndroidInjector(modules = [CodeVerificationModule::class])
    abstract fun codeVerificationFragmentInjector(): CodeVerificationFragment

    @ContributesAndroidInjector(modules = [NewPasswordModule::class])
    abstract fun newPasswordFragmentInjector(): NewPasswordFragment

    @ContributesAndroidInjector(modules = [CorporateSignupModule::class])
    abstract fun corporateSignupFragmentInjector(): CorporateSignupFragment

    @ContributesAndroidInjector(modules = [SelectCityModule::class])
    abstract fun selectCityFragmentInjector(): SelectCityFragment

    @ContributesAndroidInjector(modules = [UserProfileModule::class])
    abstract fun userProfileFragmentInjector(): UserProfileFragment

    @ContributesAndroidInjector(modules = [UploadPrescriptionModule::class])
    abstract fun uploadPrescriptionFragmentInjector(): UploadPrescriptionFragment

    @ContributesAndroidInjector(modules = [ServiceTypeModule::class])
    abstract fun serviceTypeFragmentInjector(): ServiceTypeFragment

    @ContributesAndroidInjector(modules = [AboutUsModule::class])
    abstract fun aboutUsFragmentInjector(): AboutUsFragment

    @ContributesAndroidInjector(modules = [NotificationFragmentModule::class])
    abstract fun notificationFragmentInjector(): NotificationFragment

    @ContributesAndroidInjector(modules = [UploadPrescriptionSubmitModule::class])
    abstract fun uploadPrescriptionSubmitFragmentInjector(): UploadPrescriptionSubmitFragment

    @ContributesAndroidInjector(modules = [MyOrderPagerFragmentModule::class])
    abstract fun myOrderPagerFragmentInjector(): MyOrderPagerFragment

    @ContributesAndroidInjector(modules = [MyOrderFragmentModule::class])
    abstract fun myOrderFragmentInjector(): MyOrderFragment

    @ContributesAndroidInjector(modules = [LabReportsFragmentModule::class])
    abstract fun labReportsFragmentInjector(): LabReportsFragment

    @ContributesAndroidInjector(modules = [SelectLabCategoryFragmentModule::class])
    abstract fun selectLabCategoryFragmentInjector(): SelectLabCategoryFragment

    @ContributesAndroidInjector(modules = [TestDetailsModule::class])
    abstract fun testDetailsFragmentInjector(): TestDetailsFragment

    @ContributesAndroidInjector(modules = [MedicineDetailsModule::class])
    abstract fun medicineDetailsFragmentInjector(): MedicineDetailsFragment

    @ContributesAndroidInjector(modules = [ProductDetailsModule::class])
    abstract fun productDetailsFragmentInjector(): ProductDetailsFragment

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun confirmSelectionMedicineFragmentInjector(): CartFragment

    @ContributesAndroidInjector(modules = [DoctorProfileModule::class])
    abstract fun doctorProfileFragmentInjector(): DoctorProfileFragment

    @ContributesAndroidInjector(modules = [MedicalServicesFragmentModule::class])
    abstract fun medicalServicesFragmentInjector(): MedicalServicesFragment

    @ContributesAndroidInjector(modules = [MedicineCategoryFragmentModule::class])
    abstract fun medicineCategoryFragmentInjector(): MedicineCategoryFragment

    @ContributesAndroidInjector(modules = [SelectEquipmentModule::class])
    abstract fun selectEquipmentFragmentInjector(): SelectEquipmentFragment

    @ContributesAndroidInjector(modules = [SelectMedicineModule::class])
    abstract fun selectMedicineFragmentInjector(): SelectMedicineFragment

    @ContributesAndroidInjector(modules = [SelectTestFragmentModule::class])
    abstract fun selectTestFragmentInjector(): SelectTestFragment

    @ContributesAndroidInjector(modules = [ShippingModule::class])
    abstract fun shippingFragmentInjector(): ShippingFragment

    @ContributesAndroidInjector(modules = [BillingModule::class])
    abstract fun billingFragmentInjector(): BillingFragment

    @ContributesAndroidInjector(modules = [PaymentModule::class])
    abstract fun paymentFragmentInjector(): PaymentFragment

    @ContributesAndroidInjector(modules = [CompleteOrderModule::class])
    abstract fun completeOrderFragmentInjector(): CompleteOrderFragment

    @ContributesAndroidInjector(modules = [RegisterPhoneModule::class])
    abstract fun registerPhoneFragmentInjector(): RegisterPhoneFragment

    @ContributesAndroidInjector(modules = [EditProfileModule::class])
    abstract fun editProfileFragmentInjector(): EditProfileFragment

    @ContributesAndroidInjector(modules = [ServicesHistoryFragmentModule::class])
    abstract fun servicesHistoryFragmentFragmentInjector(): ServicesHistoryFragment

    @ContributesAndroidInjector(modules = [InAppBrowserModule::class])
    abstract fun inAppBrowserFragmentInjector(): InAppBrowserFragment

    @ContributesAndroidInjector(modules = [OtherCategoriesModule::class])
    abstract fun otherCategoriesFragmentInjector(): OtherCategoriesFragment

    @ContributesAndroidInjector(modules = [PopularMedicinesModule::class])
    abstract fun popularMedicinesFragmentInjector(): PopularMedicinesFragment

    @ContributesAndroidInjector(modules = [PopularTestsCategoryModule::class])
    abstract fun popularTestsFragmentInjector(): PopularTestsCategoryFragment

    @ContributesAndroidInjector(modules = [PopularServicesModule::class])
    abstract fun popularServicesFragmentInjector(): PopularServicesFragment

    @ContributesAndroidInjector(modules = [PopularEquipmentsModule::class])
    abstract fun popularEquipmentsFragmentInjector(): PopularEquipmentsFragment

    @ContributesAndroidInjector(modules = [RentalEquipmentsModule::class])
    abstract fun rentalEquipmentsFragmentInjector(): RentalEquipmentsFragment

    @ContributesAndroidInjector(modules = [RentalEquipmentsHistoryModule::class])
    abstract fun rentalEquipmentsHistoryFragmentInjector(): RentalEquipmentsHistoryFragment

    @ContributesAndroidInjector(modules = [EasypaisaPaymentModule::class])
    abstract fun easypaisaPaymentFragmentInjector(): EasypaisaPaymentFragment

    @ContributesAndroidInjector(modules = [MyPrescriptionsModule::class])
    abstract fun myPrescriptionsFragmentInjector(): MyPrescriptionsFragment
}