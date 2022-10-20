package com.Doctoor.app.utils.validation.rule;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.Doctoor.app.model.Country;
import com.Doctoor.app.utils.PhoneNumberUtils;
import com.Doctoor.app.utils.validation.util.EditTextHandler;

import java.util.List;


public class PhoneTypeRule extends Rule<TextView, String> implements TextWatcher {
    private String code;
    TextView view;
    private Country mCountry = new Country("PK", "Pakistan");

    public PhoneTypeRule(TextView view, String value, String errorMessage) {
        super(view, value, errorMessage);
        this.view = view;
        view.addTextChangedListener(this);
    }

    @Override
    protected boolean isValid(TextView view) {
        return view.getText().toString().matches(value) && PhoneNumberUtils.isValidPhoneNumber(view.getEditableText(), mCountry.getCode());
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String rawNumber = editable.toString();
        if (rawNumber.isEmpty()) {
            mCountry = new Country("PK", "Pakistan");
        } else {
            if (rawNumber.startsWith("00")) {
                rawNumber = rawNumber.replaceFirst("00", "+");
                view.removeTextChangedListener(this);
                view.setText(rawNumber);
                view.addTextChangedListener(this);
                ((EditText) view).setSelection(rawNumber.length());
            }
            Log.d("rawNumber>>", rawNumber.substring(1));
        }
    }

    private void selectCountry(String dialCode) {
        List<Country> list = PhoneNumberUtils.getAllCountries();
        for (int i = 0; i < list.size(); i++) {
            Country country = list.get(i);
            if (country.getCode().equals(dialCode)) {
                mCountry = country;
                break;
            }
        }
    }
}
