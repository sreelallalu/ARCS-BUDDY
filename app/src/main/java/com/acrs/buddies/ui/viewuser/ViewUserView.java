package com.acrs.buddies.ui.viewuser;

import com.acrs.buddies.ui.base.MvpView;
import com.acrs.buddies.ui.medicineadd.CitizenModel;

import java.util.List;

public interface ViewUserView extends MvpView{
    void initialize();
    void refreshData();
    void onFailerApi();
    void onSuccessApi(List<CitizenModel> list);
}
