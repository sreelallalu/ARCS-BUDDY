package com.acrs.buddies.ui.viewuser;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.acrs.buddies.R;
import com.acrs.buddies.databinding.ActivityViewUserBinding;
import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.medicineadd.CitizenModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ViewUserActivity extends BaseActivity implements ViewUserView, ViewUserListAdapter.OnAdapterListener, SwipeRefreshLayout.OnRefreshListener {


    @Inject
    ViewUser_i_Presenter<ViewUserView> presenter;
    ActivityViewUserBinding binding;
    private ViewUserListAdapter listAdapter;
    private boolean refreshclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_user);
        getActivityComponent().inject(this);
        presenter.onAttach(this);
        initialize();
        getSupportActionBar().setTitle("View Patients");

    }

    @Override
    public void initialize() {
        binding.swipeRefresh.setOnRefreshListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ViewUserListAdapter(new ArrayList<CitizenModel>(), this);
        binding.recycler.setAdapter(listAdapter);
        refreshData();
    }

    @Override
    public void refreshData() {
        if (refreshclick) {
            binding.progressBar.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        refreshclick = false;


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tag", "viewuser");
        hashMap.put("b_id", dataManager.getUserId());

        presenter.buddyList(hashMap);
    }

    @Override
    public void onFailerApi() {
        binding.swipeRefresh.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessApi(List<CitizenModel> list) {
        if (list != null && list.size() == 0) {
            super.SnakBarString("User list items empty");
        }
        binding.swipeRefresh.setRefreshing(false);
        binding.progressBar.setVisibility(View.GONE);
        listAdapter.setList(list != null ? list.size() > 0 ? list : new ArrayList<CitizenModel>() : new ArrayList<CitizenModel>());
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipeRefresh.setRefreshing(true);
                refreshclick = true;
                refreshData();

            }
        }, 1000);
    }

    @Override
    public void adapterItemClick(CitizenModel item) {

    }
}
