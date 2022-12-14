package com.Doctoor.app.utils.validation.rule;

import android.webkit.URLUtil;
import android.widget.TextView;

import com.Doctoor.app.utils.validation.util.EditTextHandler;


public class UrlTypeRule extends TypeRule {

    public UrlTypeRule(TextView textView, String errorMessage) {
        super(textView, FieldType.Url, errorMessage);
    }

    @Override
    protected boolean isValid(TextView view) {
        return URLUtil.isValidUrl(view.getText().toString());
    }

    @Override
    protected void onValidationSucceeded(TextView view) {
        super.onValidationSucceeded(view);
        EditTextHandler.removeError(view);
    }

    @Override
    protected void onValidationFailed(TextView view) {
        super.onValidationFailed(view);
        EditTextHandler.setError(view, errorMessage);
    }
}
