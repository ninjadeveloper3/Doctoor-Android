package com.Doctoor.app.widget.validatedtextinputlayout;


import androidx.annotation.NonNull;


/**
 * Validator to set input field as required.
 *
 * @see BaseValidator
 */
public class RequiredValidator extends BaseValidator {

    /**
     * @param pErrorMessage error message to display if validation fails
     */
    public RequiredValidator(String pErrorMessage) {
        super(pErrorMessage);
    }

    /**
     * @param pErrorMessage error message to display if validation fails
     * @param pCallback     callback for validation event
     */
    public RequiredValidator(@NonNull String pErrorMessage, ValidationCallback pCallback) {
        super(pErrorMessage, pCallback);
    }

    /**
     * Check if the associated {@link ValidatedTextInputLayout} is empty or not.
     *
     * @param pText value associated with the input field
     * @return validity of the field
     */
    @Override
    public boolean isValid(String pText) {
        return !pText.isEmpty();
    }
}
