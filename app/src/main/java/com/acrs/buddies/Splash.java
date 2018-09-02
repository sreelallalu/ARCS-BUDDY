package com.acrs.buddies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.dashboard.DashBoardActvity;
import com.acrs.buddies.ui.login.LoginActvity;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public class Splash extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(FLAG_LAYOUT_NO_LIMITS);
          Intent intent = null;
        if (dataManager.getUserId() != null) {
            intent = new Intent(this, DashBoardActvity.class);

        } else {
            intent = new Intent(this, LoginActvity.class);

        }
        final Intent finalIntent = intent;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(finalIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 3000);

    }

}

