package com.Doctoor.app.binding;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.Doctoor.app.R;
import com.Doctoor.app.utils.validation.rule.PhoneTypeRule;
import com.Doctoor.app.utils.validation.util.EditTextHandler;
import com.Doctoor.app.utils.validation.util.ErrorMessageHelper;
import com.Doctoor.app.utils.validation.util.ViewTagHelper;

public class PhoneBinding {
    @BindingAdapter(value = {"validPhone", "validateRegexMessage", "validateRegexAutoDismiss"}, requireAll = false)
    public static void bindPhone(EditText view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.invalid_phone_no_msg);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new PhoneTypeRule(view, pattern, handledErrorMessage));
    }

}
