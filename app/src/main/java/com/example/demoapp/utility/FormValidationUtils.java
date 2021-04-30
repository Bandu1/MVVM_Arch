package com.example.demoapp.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of validation
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */

public class FormValidationUtils {
    private static boolean log_enable = true;
    private HashMap<String, HashMap<String, Object>> arrFieldData;

    private ArrayList<HashMap<String, Object>> arrErrorList;
    private Context _c;

    public FormValidationUtils() {
    }
    public FormValidationUtils(Context c) {
        this._c = c;
        this.arrFieldData = new HashMap<>();
        this.arrErrorList = new ArrayList<>();
    }

    // show toast message
    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    // Display log
    private void DisplayLog(String tag, String msg) {
        if (this.log_enable) {
            Logs.ERROR(msg);
        }
    }

    // set validation rules
    public void set_rules(View viewField, String slabe, String sRule, String[] arrError, String sErrorDisplayType) {
        EditText edtField = null;
        Spinner spinnerField = null;
        String fieldValue = "";
        if (viewField == null || viewField.equals("")) {
            return;
        } else {
            if (viewField instanceof EditText) {
                edtField = (EditText) viewField;
            }
            if (viewField instanceof Spinner) {
                spinnerField = (Spinner) viewField;
            }
        }
        if (sRule == null || sRule.equals("")) {
            return;
        }
        if (slabe == null || slabe.equals("")) {
            if (viewField instanceof EditText && edtField.getHint() != null && !edtField.getHint().equals("")) {
                slabe = edtField.getHint().toString();
            }
        }
        String sField = String.valueOf(viewField.getId());
        String[] arrRule = sRule.split("\\|(?![^\\[]*\\])");
        HashMap<String, Object> hmKeys = new HashMap<>();
        hmKeys.put("field", sField);
        hmKeys.put("edtfield", viewField);
        hmKeys.put("fieldValue", getFieldValue(viewField));
        hmKeys.put("label", slabe);
        hmKeys.put("rules", arrRule);
        hmKeys.put("errors", arrError);
        hmKeys.put("sErrorDisplayType", sErrorDisplayType);
        hmKeys.put("is_array", false);
        hmKeys.put("error", "");
        this.arrFieldData.put(sField, hmKeys);
    }

    // set validation rules
    public boolean set_rules(boolean isCheck, View viewField, String slabe, String sRule, String[] arrError, String sErrorDisplayType) {
        if (this.arrFieldData.size() > 0) {
            this.arrFieldData.clear();
        }
        EditText edtField = null;
        Spinner spinnerField = null;
        String fieldValue = "";
        if (viewField == null || viewField.equals("")) {
            return false;
        } else {
            if (viewField instanceof EditText) {
                edtField = (EditText) viewField;
            }
            if (viewField instanceof Spinner) {
                spinnerField = (Spinner) viewField;
            }
        }
        if (sRule == null || sRule.equals("")) {
            return false;
        }
        if (slabe == null || slabe.equals("")) {
            if (viewField instanceof EditText && edtField.getHint() != null && !edtField.getHint().equals("")) {
                slabe = edtField.getHint().toString();
            }
        }
        String sField = String.valueOf(viewField.getId());
        String[] arrRule = sRule.split("\\|(?![^\\[]*\\])");
        HashMap<String, Object> hmKeys = new HashMap<>();
        hmKeys.put("field", sField);
        hmKeys.put("edtfield", viewField);
        hmKeys.put("fieldValue", getFieldValue(viewField));
        hmKeys.put("label", slabe);
        hmKeys.put("rules", arrRule);
        hmKeys.put("errors", arrError);
        hmKeys.put("sErrorDisplayType", sErrorDisplayType);
        hmKeys.put("is_array", false);
        hmKeys.put("error", "");
        this.arrFieldData.put(sField, hmKeys);
        return run();
    }
    // check all validation match
    public boolean run() {
        DisplayLog("run :this.arrFieldData.size() ", "" + this.arrFieldData.size());
        if (this.arrFieldData != null && this.arrFieldData.size() > 0) {

            Iterator it = this.arrFieldData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                DisplayLog("run key,value ", pair.getKey() + " = " + pair.getValue());

                HashMap<String, Object> hmKeys = (HashMap<String, Object>) pair.getValue();
                String[] rules = (String[]) hmKeys.get("rules");
                DisplayLog("run rules.length ", rules.length + "");
                String methodName = "";
                if (rules != null && rules.length > 0) {
                    for (int i = 0; i < rules.length; i++) {
                        DisplayLog("run rules[i] ", "" + rules[i]);
                        View viewtyp = (View) hmKeys.get("edtfield");
                        methodName = rules[i];

                        if (methodName.trim().equals("required")) {
                            required(viewtyp, i);
                        }
                        if (methodName.trim().equals("emailCheck")) {
                            emailCheck(viewtyp, i);
                        }
                        if (methodName.trim().equals("validEmail")) {
                            validEmail(viewtyp, i);
                        }
                        if (methodName.trim().equals("validName")) {
                            validName(viewtyp, i);
                        }
                        if (methodName.trim().equals("validDate")) {
                            validDate(viewtyp, i);
                        }
                        if (methodName.trim().equals("validname")) {
                            validname(viewtyp, i);
                        }

                        if (methodName.trim().equals("mobileNumber")) {
                            mobileNumber(viewtyp, i);
                        }
                        if (methodName.trim().equals("securePassword")) {
                            securePassword(viewtyp, i);
                        }
                        if (methodName.trim().equals("validText")) {
                            validText(viewtyp, i);
                        }
                        if (methodName.trim().equals("validSpinner")) {
                            validSpinner(viewtyp, i);
                        }
                        if (methodName.trim().equals("validHrs")) {
                            validHrs(viewtyp, i);
                        }
                        if (methodName.trim().equals("valiTime")) {
                            validTime(viewtyp, i);
                        }
                        String[] numb = methodName.replace("]", "").split("\\[");
                        methodName = numb[0];
                        if (numb != null && numb.length > 1) {
                            if (methodName.trim().equals("min_length") || methodName.trim().equals("max_length") || methodName.trim().equals("between_length")) {
                                if (methodName.trim().equals("min_length")) {
                                    numberInBetween(viewtyp, i, Integer.parseInt(numb[1]), 0);
                                }
                                if (methodName.trim().equals("max_length")) {
                                    numberInBetween(viewtyp, i, 0, Integer.parseInt(numb[1]));
                                }
                                if (methodName.trim().equals("between_length")) {
                                    String[] betnum = numb[1].split("\\,");
                                    numberInBetween(viewtyp, i, Integer.parseInt(betnum[0]), Integer.parseInt(betnum[1]));
                                }
                            }
                            if (methodName.trim().equals("comparePassword")) {
                                comparePassword(viewtyp, i, numb[1]);

                            }
                        }

                    }
                }
                it.remove();
            }
        }
        if (!setDisplayError()) {
            return false;
        }
        return true;
    }
    // check required filed validation
    private boolean required(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (viewField != null
                && ((viewField instanceof RadioGroup && ((RadioGroup) viewField).getCheckedRadioButtonId() == -1)
                || (viewField instanceof RadioButton && !((RadioButton) viewField).isChecked())
                || (viewField instanceof CheckBox && !((CheckBox) viewField).isChecked()))
        ) {
            sErrorMessage = (serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "" + (String) hmKeys.get("label") : "Please select at least one value";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
        } else if (sFieldValue == null || sFieldValue.equals("")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Empty" : "Your value is Empty";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
        }

        return false;
    }
    // check email validation
    private boolean emailCheck(View viewField, int iErrorIndex) {
        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());
        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);
        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (!sFieldValue.trim().equalsIgnoreCase("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(sFieldValue).matches()) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Invalid Email Address";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
        }
        return false;
    }
    /**
     * validation for proper title between a-z and 0-9
     **/
    private boolean validName(View viewField, int iErrorIndex) {
        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());
        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);
        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^[a-zA-Z0-9 ]*$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Accept Alphabets And Numbers Only.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
            return false;
        }
        return true;
    }
    /**
     * validation for proper email
     **/
    private boolean validEmail(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());
        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);
        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");
        String emailPattern = "[a-zA-Z0-9._ ]+@[a-z]+\\.+[a-z]+";
        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches(emailPattern)) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Accept Alphabets And Numbers Only.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
            return false;
        }
        return true;
    }


    /**
     * validation for proper Date Validation  dd-mm-yyyy or dd/mm/yyyy
     **/
    private boolean validDate(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");
        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^\\d{2}/\\d{2}/\\d{4}$"))

        //if (!sFieldValue.matches("^\\d{2}/\\d{2}/\\d{4}$"))

        {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Some special character not allowed.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        }
        return true;
    }

    /**
     * validation for check valid time
     **/
    private boolean validTime(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");
        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"))

        //if (!sFieldValue.matches("^\\d{2}/\\d{2}/\\d{4}$"))

        {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Some special character not allowed.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        }
        return true;
    }

    /**
     * validation for proper name between a-z
     **/
    private boolean validname(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("[a-zA-Z_ ]+")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Accept special character And Numbers Only.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
            return false;
        }

        return true;

    }
    /**
     * validation for check hours
     **/
    private boolean validHrs(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (!sFieldValue.matches("^[a-zA-Z0-9]+$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Accept Alphabets And Numbers Only.";
            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
            return false;
        }
        return true;

    }


    /**
     * validation for proper number between strMinute-value and max-value
     **/
    private boolean numberInBetween(View viewField, int iErrorIndex, int MinLen, int MaxLen) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run validSignNumber ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^[0-9]*$")) {

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", "Accept Number Only.");
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (sFieldValue.toString().length() < MinLen) {

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", "Minimum length " + MinLen);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (MaxLen > 0 && sFieldValue.toString().length() > MaxLen) {

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", "Maximum length " + MaxLen);
            this.arrErrorList.add(hmErrorKeys);

            return false;
        }
        return true;

    }

    /**
     * validation for proper number between strMinute-value and max-value
     **/
    private boolean mobileNumber(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run validSignNumber ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^[0-9]*$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Accept Number Only.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (!sFieldValue.matches("\\d{10}")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Please enter proper number.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (sFieldValue.equalsIgnoreCase("0000000000") || !sFieldValue.matches("[1-9][0-9]{9}")) {
            sErrorMessage = "Please enter proper mobile number.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        }
        return true;

    }

    /* *//**
     * validation for proper password with special permission
     **//*
    public boolean securePassword(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run validSignNumber ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (sFieldValue.length() < 8) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " must be at least 8 characters long" : "Your password must be at least 8 characters long";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (!sFieldValue.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Not Included at least 1 Uppercase and 1 Special character and 1 Number, ex. : \"'Example@123'\"" : "Please Enter Password Including 1 Uppercase and 1 Special character and 1 Number must be there, Like : \"'Example@123'\"";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;
        }

        return true;

    }*/

    /**
     * validation for proper password with special permission
     **/
    private boolean securePassword(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run validSignNumber ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (sFieldValue.length() < 8) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " must be at least 8 characters long" : "Your password must be at least 8 characters long";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;

        } else if (!sFieldValue.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!)(&+=*])(?=\\S+$).{4,}$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " must include at least 1 Uppercase, 1 Special character and 1 Number, ex. : \"'Example@123'\"" : "Please Enter Password Including 1 Uppercase, 1 Special character and 1 Number must be there, Like : \"'Example@123'\"";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;
        }

        return true;

    }


    /**
     * validation for proper password comparing with text
     **/
    private boolean comparePassword(View viewField, int iErrorIndex, String sCompareString) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run validSignNumber ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.equals(sCompareString)) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is not match" : "Your data is not match.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);
            return false;
        }
        return true;

    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    /**
     * validation for proper Spinner valid
     **/
    private boolean validSpinner(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (sFieldValue.trim().equalsIgnoreCase("")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Some special character not allowed.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;
        }
        return true;
    }


    private HashMap<String, Object> detDefaultError(View viewField) {

        String sField = String.valueOf(viewField.getId());

        DisplayLog("run detDefaultError ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        DisplayLog("run detDefaultError ", "sErrorDisplayType :: " + (String) hmKeys.get("sErrorDisplayType"));

        HashMap<String, Object> hmErrorKeys = new HashMap<>();
        hmErrorKeys.put("field", (View) viewField);
        hmErrorKeys.put("fieldId", sField);
        hmErrorKeys.put("fieldValue", getFieldValue(viewField));
        hmErrorKeys.put("label", (String) hmKeys.get("label"));
        hmErrorKeys.put("errorsStatus", false);
        hmErrorKeys.put("errorDisplayType", (String) hmKeys.get("sErrorDisplayType"));
        hmErrorKeys.put("error", "");

//        this.arrErrorList.add(hmErrorKeys);

        return hmErrorKeys;
    }

    private String getFieldValue(View viewField) {

        String fieldValue = "";
        if (viewField instanceof TextView) {
            TextView tvField = (TextView) viewField;
            fieldValue = tvField.getText().toString();
        }
        if (viewField instanceof EditText) {
            EditText edtField = (EditText) viewField;
            fieldValue = edtField.getText().toString().trim();
        }
        if (viewField instanceof Spinner) {
            Spinner spinnerField = (Spinner) viewField;
            fieldValue = spinnerField.getSelectedItem().toString();
            if (spinnerField.getSelectedItemPosition() <= 0) {
                fieldValue = "";
            }
        }
        if (viewField instanceof RadioGroup) {
            RadioGroup radioGroupField = (RadioGroup) viewField;
            RadioButton radioButtonField = (RadioButton) viewField.findViewById(radioGroupField.getCheckedRadioButtonId());
            if (radioButtonField != null) {
                fieldValue = radioButtonField.getText().toString();
            }
        }
        if (viewField instanceof RadioButton) {
            RadioButton radioButtonField = (RadioButton) viewField;
            fieldValue = radioButtonField.getText().toString();
        }
        if (viewField instanceof CheckBox) {
            CheckBox checkButtonField = (CheckBox) viewField;
            fieldValue = checkButtonField.getText().toString();
        }
        return fieldValue;
    }

    private void setViewError(final View viewField, String errorMsg) {
        errorMsg = errorMsg.trim();
        if (viewField instanceof EditText) {
            ((EditText) viewField).setError(errorMsg);
        }
        if (viewField instanceof Spinner) {
            try {
                ((TextView) ((Spinner) viewField).getSelectedView()).setError(errorMsg);
            } catch (Exception e) {
                showToast(_c, errorMsg);
            }
        }
        if (viewField instanceof RadioGroup) {
            showToast(_c, errorMsg);
        }
        if (viewField instanceof RadioButton) {
            ((RadioButton) viewField).setError(errorMsg);
            ((RadioButton) viewField).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RadioButton) viewField).setError(null);
                }
            });
        }
        if (viewField instanceof CheckBox) {
            ((CheckBox) viewField).setError(errorMsg);
            ((CheckBox) viewField).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CheckBox) viewField).setError(null);
                }
            });
        }
    }

    private boolean setDisplayError() {
        String sErrorDisplayType = null, sErrorMessage = "", sLastField = "";
        EditText field;
        boolean bReturnStatus = true;
        if (this.arrErrorList != null && this.arrErrorList.size() > 0) {
            for (int i = 0; i < this.arrErrorList.size(); i++) {
                DisplayLog("bReturnStatus ", "*****\n* bReturnStatus :: " + bReturnStatus + " * \n ******");
                if (bReturnStatus) {
                    bReturnStatus = false;
                }
                HashMap<String, Object> hmKeys = this.arrErrorList.get(i);
                sErrorDisplayType = (String) hmKeys.get("errorDisplayType");
//                sErrorMessage = (String) hmKeys.get("error");
//                field = (EditText) hmKeys.get("field");
                DisplayLog("run setDisplayError ", "sErrorDisplayType :: " + sErrorDisplayType);
                if (sErrorDisplayType != null) {
                    if (sErrorDisplayType.equals("toast")) {
                        showToast(_c, hmKeys.get("error").toString().trim());
                    } else if (sErrorDisplayType.equals("seterror")) {
//                        showToast(_c, sErrorMessage);
                        sErrorMessage = ((sLastField.equals(hmKeys.get("error").toString())) ? "\n" : "") + hmKeys.get("error").toString();
//                        field.setError(sErrorMessage);
                        setViewError((View) hmKeys.get("field"), sErrorMessage);
                    } else if (sErrorDisplayType.equals("alert")) {
//                        showToast(_c, sErrorMessage);
                        sErrorMessage += hmKeys.get("error").toString() + ",\n";
//                        sErrorMessage += sErrorMessage + "</br>";
                    }
                    sLastField = hmKeys.get("error").toString().trim();
                }
            }
            if (sErrorDisplayType != null && sErrorDisplayType.equals("alert")) {
//                        showToast(_c, sErrorMessage);
                DisplayLog("", sErrorMessage);
                AlertDialogManager.showAlertDialog(_c, "Ok", "Validate", sErrorMessage, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
//                field.setError(sErrorMessage);
            }

        }
        return bReturnStatus;

    }


    /**
     * validation for proper title between a-z , 0-9, ., -,:,,
     **/
    private boolean validText(View viewField, int iErrorIndex) {

        String sField, sFieldValue, sErrorMessage;
        sField = String.valueOf(viewField.getId());

        DisplayLog("run required ", "sField :: " + sField);
        HashMap<String, Object> hmKeys = this.arrFieldData.get(sField);

        sFieldValue = (String) hmKeys.get("fieldValue");
        String[] serrors = (String[]) hmKeys.get("errors");

        if (required(viewField, iErrorIndex)) {
            return true;
        } else if (!sFieldValue.matches("^[A-Za-z0-9\\\\s,\\\\.\\\\-\\\\:]*$")) {
            sErrorMessage = (serrors.length > iErrorIndex && serrors[iErrorIndex] != null && !serrors[iErrorIndex].equals("")) ? serrors[iErrorIndex] : (hmKeys.get("label") != null && !hmKeys.get("label").toString().equals("")) ? "Your " + (String) hmKeys.get("label") + " is Invalid" : "Some special character not allowed.";

            HashMap<String, Object> hmErrorKeys = detDefaultError(viewField);
            hmErrorKeys.put("label", (String) hmKeys.get("label"));
            hmErrorKeys.put("errorsStatus", true);
            hmErrorKeys.put("error", sErrorMessage);
            this.arrErrorList.add(hmErrorKeys);

            return false;
        }

        return true;

    }


    public static class AlertDialogManager {

        private static void showAlertDialog(final Context context, final String positiveButtonTitle, final String title,
                                            final String message, final OnClickListener listener) {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(Html.fromHtml("<font color='#FF0000'>" + message + "</font>"));
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            alertDialog.show();
        }

        private static void showInfoDialog(final Context context, final String positiveButtonTitle, final String title,
                                           final String message, final OnClickListener listener) {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(Html.fromHtml("<font color='#009933'>" + message + "</font>"));
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            alertDialog.show();
        }

        private static void showInfoDialog(final Context context, final String positiveButtonTitle,
                                           final String negativeButtonTitle, final String title, final String message, final OnClickListener listener) {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(Html.fromHtml("<font color='#009933'>" + message + "</font>"));
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            alertDialog.show();
        }

        /**
         * Function to display simple Alert Dialog
         *
         * @param context  - application context
         * @param title    - alert dialog title
         * @param message  - alert message
         * @param listener - success/failure (used to set icon) - pass null if you
         *                 don't want icon
         */
        private static void showInfoDialog(final Context context, final String positiveButtonTitle,
                                           final String negativeButtonTitle, final String neutralButtonTitle, final String title, final String message,
                                           final OnClickListener listener) {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            // Setting Dialog Title
            alertDialog.setTitle(title);
            // Setting Dialog Message
            alertDialog.setMessage(Html.fromHtml("<font color='#009933'>" + message + "</font>"));
            // Setting alert dialog icon
//            alertDialog.setIcon(R.drawable.ic_success);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            // Setting BUTTON_POSITIVE
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            // Setting BUTTON_NEGATIVE
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            // Setting BUTTON_NEUTRAL
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, neutralButtonTitle,
                    new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onClick(dialog, which);
                            }
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }
    }

}