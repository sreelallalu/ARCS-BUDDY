package com.acrs.buddies.ui.takepic;

import com.acrs.buddies.ui.base.MvpView;
import com.acrs.buddies.ui.medicineadd.CitizenModel;

import java.util.List;

public interface TakePictureView extends MvpView{

    void initialize();
    void viewUserListFailed();
    void viewUserListSuccess(List<CitizenModel> list);

    void requestFail();
    void requestSucc();
}
