package com.Doctoor.app.utils.validation.rule;

import android.widget.TextView;

import com.Doctoor.app.utils.validation.util.EditTextHandler;


public class ConfirmPasswordRule extends Rule<TextView, TextView> {

    public ConfirmPasswordRule(TextView view, TextView value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {
        if (value == null) return false;

        final String value1 = view.getText().toString();
        final String value2 = value.getText().toString();
        return value1.trim().equals(value2.trim());
    }

    @Override
    public void onValidationSucceeded(TextView view) {
        EditTextHandler.removeError(view);
    }

    @Override
    public void onValidationFailed(TextView view) {
        EditTextHandler.setError(view, errorMessage);
    }
}
