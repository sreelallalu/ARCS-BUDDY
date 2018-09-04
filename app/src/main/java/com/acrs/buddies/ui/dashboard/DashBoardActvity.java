package com.acrs.buddies.ui.dashboard;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.acrs.buddies.R;
import com.acrs.buddies.databinding.ActivityDashBoardBinding;
import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.login.LoginActvity;
import com.acrs.buddies.ui.medicineadd.MedicineAddActvity;
import com.acrs.buddies.ui.takepic.TakePictureActivity;
import com.acrs.buddies.ui.useradd.UserAddActivity;
import com.acrs.buddies.ui.viewuser.ViewUserActivity;

public class DashBoardActvity extends BaseActivity implements DashBoardView {
    ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);
        binding.addmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActvity.this, MedicineAddActvity.class);
                startActivity(intent);

            }
        });
        binding.adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActvity.this, UserAddActivity.class);
                startActivity(intent);

            }
        });
        binding.viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActvity.this, ViewUserActivity.class);
                startActivity(intent);

            }
        });
        binding.takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActvity.this, TakePictureActivity.class);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("DashBoard");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:

                try {
                    logOut();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logOut() {
        dataManager.setFirebaseID(null);
        dataManager.setUserDetails(null);
        dataManager.setUserId(null);
        dataManager.setNotificationCancel(1, false);
        Intent intent = new Intent(this, LoginActvity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
