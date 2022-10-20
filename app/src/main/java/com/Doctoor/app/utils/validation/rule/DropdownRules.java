package com.Doctoor.app.utils.validation.rule;

import android.widget.TextView;

import com.Doctoor.app.utils.validation.util.EditTextHandler;


public class DropdownRules extends Rule<TextView, Boolean> {

    public DropdownRules(TextView view, Boolean value, String errorMessage) {
        super(view, value, errorMessage);
    }

    @Override
    public boolean isValid(TextView view) {

        final String value1 = view.getText().toString();
        return !value1.equals("Select Province");
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
