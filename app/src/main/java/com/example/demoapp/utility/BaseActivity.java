package com.example.demoapp.utility;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.demoapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of comman
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "BaseFragment";
    public SharedPref mSharedPref;

    // hide keyboard
    public final static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mSharedPref = new SharedPref(this);
        hideKeyboard(this);
        MyApplication.disposeCompositeDisposable();
    }

    // replace fragment

    // call intent for open activity
    public final void callIntent(Class secondActivity) {
        Intent intent = new Intent(this, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }
    // call intent for open activity
    public final void callIntent(Class secondActivity, Bundle bundle) {
        Intent intent = new Intent(this, secondActivity);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    // display toast message
    public final void toastMsg(String msg) {
        try {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // call intent for open activity with finish current activity
    public final void callIntent(Class secondActivity, String finish) {
        Intent intent = new Intent(this, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    // call intent with shared element
    public final void callIntentWithSharedElementWithResult(Context context, Class className, int requestCode, Pair<View, String>... pair) {
        Intent intent = new Intent(context, className);
        ActivityOptions options;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
            ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, options.toBundle());
        } else {
            ((Activity) context).startActivityForResult(intent, requestCode);
        }
    }
    /**
     * To add some Hour or time in date
     *
     * @param myTime
     * @param number
     * @param isHour
     * @return
     */
    public final String addHourOrMinutes(String myTime, String number, boolean isHour) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            if (isHour) {
                cal.add(Calendar.HOUR, Integer.parseInt(number));
            } else {
                cal.add(Calendar.MINUTE, Integer.parseInt(number));
            }
            return df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }



}
