package com.example.demoapp.module.login.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivityLoginBinding;
import com.example.demoapp.module.login.activity.LoginActivity;
import com.example.demoapp.module.login.model.Login;
import com.example.demoapp.module.navigation.activity.NavigationActivity;
import com.example.demoapp.repository.ToRepository;
import com.example.demoapp.utility.AppUtility;
import com.example.demoapp.utility.Constants;
import com.example.demoapp.utility.FormValidationUtils;
import com.example.demoapp.utility.MyApplication;
import com.example.demoapp.utility.MyProgressDialog;
import com.example.demoapp.utility.SharedPref;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : This class is used for business logic of Login screen
 * Revisions        : 1 - XYZ     30-04-2019
 *			         Change – Add in add()
 *
 *                    2 - PQR     1-05-2019
 *                    Change – Modify  Method()
 *
 * Additional Comments -
 */
public class LoginViewModel extends AndroidViewModel implements View.OnClickListener, TextWatcher {
    private ActivityLoginBinding loginBinding;
    private LoginActivity activity;
    private MutableLiveData<HashMap<String, String>> loginResponseLiveData = new MutableLiveData<>();
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private long mLastClickTime;
    private String[] permissionsRequired = new String[]{
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    private SharedPref mSharedPref;
    private LoginViewModel(@NonNull final Application application, LoginActivity activity, ActivityLoginBinding loginBinding) {
        super(application);
        this.activity = activity;
        this.mSharedPref = new SharedPref(activity);
        this.loginBinding = loginBinding;
        handlePermission();
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        loginBinding.setLoginViewModel(this);
        loginBinding.actLoginBtnLogin.setOnClickListener(this);
        loginBinding.toolbarLay.imgBack.setOnClickListener(this);
        loginBinding.actLoginEtPassword.addTextChangedListener(this);
        activity.editTextFun(loginBinding.actLoginEtMobile, loginBinding.actLoginTilMobile);
        activity.editTextFun(loginBinding.actLoginEtPassword, loginBinding.actLoginTilPassword);
        loginBinding.actLoginEtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (loginBinding.actLoginEtPassword.getError() != null) {
                        loginBinding.actLoginEtPassword.setError(null);
                        loginBinding.actLoginTilPassword.setPasswordVisibilityToggleEnabled(true);
                    }
                }
                return false;
            }
        });
        loginBinding.actLoginEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    loginBinding.actLoginTilPassword.setHintEnabled(true);
                    loginBinding.actLoginTilPassword.setBackgroundResource(R.drawable.otp_background_shape_black);
                } else {
                    if (loginBinding.actLoginEtPassword.getText().toString().trim().length() == 0) {
                        loginBinding.actLoginTilPassword.setHintEnabled(false);
                        loginBinding.actLoginTilPassword.setBackgroundResource(R.drawable.login_rectangleshape);
                    } else {
                        loginBinding.actLoginTilPassword.setHintEnabled(true);
                        loginBinding.actLoginTilPassword.setBackgroundResource(R.drawable.otp_background_shape);
                    }
                }
            }
        });
        loginBinding.actLoginEtMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    loginBinding.actLoginTilMobile.setHintEnabled(true);
                    loginBinding.actLoginTilMobile.setBackgroundResource(R.drawable.otp_background_shape_black);
                } else {
                    if (loginBinding.actLoginEtMobile.getText().toString().trim().length() == 0) {
                        loginBinding.actLoginTilMobile.setHintEnabled(false);
                        loginBinding.actLoginTilMobile.setBackgroundResource(R.drawable.login_rectangleshape);
                    } else {
                        loginBinding.actLoginTilMobile.setHintEnabled(true);
                        loginBinding.actLoginTilMobile.setBackgroundResource(R.drawable.otp_background_shape);
                    }
                }
            }
        });
        mSharedPref.saveValue(Constants.PrefKeys.PREF_API_SERVER, Constants.PrefKeys.PREF_QA_SERVER);
    }
    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (v.getId() == R.id.act_login_btn_login) {

            if (loginBinding.actLoginTilPassword.getVisibility() == View.VISIBLE) {

                String strPasswordError = AppUtility.isLoginPasswordValid(activity, loginBinding.actLoginEtPassword.getText().toString());
                if (AppUtility.isNetworkAvailable(activity)) {
                    if (strPasswordError.length() == 0) {
                        if (!loginBinding.actLoginTilPassword.isPasswordVisibilityToggleEnabled()) {
                            loginBinding.actLoginTilPassword.setPasswordVisibilityToggleEnabled(true);
                        }
                    } else {
                        if (loginBinding.actLoginTilPassword.isPasswordVisibilityToggleEnabled()) {
                            loginBinding.actLoginTilPassword.setPasswordVisibilityToggleEnabled(false);
                        }
                        loginBinding.actLoginEtPassword.setError(strPasswordError);
                        loginBinding.actLoginEtPassword.setSelection(loginBinding.actLoginEtPassword.getText().length());
                    }
                    if (strPasswordError.isEmpty()) {
                        loginBinding.actLoginEtPassword.setSelection(loginBinding.actLoginEtPassword.getText().length());
                       MyProgressDialog.getInstance().showProgressBar();
                        try {
                            Map<String, String> requestMap = new HashMap<>();
                            requestMap.put(Constants.RequestParams.REQUEST_PARAMS_MOBILE, loginBinding.actLoginEtMobile.getText().toString());
                            requestMap.put(Constants.RequestParams.REQUEST_PARAMS_PASSWORD, loginBinding.actLoginEtPassword.getText().toString());
                            requestMap.put(Constants.RequestParams.REQUEST_PARAMS_DEVICE_TYPE, Constants.RequestParams.REQUEST_PARAMS_VALUE_DEVICE_TYPE);
                            requestMap.put(Constants.RequestParams.REQUEST_PARAMS_NOTIFICATION_ID, activity.mSharedPref.getFCMToken());

                            loginResponseLiveData = ToRepository.getInstance(MyApplication.getmContext(), ToRepository.APIBaseURLType.URL_1).apiCallingMethod(ToRepository.POST, ToRepository.POST_URL + Constants.ApiURLs.API_URL_POST_LOGIN, requestMap, loginResponseLiveData, false, false);
                            observeViewModel();
                        } catch (Exception e) {
                            MyProgressDialog.getInstance().dismissProgressBar();
                            Log.d("Tag", "exception error" + e.getMessage());
                        }
                    }
                } else {
                    activity.toastMsg(activity.getString(R.string.no_internet_connection_login));
                }
            } else {
                /*Check the validation*/
                FormValidationUtils fvu = new FormValidationUtils(activity);
                fvu.set_rules(loginBinding.actLoginEtMobile, "mobileNumber", "required|mobileNumber", new String[]{"Mobile No is Required", "Enter 10 digit mobile number"}, "seterror");
                if (fvu.run()) {
                    setPasswordView();
                }
            }
        }
         else if (v.getId() == R.id.imgBack) {
            activity.onBackPressed();
        }

    }

    public void setMobileNumberView() {
        loginBinding.actLoginTvSubTile.setText(activity.getResources().getString(R.string.txt_enter_mobile_number));
        loginBinding.actLoginTilMobile.setVisibility(View.VISIBLE);
        loginBinding.actLoginTilPassword.setVisibility(View.GONE);
        loginBinding.actLoginLlForgotPassword.setVisibility(View.INVISIBLE);
        loginBinding.actLoginEtMobile.requestFocus();
    }

    public void setPasswordView() {
        loginBinding.actLoginTvSubTile.setText(activity.getResources().getString(R.string.txt_login_enter_your_password));
        loginBinding.actLoginTilMobile.setVisibility(View.GONE);
        loginBinding.actLoginTilPassword.setVisibility(View.VISIBLE);
        loginBinding.actLoginLlForgotPassword.setVisibility(View.VISIBLE);
        loginBinding.actLoginEtPassword.requestFocus();
    }
    // login live data response observe
    private void observeViewModel() {
        loginResponseLiveData.observe(activity, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, String> hashMapMutableLiveData) {
                MyProgressDialog.getInstance().dismissProgressBar();
                if (hashMapMutableLiveData != null) {
                    String strResponseBody = hashMapMutableLiveData.get(Constants.API_RESPONSE_BODY);
                    Login login = AppUtility.convertJsonToClass(strResponseBody, Login.class);
                    if (login != null) {
                        if (login.getIHttpCode() == Constants.ResponseStatusCode.STATUS_200 && login.getBStatus()) {
                            if (login.getUserTData() != null) {
                                if (login.getUserTData().user.is_otpvarified) {
                                    if (login.getUserTData().user.is_adharverified){
                                        activity.toastMsg(login.getTMessage());
                                        activity.mSharedPref.saveValue(Constants.PrefKeys.PREF_IS_LOGIN, true);
                                        activity.mSharedPref.setUserID(login.getUserTData().user.user_id);
                                        activity.mSharedPref.setUserName(login.getUserTData().user.name);
                                        activity.mSharedPref.setMobileNo(login.getUserTData().user.mobileNo);
                                        activity.mSharedPref.setAccessToken(login.getUserTData().token.access_token);
                                        activity.mSharedPref.setUserEmail(login.getUserTData().user.email);
                                        activity.mSharedPref.saveValue(Constants.PrefKeys.PREF_IS_MEMBER, login.getUserTData().user.is_member);
                                        activity.mSharedPref.setUserOldPwd(loginBinding.actLoginEtPassword.getText().toString());
                                        activity.callIntent(NavigationActivity.class, "finish");
                                    }
                                }
                            } else {
                                activity.toastMsg(login.getTMessage());
                            }
                        }
                        else {
                            activity.toastMsg(login.getTMessage());
                        }
                    } else {
                        activity.toastMsg(activity.getString(R.string.server_error));
                    }
                }
            }
        });
    }
    //runtime permission method
    public void permissionFunc(int requestCode, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsRequired[1]) || ActivityCompat.checkSelfPermission(activity, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            } else {
                Toast.makeText(activity, "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void handlePermission() {
        SharedPreferences permissionStatus = activity.getSharedPreferences("permissionStatus", MODE_PRIVATE);
        if (ActivityCompat.checkSelfPermission(activity, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsRequired[1])
                    || ActivityCompat.checkSelfPermission(activity, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED) {
                //Show Information about why you need the permission
                ActivityCompat.requestPermissions(activity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                // Redirect to Settings after showing Information about why you need the permission
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                Toast.makeText(activity, "Go to Settings to Grant Permission", Toast.LENGTH_LONG).show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(activity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.apply();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
//        Toast.makeText(activity,"Permission granted..",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (loginBinding.actLoginEtPassword.getError() != null) {
            loginBinding.actLoginEtPassword.setError(null);
            loginBinding.actLoginTilPassword.setPasswordVisibilityToggleEnabled(true);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    // used for set focus background, selected text and unselected text

    public static class LoginFactory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private ActivityLoginBinding loginBinding;
        private LoginActivity activity;
        public LoginFactory(Application application, LoginActivity activity, ActivityLoginBinding loginBinding) {
            this.application = application;
            this.activity = activity;
            this.loginBinding = loginBinding;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LoginViewModel(application, activity, loginBinding);
        }
    }
}
