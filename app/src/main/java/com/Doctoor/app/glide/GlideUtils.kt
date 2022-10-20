package com.Doctoor.app.glide

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat.getColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import java.io.File

const val BASE_IMAGES_URL = BuildConfig.BASE_IMAGES_URL
const val BASE_PROFILE_URL = BuildConfig.BASE_PROFILE_URL

fun setImage(imageView: ImageView, url: String) {

    val mUrl = getUrl(url)
//    val fallbackDrawables = getFallbackDrawables()
//    val index = Math.abs(mUrl.hashCode() % fallbackDrawables.size)
//    Glide.with(imageView).load(mUrl).error(fallbackDrawables[index])
//        .placeholder(fallbackDrawables[index]).into(imageView)


    val placeholder = R.drawable.ic_home_medics_placeholder
    Glide.with(imageView).load(mUrl).error(placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
        .disallowHardwareConfig()
        .placeholder(placeholder).into(imageView)
}


fun setBannerImage(imageView: ImageView, url: String) {

    val mUrl = getProfileUrl(url)
    val placeholder = R.drawable.main_banner
    Glide.with(imageView).load(mUrl).error(placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
        .disallowHardwareConfig()
        .placeholder(placeholder).into(imageView)
}

fun getUrl(url: String?): String {
    return if (url == null || TextUtils.isEmpty(url)) "http:" else BASE_IMAGES_URL + url
}

fun getProfileUrl(url: String?): String {
    return if (url == null || TextUtils.isEmpty(url)) "http:" else BASE_PROFILE_URL + url
}

fun getUrls(url: String?) = if (url == null || TextUtils.isEmpty(url)) "http:" else url

fun getFallbackDrawables(): Array<Drawable> {
    return arrayOf(
        ColorDrawable(getColor(DoctoorApp.instance!!, R.color.md_purple_800)),
        ColorDrawable(getColor(DoctoorApp.instance!!, R.color.md_light_green_600)),
        ColorDrawable(getColor(DoctoorApp.instance!!, R.color.colorPrimary)),
        ColorDrawable(getColor(DoctoorApp.instance!!, R.color.md_orange_600)),
        ColorDrawable(getColor(DoctoorApp.instance!!, R.color.md_red_600))
    )
}

fun setImage(imageView: ImageView, url: String, fallback: Drawable) {
    Glide.with(imageView).load(getUrl(url)).error(fallback).fitCenter()
        .placeholder(fallback).into(imageView)
}

fun setImage1(context: Context, imageView: ImageView, url: String) {
    Glide.with(context).load(getUrl(url)).into(imageView)
}

fun setImage(context: Context, imageView: ImageView, uri: Uri) {

    val mUrl = uri.path
    val fallbackDrawables = getFallbackDrawables()
    val index = Math.abs(mUrl!!.hashCode() % fallbackDrawables.size)
    Glide.with(imageView).load(uri).error(fallbackDrawables[index])
        .placeholder(fallbackDrawables[index]).centerCrop().into(imageView)
}

fun setImageNew(context: Context, imageView: ImageView, url: String) {

    val mUrl = getUrl(url)
    val fallbackDrawables = getFallbackDrawables()
    val requestManager = Glide.with(context)
    val requestBuilder = requestManager.load(getUrl(url))
    val requestOptions = RequestOptions()
    // Set place holder image resource id.
    val index = Math.abs(mUrl.hashCode() % fallbackDrawables.size)
    requestOptions.placeholder(fallbackDrawables[index])

    requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    /* Set load image error place holder resource id. When image can not be loaded show this image.*/
    requestOptions.error(fallbackDrawables[index])
    requestBuilder.apply(requestOptions)
    requestBuilder.into(imageView)

}

fun setImage(context: Context, imageView: ImageView, file: File, fallback: Drawable) {
    Glide.with(context).load(file).error(fallback)
        .placeholder(fallback).centerCrop().into(imageView)
}

fun setCircleCropImage(imageView: AppCompatImageView, url: String) {

    val mUrl = getUrl(url)
    val fallbackDrawables = getFallbackDrawables()
    val index = Math.abs(mUrl.hashCode() % fallbackDrawables.size)
    Glide.with(imageView).load(mUrl)
        .error(fallbackDrawables[index])
        .placeholder(fallbackDrawables[index])
        .into(imageView)
}

fun setCircleCropImage(imageView: ImageView, url: String, fallback: Int) {

    val mUrl = getUrl(url)
    Glide.with(imageView).load(mUrl)
        .error(fallback)
        .placeholder(fallback)
        .into(imageView)
}

fun setCircleProfileImage(imageView: ImageView, url: String, fallback: Int) {
    val mUrl = getProfileUrl(url)
    Glide.with(imageView).load(mUrl)
        .error(fallback)
        .placeholder(fallback)
        .into(imageView)
}

fun setBlurImage(imageView: AppCompatImageView, uri: Uri) {
    Glide.with(imageView).load(uri).apply(RequestOptions.bitmapTransform(BlurTransformation(10, 3)))
        .into(imageView)
}

fun setImage(imageView: ImageView, uri: Uri) {
    Glide.with(imageView).load(uri).into(imageView)

}

fun setImage(imageView: ImageView, file: File, applyCircle: Boolean = false) {
    Glide.with(imageView).load(file).into(imageView)
}

