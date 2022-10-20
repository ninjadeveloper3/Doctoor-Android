package com.Doctoor.app.binding;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.Doctoor.app.R;
import com.Doctoor.app.utils.validation.rule.EmptyRule;
import com.Doctoor.app.utils.validation.rule.TypeRule;
import com.Doctoor.app.utils.validation.util.EditTextHandler;
import com.Doctoor.app.utils.validation.util.ErrorMessageHelper;
import com.Doctoor.app.utils.validation.util.ViewTagHelper;


public class TypeBindings {

    @BindingAdapter(value = {"validateType", "validateTypeMessage", "validateTypeAutoDismiss"}, requireAll = false)
    public static void bindingTypeValidation(TextView view, String fieldTypeText, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }
        TypeRule.FieldType fieldType = getFieldTypeByText(fieldTypeText);
        try {
            String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                    errorMessage, fieldType.errorMessageId);

            String emptyErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                    errorMessage, R.string.error_message_empty_validation);
            ViewTagHelper.appendValue(R.id.validator_rule, view, new EmptyRule(view, true, emptyErrorMessage));

            ViewTagHelper.appendValue(R.id.validator_rule, view, fieldType.instantiate(view, handledErrorMessage));
        } catch (Exception ignored) {
        }
    }

    @NonNull
    private static TypeRule.FieldType getFieldTypeByText(String fieldTypeText) {
        TypeRule.FieldType fieldType = TypeRule.FieldType.None;
        for (TypeRule.FieldType type : TypeRule.FieldType.values()) {
            if (type.toString().equalsIgnoreCase(fieldTypeText)) {
                fieldType = type;
                break;
            }
        }
        return fieldType;
    }

}
