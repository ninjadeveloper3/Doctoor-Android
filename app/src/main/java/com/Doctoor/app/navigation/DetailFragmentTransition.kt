package com.Doctoor.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.transition.*

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class DetailFragmentTransition : TransitionSet() {
    init {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
            .addTransition(ChangeClipBounds())
    }
}