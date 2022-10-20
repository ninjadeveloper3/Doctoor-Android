package com.Doctoor.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.glide.setBannerImage
import com.Doctoor.app.model.response.Home
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.modules.dashboard.HomeSliderVM
import com.Doctoor.app.widget.slider.SliderViewAdapter
import javax.inject.Inject

class SliderAdapter(mValue: MutableList<Home.BannerImage>) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    @Inject
    lateinit var navigatorHelper: NavigatorHelper
    var bannerList: MutableList<Home.BannerImage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {

        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_image_slider, parent, false)

        val viewholder = SliderAdapterVH(view);
        val mDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        mDataBinding?.setVariable(BR.homeSliderVM, HomeSliderVM())
        return viewholder
    }

    override fun onBindViewHolder(holder: SliderAdapterVH?, position: Int) {
        val bannerImage: Home.BannerImage = bannerList[position]
        holder?.bind(bannerImage)
    }

    fun setData(@Nullable newData: MutableList<Home.BannerImage>) {
        this.bannerList.clear()
        this.bannerList = newData

        notifyDataSetChanged()
    }

    override fun getCount() = bannerList.run { count() }


    class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {

        private var imageView: ImageView? = null

        init {
            imageView = view.findViewById(R.id.iv_auto_image_slider)
        }

        fun bind(bannerImage: Home.BannerImage) {
            setBannerImage(imageView!!, bannerImage.file)
//            val placeholder = R.drawable.main_banner
//
//            Glide.with(imageView?.rootView!!).load(placeholder)
//                .placeholder(placeholder).into(imageView!!)
        }
    }
}