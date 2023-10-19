package com.krishigadmin.android.Basics.UtilityTools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.krishigadmin.android.R;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserAccount {
    @SuppressLint("StaticFieldLeak")
    public static EditText EditTextPointer;
    public static String errorMessage;

    /**
     * Email All Type Validation
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isEmailValid(@NotNull Context context, @NotNull EditText editText) {
        //add your own logic
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            EditTextPointer = editText;
            errorMessage = context.getString(R.string.empty_error);
            return false;
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.invalid_email);
                return false;
            }
        }
    }


    /**
     * Mobile All Type Validation
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isValidPhoneNumber(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            if (editText.getText().toString().length() != 15) {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.enter_ten_digits_number);
                return false;
            } else {
                if (android.util.Patterns.PHONE.matcher(editText.getText()).matches()) {
                    return true;
                } else {
                    EditTextPointer = editText;
                    errorMessage = context.getString(R.string.valid_number);
                    return false;
                }
            }
        }
    }

    /**
     * Check Is Empty
     * @param context  Page Reference
     * @param arg Multiple Edit Text To Check
     * @return  true/false
     */
    public static boolean isEmpty(@NotNull Context context, @NotNull EditText... arg) {
        for (EditText editText : arg) {
            if (editText.getText().toString().trim().length() <= 0) {
                EditTextPointer = editText;
                EditTextPointer.requestFocus();
                errorMessage = context.getString(R.string.empty_error);
                return false;
            }

        }
        return true;
    }

    /**
     * Check Is PasswordMatch
     * @param context  Page Reference
     * @param pass Edit Text To Check
     * @param confirm_pass  Edit Text To Check
     * @return  true/false
     */
    public static boolean isPasswordMatch(@NotNull Context context, @NotNull EditText pass, @NotNull EditText confirm_pass) {
        if (pass.getText() == null || TextUtils.isEmpty(pass.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = pass;
            return false;
        } else if (confirm_pass.getText() == null || TextUtils.isEmpty(confirm_pass.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = confirm_pass;
            return false;
        } else {
            if (!pass.getText().toString().equals(confirm_pass.getText().toString())) {
                EditTextPointer = confirm_pass;
                errorMessage = context.getString(R.string.password_not_match);
                return false;
            }
            return true;
        }
    }

    public static boolean isValidData(@NotNull Context context, @NotNull EditText editText,int msg){
        Pattern p = Pattern.compile("^(\\d*\\.)?\\d+$");
        String s=editText.getText().toString().trim();
        Matcher m = p.matcher(s.trim());
        if (m.matches()) {
            return true;
        } else {
            EditTextPointer = editText;
            errorMessage = context.getString(msg);
            return false;
        }
    }

    /**
     * is Valid Aadhaar Number
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isValidAmount(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^(\\d*\\.)?\\d+$");
            String s=editText.getText().toString().trim();
            Matcher m = p.matcher(s.trim());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.amount_valid);
                return false;
            }

        }
    }

    /**
     * is Valid Aadhaar Number
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isValidAadhaarNumber(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$");
            String s=editText.getText().toString().replaceAll("....","$0 ");
            Matcher m = p.matcher(s.trim());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.aadhaar_valid);
                return false;
            }

        }
    }


    /**
     * is Valid PAN Number
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isValidPAN(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
            Matcher m = p.matcher(editText.getText().toString());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.pan_card_valid);
                return false;
            }

        }
    }

    /**
     * is Valid IFSC Code
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return  true/false
     */
    public static boolean isValidIFSC(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
            Matcher m = p.matcher(editText.getText().toString());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.ifsc_valid);
                return false;
            }

        }
    }

}