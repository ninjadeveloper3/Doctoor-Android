package com.Doctoor.app.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.Doctoor.app.R;
import com.Doctoor.app.utils.validation.rule.RegexRule;
import com.Doctoor.app.utils.validation.util.EditTextHandler;
import com.Doctoor.app.utils.validation.util.ErrorMessageHelper;
import com.Doctoor.app.utils.validation.util.ViewTagHelper;


public class RegexBindings {

    @BindingAdapter(value = {"validateRegex", "validateRegexMessage", "validateRegexAutoDismiss"}, requireAll = false)
    public static void bindingRegex(TextView view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_regex_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new RegexRule(view, pattern, handledErrorMessage));
    }

}
