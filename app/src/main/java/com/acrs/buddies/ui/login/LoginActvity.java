package com.acrs.buddies.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.acrs.buddies.R;
import com.acrs.buddies.databinding.ActivityLoginBinding;
import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.dashboard.DashBoardActvity;
import com.acrs.buddies.ui.register.RegisterActivity;

import java.util.HashMap;

import javax.inject.Inject;

public class LoginActvity extends BaseActivity implements LoginView,View.OnClickListener {
    ActivityLoginBinding binding;

    @Inject
    Login_i_Presenter<LoginView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getActivityComponent().inject(this);
        presenter.onAttach(this);
        getSupportActionBar().setTitle("Login");
        //get().setTitle("Login");
        initialize();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == binding.login.getId()) {
            loginClick();
            //onSuccessApi();

        }
        if (v.getId() == binding.register.getId()) {

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void initialize() {
        binding.login.setOnClickListener(this);
        binding.register.setOnClickListener(this);
    }

    @Override
    public void loginClick() {
        String name = binding.name.getText().toString();
        String password = binding.password.getText().toString();
        boolean check = true;
        if (name != null && name.length() < 3) {
            binding.name.setError("Invalid name");
            check = false;
        }
        if (password != null && password.length() < 3) {
            binding.password.setError("Invalid password");
            check = false;

        }
        if (check) {

            if(dataManager.getFirebaseID()!=null) {

                progresShow(true);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(LoginWebApi.LoginParams.TAG, LoginWebApi.LoginParams.TAG_USER);
                hashMap.put(LoginWebApi.LoginParams.NAME, name);
                hashMap.put(LoginWebApi.LoginParams.PASSWRD, password);
                hashMap.put(LoginWebApi.LoginParams.FIREBASE, dataManager.getFirebaseID() == null ? "" : dataManager.getFirebaseID());
                presenter.loginApiCall(hashMap);
            }else{
                super.SnakBarString("firebase id not sync with server plz check ur online or restart the application");
            }
        }
    }

    @Override
    public void onFailerApi() {
        progresShow(false);
        super.SnakBarString("Login failed");
    }

    @Override
    public void onSuccessApi() {
        progresShow(false);
        SnakBarCallback("Login Success", new Callback() {
            @Override
            public void back() {

                Intent intent = new Intent(LoginActvity.this, DashBoardActvity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
