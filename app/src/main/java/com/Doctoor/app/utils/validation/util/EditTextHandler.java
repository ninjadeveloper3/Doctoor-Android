package com.Doctoor.app.utils.validation.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.adapters.ListenerUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.Doctoor.app.R;


public class EditTextHandler {

    public static void removeError(TextView textView) {
        EditTextHandler.setError(textView, null);
    }

    public static void setError(TextView textView, String errorMessage) {
        TextInputLayout textInputLayout = getTextInputLayout(textView);
        if (textInputLayout != null) {
            textInputLayout.setErrorEnabled(!TextUtils.isEmpty(errorMessage));
            textInputLayout.setError(errorMessage);
        } else {
            textView.setError(errorMessage);
        }
    }

    @Nullable
    private static TextInputLayout getTextInputLayout(TextView textView) {
        TextInputLayout textInputLayout = null;

        ViewParent parent = textView.getParent();
        while (parent instanceof View) {
            if (parent instanceof TextInputLayout) {
                textInputLayout = (TextInputLayout) parent;
                break;
            }
            parent = parent.getParent();
        }
        return textInputLayout;
    }

    public static void disableErrorOnChanged(final TextView textView) {
        if (ListenerUtil.<TextWatcher>getListener(textView, R.id.text_watcher_clear_error) != null) {
            return;
        }

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditTextHandler.setError(textView, null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        textView.addTextChangedListener(textWatcher);
        ListenerUtil.trackListener(textView, textView, R.id.text_watcher_clear_error);
    }

}
