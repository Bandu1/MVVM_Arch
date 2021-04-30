package com.example.demoapp.utility;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.demoapp.R;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of comman method
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class AppUtility {
    public static ProgressDialog progressDialog;
    private Activity mActivity;
    private ProgressHUD mProgressHUD;
    private static Toast toast;
    public AppUtility(Activity activity) {
        this.mActivity = activity;
    }
    //Todo: Show progressbar dialog
    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    //Todo: Hide Progressbar dialog
    public static void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*Date Formate and hours:dd MMM yyyy  HH:mm aa */
    public static String convertTimestampToDateAndTime(long milliseconds) {
        DateTime date = new DateTime(milliseconds);
        DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("dd MMM yyyy  HH:mm aa");
        return date.toString(simpleDateFormat);
    }
    /*Date Formate and hours:yyyy-MM-dd HH:mm:ss" */
    public static String parseDateToddMMyyyy(String date)
    {
        Date oneWayTripDate = null;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy  HH:mm aa");
        try {
            oneWayTripDate = input.parse(date);  // parse input
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(oneWayTripDate);
    }
/*password check valid*/
    private static boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!)(&+=*])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

   /* //^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!)(&+=*])(?=\S+$).{4,}$*/

    public static String isPasswordValid(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            return context.getResources().getString(R.string.error_please_enter_password);
        } else if (password.length() < 8) {
            return context.getResources().getString(R.string.error_please_enter_password_more_than_8);
        } else if (!AppUtility.isValidPassword(password)) {
            return context.getResources().getString(R.string.error_please_enter_valid_password);
        } else {
            return "";
        }
    }
/*check the password valid or not*/
    public static String isLoginPasswordValid(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            return context.getResources().getString(R.string.error_please_enter_password);
        } /*else if (password.length() < 8) {
            return context.getResources().getString(R.string.error_please_enter_password_more_than_8);
        }*/ else {
            return "";
        }
    }
/*Confirm password*/
    public static String isConfirmPasswordValid(Context context, String password, String strRePassword) {
        if (TextUtils.isEmpty(password)) {
            return context.getResources().getString(R.string.error_please_enter_password);
        } else if (password.length() < 8) {
            return context.getResources().getString(R.string.error_please_enter_password_more_than_8);
        } else if (!password.equals(strRePassword)) {
            return context.getResources().getString(R.string.password_confrimpassword_not_match);
        } else {
            return "";
        }
    }
/*check the conform password valid or not*/
    public static String isConfirmPasswordValid(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            return context.getResources().getString(R.string.error_please_enter_confirm_password);
        } else if (password.length() < 8) {
            return context.getResources().getString(R.string.error_please_enter_password_more_than_8);
        } else if (!AppUtility.isValidPassword(password)) {
            return context.getResources().getString(R.string.error_please_enter_valid_password);
        } else {
            return "";
        }
    }

    public static String isConfirmPasswordEmpty(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            return context.getResources().getString(R.string.error_please_enter_confirm_password);
        } else {
            return "";
        }
    }

    public static String parseDate(String date) {
        if (date != null) {
            DateTime dt_pickup = new DateTime(Long.parseLong(date));
            DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
            return dt_pickup.toString(simpleDateFormat);
        }
        return null;
    }

    public static Timestamp convertStringToTimestamp(String str_date) {
        DateTimeFormatter e = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime date = e.parseDateTime(str_date);
        Timestamp timeStampDate = new Timestamp(date.getMillis());
        return timeStampDate;
    }

    public static String convertTimestampToTimeOnly(Timestamp timestamp) {
        DateTime date = new DateTime(timestamp.getTime());
        DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("hh:mm a");
        return date.toString(simpleDateFormat);
    }
    public static void callIntentWithSharedElement(Context context, Class className, Pair<View, String>... pair) {
        Intent intent = new Intent(context, className);
        ActivityOptions options = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
            ActivityCompat.startActivity(context, intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }
    public static String getGenderText(int gender) {
        String strResult = "";
        if (gender == 1) {
            strResult = "M";
        } else if (gender == 2) {
            strResult = "F";
        } else if (gender == 3) {
            strResult = "O";
        }
        return strResult;
    }

    public static void checkPermissionCameraGallery(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.IntentKeys.INTENT_KEY_CAMERA_GALLERY_REQUEST_CODE);
    }

    public static void checkPermissionStorage(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.IntentKeys.INTENT_KEY_STORAGE_REQUEST_CODE);
    }

    public static void showShortToast(Context context, String strMessage) {
        Toast.makeText(context, "" + strMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String strMessage) {
        Toast.makeText(context, "" + strMessage, Toast.LENGTH_LONG).show();
    }


    public static void showShortToast(String strMessage) {
        if (MyApplication.getmContext() != null) {
            Toast.makeText(MyApplication.getmContext(), "" + strMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToast(String strMessage) {
        if (MyApplication.getmContext() != null) {
            Toast.makeText(MyApplication.getmContext(), "" + strMessage, Toast.LENGTH_LONG).show();
        }
    }

    public static void checkPermissionContact(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_CONTACTS}, Constants.IntentKeys.INTENT_KEY_CONTACT_PERMISSION_REQUEST);
    }

    public static void checkPermissionLocation(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.IntentKeys.INTENT_KEY_LOCATION_PERMISSION_REQUEST);
    }

    public static String phoeNumberWithOutCountryCode(String phoneNumberWithCountryCode) {
        String mobileNO = "";
        if (phoneNumberWithCountryCode != null && !phoneNumberWithCountryCode.equalsIgnoreCase("")) {
            mobileNO = phoneNumberWithCountryCode;
            mobileNO = mobileNO.replaceAll("\\s+", "");
            mobileNO = mobileNO.replaceAll("[^0-9]", "");
            if (mobileNO.length() >= 10) {
                mobileNO = mobileNO.substring(mobileNO.length() - 10);
                return mobileNO;
            }
        }
        return mobileNO;
    }

    public static String isValidPhoneNumber(Context context, String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return getResourceString(context, R.string.error_empty_mobile_no);
        } else if (!mobile.matches("^[0-9]*$") || mobile.equalsIgnoreCase("0000000000")) {
            return getResourceString(context, R.string.error_select_valid_mobile_no);
        } else if (!mobile.matches("\\d{10}")) {
            return getResourceString(context, R.string.error_select_10_digit_mobile_no);
        } else if (!mobile.matches("[1-9][0-9]{9}")) {
            return getResourceString(context, R.string.error_enter_proper_mobile_number);
        } else {
            return "";
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static String getResourceString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /* Show Progress */
    public void ShowProgress(final String mStringTitle) {
        if (mActivity != null) {
            if (mProgressHUD != null && mProgressHUD.isShowing()) {

            } else {
                mProgressHUD = ProgressHUD.showDialog(mActivity, mStringTitle, true, false, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
            }
        }
    }
    /* Hide Progress */
    public void HideProgress() {
        if (mActivity != null) {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }
    public static long Daybetween(String date1, String date2) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parseDateTime(date1);
            Date2 = sdf.parseDateTime(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Date2.getMillis() - Date1.getMillis()) / (24 * 60 * 60 * 1000) + 1;
    }
    public static <T> T convertJsonToClass(String strResponse, Class<T> type) {
        Gson gson = new Gson();
        T classModel = gson.fromJson(strResponse, type);
        return classModel;
    }
    public static String getFirstCharString(String strValue) {
        String strResult = "";
        try {
            String[] strValueArray = strValue.split(" ");
            for (int i = 0; i < strValueArray.length; i++) {
                if (i < 2) {
                    strResult = strResult + strValueArray[i].charAt(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }

    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2));
        }
        return capMatcher.appendTail(capBuffer).toString();
    }
    public static void showToast(Context context, String message) {
        try {
            toast.getView().isShown();
            toast.setText(message);
        } catch (Exception e) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
