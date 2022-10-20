
package com.Doctoor.app.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.Doctoor.app.R;
import com.Doctoor.app.utils.validation.rule.EmptyRule;
import com.Doctoor.app.utils.validation.rule.MaxLengthRule;
import com.Doctoor.app.utils.validation.rule.MinLengthRule;
import com.Doctoor.app.utils.validation.util.EditTextHandler;
import com.Doctoor.app.utils.validation.util.ErrorMessageHelper;
import com.Doctoor.app.utils.validation.util.ViewTagHelper;


public class LengthBindings {

    @BindingAdapter(value = {"validateMinLength", "validateMinLengthMessage", "validateMinLengthAutoDismiss"}, requireAll = false)
    public static void bindingMinLength(TextView view, int minLength, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.default_required_length_message_min, minLength);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new MinLengthRule(view, minLength, handledErrorMessage));
    }

    @BindingAdapter(value = {"validateMaxLength", "validateMaxLengthMessage", "validateMaxLengthAutoDismiss"}, requireAll = false)
    public static void bindingMaxLength(TextView view, int maxLength, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.default_required_length_message_max, maxLength);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new MaxLengthRule(view, maxLength, handledErrorMessage));
    }

    @BindingAdapter(value = {"validateEmpty", "validateEmptyMessage", "validateEmptyAutoDismiss"}, requireAll = false)
    public static void bindingEmpty(TextView view, boolean empty, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_empty_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new EmptyRule(view, empty, handledErrorMessage));
    }

}
