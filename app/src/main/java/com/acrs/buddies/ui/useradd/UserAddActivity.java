package com.acrs.buddies.ui.useradd;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.acrs.buddies.R;
import com.acrs.buddies.databinding.ActivityUserAddBinding;
import com.acrs.buddies.ui.base.BaseActivity;

import java.util.HashMap;

import javax.inject.Inject;

public class UserAddActivity extends BaseActivity implements UserAddView {

    @Inject
    UserAdd_i_Presenter<UserAddView> presenter;

    ActivityUserAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_add);
        getActivityComponent().inject(this);
        presenter.onAttach(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void initialize() {
        binding.sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = binding.userid.getText().toString().trim();
                if (userId.isEmpty()) {


                    binding.userid.setError("Invalid");
                } else {
                    UserAddActivity.super.progresShow(true);
                    HashMap<String, String> hashMa = new HashMap<>();
                    hashMa.put("tag", "requestuser");
                    hashMa.put("b_id", dataManager.getUserId());
                    hashMa.put("userid", userId);
                    presenter.requestUser(hashMa);
                }
            }
        });
    }

    @Override
    public void addUserSucess() {
        UserAddActivity.super.progresShow(false);
        SnakBarCallback("Request send succcessfully", new Callback() {
            @Override
            public void back() {
                finish();
            }
        });
    }

    @Override
    public void addUserFailed() {
        UserAddActivity.super.progresShow(false);

    }
}
