package com.Doctoor.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class PathwayButton extends AppCompatButton {
    public PathwayButton(Context context) {
        super(context);
        if (!isInEditMode())
            touchUp();
    }

    private void touchUp() {
        getBackground().setAlpha(255);
        animate().scaleX(1F).scaleY(1F).setDuration(50).setStartDelay(0);
    }

    public PathwayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            touchUp();
    }

    public PathwayButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            touchUp();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                touchDown();
                break;
            case (MotionEvent.ACTION_UP):
                touchUp();
                break;
            case (MotionEvent.ACTION_CANCEL):
                touchUp();
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                touchUp();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            touchUp();
        } else {
            touchDown();
        }
    }

    private void touchDown() {
        getBackground().setAlpha(150);
        animate().scaleX(0.95F).scaleY(0.95F).setDuration(50).setStartDelay(0);
    }
}
