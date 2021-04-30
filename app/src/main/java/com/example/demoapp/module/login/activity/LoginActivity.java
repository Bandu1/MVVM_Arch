package com.example.demoapp.module.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivityLoginBinding;
import com.example.demoapp.module.login.viewmodel.LoginViewModel;
import com.example.demoapp.utility.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of login
 * Revisions        : 1 - XYZ     29-04-2021
 *			         Change – Add in add()
 *
 *                    2 - PQR     30-11-2021
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class LoginActivity extends BaseActivity {
    private LoginViewModel mViewmodel;
    private ActivityLoginBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel.LoginFactory factory = new LoginViewModel.LoginFactory(getApplication(), this, mBinding);
        mViewmodel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        mBinding.setLoginViewModel(mViewmodel);
       // mBinding.toolbarLay.txtTitle.setText(getResources().getString(R.string.txt_login));

    }
    @Override
    public void onBackPressed() {
        if (mBinding.actLoginTilPassword.getVisibility() == View.VISIBLE) {
            mViewmodel.setMobileNumberView();
        } else {

           /* Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);*/
        }
    }

    // used for set focus background, selected text and unselected text
    public final void editTextFun(final EditText editText, final TextInputLayout textInputLayout) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textInputLayout.setBackgroundResource(R.drawable.otp_background_shape_black);
                } else {
                    if (editText.getText().length() == 0) {
                        textInputLayout.setBackgroundResource(R.drawable.login_rectangleshape);
                    } else {
                        textInputLayout.setBackgroundResource(R.drawable.otp_background_shape);
                    }
                }
            }
        });
    }
}