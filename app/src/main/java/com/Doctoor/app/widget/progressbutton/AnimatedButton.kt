package com.Doctoor.app.widget.progressbutton

interface AnimatedButton {
    fun startAnimation()

    fun revertAnimation()

    fun revertAnimation(onAnimationEndListener: OnAnimationEndListener)

    fun dispose()
}
