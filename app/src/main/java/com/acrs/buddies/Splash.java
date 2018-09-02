package com.acrs.buddies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.base.Permission;
import com.acrs.buddies.ui.base.PermissionHelper;
import com.acrs.buddies.ui.dashboard.DashBoardActvity;
import com.acrs.buddies.ui.login.LoginActvity;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public class Splash extends BaseActivity {


    private PermissionHelper permissionHelper;
    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(FLAG_LAYOUT_NO_LIMITS);

        if (dataManager.getUserId() != null) {
            intent = new Intent(this, DashBoardActvity.class);

        } else {
            intent = new Intent(this, LoginActvity.class);

        }
        permissionHelper = new PermissionHelper(this, Permission.camera,12);


        if(Build.VERSION.SDK_INT>22)
        {
            permission();
        }else{
            nextActvity();
        }



    }

    private void permission() {


        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                nextActvity();
            }

            @Override
            public void onPermissionDenied() {

            }

            @Override
            public void onPermissionDeniedBySystem() {

            }
        });
    }

    private void nextActvity() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }


}

