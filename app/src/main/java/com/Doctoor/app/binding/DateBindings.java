package com.Doctoor.app.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.Doctoor.app.R;
import com.Doctoor.app.utils.validation.rule.DateRule;
import com.Doctoor.app.utils.validation.util.EditTextHandler;
import com.Doctoor.app.utils.validation.util.ErrorMessageHelper;
import com.Doctoor.app.utils.validation.util.ViewTagHelper;


public class DateBindings {

    @BindingAdapter(value = {"validateDate", "validateDateMessage", "validateDateAutoDismiss"}, requireAll = false)
    public static void bindingDate(TextView view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_date_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new DateRule(view, pattern, handledErrorMessage));
    }

}
