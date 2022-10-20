package com.Doctoor.app.widget.progressbutton

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

class CircularAnimatedDrawable
/**
 * @param view        View to be animated
 * @param borderWidth The width of the spinning bar
 * @param arcColor    The color of the spinning bar
 */
    (private val mAnimatedView: View, private val mBorderWidth: Float, arcColor: Int) : Drawable(),
    Animatable {
    private val fBounds = RectF()
    private var mValueAnimatorAngle: ValueAnimator? = null
    private var mValueAnimatorSweep: ValueAnimator? = null
    private val mPaint: Paint
    private var mCurrentGlobalAngle: Float = 0.toFloat()
    private var mCurrentSweepAngle: Float = 0.toFloat()
    private var mCurrentGlobalAngleOffset: Float = 0.toFloat()

    private var mModeAppearing: Boolean = false
    private var mRunning: Boolean = false

    init {

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth
        mPaint.color = arcColor

        setupAnimations()
    }

    /**
     * Set up all the animations. There are two animation: Global angle animation and sweep animation.
     */
    private fun setupAnimations() {
        mValueAnimatorAngle = ValueAnimator.ofFloat(0f, 360f)
        mValueAnimatorAngle!!.interpolator = ANGLE_INTERPOLATOR
        mValueAnimatorAngle!!.duration = ANGLE_ANIMATOR_DURATION.toLong()
        mValueAnimatorAngle!!.repeatCount = ValueAnimator.INFINITE
        mValueAnimatorAngle!!.addUpdateListener { animation ->
            setCurrentGlobalAngle(animation.animatedValue as Float)
            mAnimatedView.invalidate()
        }

        mValueAnimatorSweep = ValueAnimator.ofFloat(0f, 360f - 2 * MIN_SWEEP_ANGLE)
        mValueAnimatorSweep!!.interpolator = SWEEP_INTERPOLATOR
        mValueAnimatorSweep!!.duration = SWEEP_ANIMATOR_DURATION.toLong()
        mValueAnimatorSweep!!.repeatCount = ValueAnimator.INFINITE
        mValueAnimatorSweep!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                toggleAppearingMode()
            }
        })
        mValueAnimatorSweep!!.addUpdateListener { animation ->
            setCurrentSweepAngle(animation.animatedValue as Float)
            mAnimatedView.invalidate()
        }

    }

    /**
     * Set the Global angle of the spinning bar.
     *
     * @param currentGlobalAngle
     */
    fun setCurrentGlobalAngle(currentGlobalAngle: Float) {
        mCurrentGlobalAngle = currentGlobalAngle
        invalidateSelf()
    }

    /**
     * This method is called in every repetition of the animation, so the animation make the sweep
     * growing and then make it shirinking.
     */
    private fun toggleAppearingMode() {
        mModeAppearing = !mModeAppearing
        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360
        }
    }

    /**
     * Sets the current sweep angle, so the fancy loading animation can happen
     *
     * @param currentSweepAngle
     */
    fun setCurrentSweepAngle(currentSweepAngle: Float) {
        mCurrentSweepAngle = currentSweepAngle
        invalidateSelf()
    }

    /**
     * Start the animation
     */
    override fun start() {
        if (isRunning) {
            return
        }

        mRunning = true
        mValueAnimatorAngle!!.start()
        mValueAnimatorSweep!!.start()
    }

    /**
     * Stops the animation
     */
    override fun stop() {
        if (!isRunning) {
            return
        }

        mRunning = false
        mValueAnimatorAngle!!.cancel()
        mValueAnimatorSweep!!.cancel()
    }

    /**
     * Method the inform if the animation is in process
     *
     * @return
     */
    override fun isRunning(): Boolean {
        return mRunning
    }

    /**
     * Method called when the drawable is going to draw itself.
     *
     * @param canvas
     */
    override fun draw(canvas: Canvas) {
        var startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset
        var sweepAngle = mCurrentSweepAngle
        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle
            sweepAngle = 360f - sweepAngle - MIN_SWEEP_ANGLE
        } else {
            sweepAngle += MIN_SWEEP_ANGLE
        }

        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        fBounds.left = bounds.left.toFloat() + mBorderWidth / 2f + .5f
        fBounds.right = bounds.right.toFloat() - mBorderWidth / 2f - .5f
        fBounds.top = bounds.top.toFloat() + mBorderWidth / 2f + .5f
        fBounds.bottom = bounds.bottom.toFloat() - mBorderWidth / 2f - .5f

    }

    fun dispose() {
        if (mValueAnimatorAngle != null) {
            mValueAnimatorAngle!!.end()
            mValueAnimatorAngle!!.removeAllUpdateListeners()
            mValueAnimatorAngle!!.cancel()
        }

        mValueAnimatorAngle = null

        if (mValueAnimatorSweep != null) {
            mValueAnimatorSweep!!.end()
            mValueAnimatorSweep!!.removeAllUpdateListeners()
            mValueAnimatorSweep!!.cancel()
        }

        mValueAnimatorSweep = null
    }

    companion object {
        private val ANGLE_INTERPOLATOR = LinearInterpolator()
        private val SWEEP_INTERPOLATOR = DecelerateInterpolator()
        private val ANGLE_ANIMATOR_DURATION = 2000
        private val SWEEP_ANIMATOR_DURATION = 900
        private val MIN_SWEEP_ANGLE = 30f
    }
}
