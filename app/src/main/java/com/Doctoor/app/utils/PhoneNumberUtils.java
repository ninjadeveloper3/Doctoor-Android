package com.Doctoor.app.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.Doctoor.app.DoctoorApp;
import com.Doctoor.app.R;
import com.Doctoor.app.model.Country;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhoneNumberUtils {
    public static String getNationalNumber(Editable editable, int code, String codeString) {


        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String tempNo = editable.toString().trim();
        String number = null;
        try {
            Phonenumber.PhoneNumber ph = phoneUtil.parse(tempNo, codeString);
            ph.setCountryCode(code);
            number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber();
            return number;
        } catch (NumberParseException e) {
        }
        return number;
    }

    public static int getCodeFromPhone(String pNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(pNumber, "");
            int countryCode = numberProto.getCountryCode();
            return countryCode;
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return 0;
    }

    public static String getInterNationalNumber(Editable editable, String codeString) {


        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String tempNo = editable.toString().trim();
        String number = null;
        try {
            Phonenumber.PhoneNumber ph = phoneUtil.parse(tempNo, codeString);
            ph.setCountryCode(ph.getCountryCode());
            number = phoneUtil.format(ph, PhoneNumberUtil.PhoneNumberFormat.E164);
            //number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber();
            return number;
        } catch (NumberParseException e) {
        }
        return number;
    }

    public static String getNationalNumber(String text, int code, String codeString) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String tempNo = text.toString().trim();
        String number = null;
        try {
            Phonenumber.PhoneNumber ph = phoneUtil.parse(tempNo, codeString);
            ph.setCountryCode(code);

            number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber();
            return number;
        } catch (NumberParseException e) {


        }
        return number;
    }

    public static String getNationalNumber(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber ph = phoneUtil.parse(number, "");
            ph.getNationalNumber();
            return "" + ph.getNationalNumber();
        } catch (NumberParseException e) {

        }
        return " ";
    }

    public static boolean isValidPhoneNumber(Editable no, String code) {
        if (TextUtils.isEmpty(no)) {
            return false;
        }
        String number = no.toString().trim();
        return isValidPhoneNumber(number, code);
    }

    public static boolean isValidPhoneNumber(String number, String code) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        boolean isValid = false;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumberUtil.PhoneNumberType isMobile = null;
        try {
            Phonenumber.PhoneNumber ph = phoneUtil.parse(number, code);
            ph.setCountryCode(phoneUtil.getCountryCodeForRegion(code));
            isValid = phoneUtil.isValidNumber(ph) && !number.startsWith("0");
            isMobile = phoneUtil.getNumberType(ph);
            return isValid && (PhoneNumberUtil.PhoneNumberType.MOBILE == isMobile ||
                    PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE == isMobile);
        } catch (NumberParseException e) {
            return isValid;
        }
    }

    public static String parseContact(String contact, String code) {
        Phonenumber.PhoneNumber phoneNumber = null;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String finalNumber = null;
        // String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countrycode));
        boolean isValid = false;
        PhoneNumberUtil.PhoneNumberType isMobile = null;
        try {
            phoneNumber = phoneNumberUtil.parse(contact, code);
            isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            isMobile = phoneNumberUtil.getNumberType(phoneNumber);

        } catch (NumberParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if (isValid
                && (PhoneNumberUtil.PhoneNumberType.MOBILE == isMobile ||
                PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE == isMobile)) {
            finalNumber = phoneNumberUtil.format(phoneNumber,
                    PhoneNumberUtil.PhoneNumberFormat.E164).substring(1);
        }

        return finalNumber;
    }


    public static int getCountryCodeForRegion(String code) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        return phoneUtil.getCountryCodeForRegion(code.toUpperCase());

    }

    public static String getCountryCodeForRegion(int code) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        return phoneUtil.getRegionCodeForCountryCode(code);

    }

    /**
     * R.string.countries is a json string which is Base64 encoded to avoid
     * special characters in XML. It's Base64 decoded here to get original json.
     *
     * @param context
     * @return
     * @throws java.io.IOException
     */
    private static String readFileAsString()
            throws java.io.IOException {
        String base64 = DoctoorApp.Companion.string(R.string.countries);
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data, "UTF-8");
    }

    /**
     * Get all countries with code and name from res/raw/countries.json
     *
     * @return
     */
    public static List<Country> getAllCountries() {
        List<Country> allCountriesList = new ArrayList<Country>();
        try {

            String allCountriesString = readFileAsString();

            JSONObject jsonObject = new JSONObject(allCountriesString);
            Iterator<?> keys = jsonObject.keys();

            // Add the data to all countries list
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Country country = new Country(key, jsonObject.getString(key));
                allCountriesList.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCountriesList;
    }
}
