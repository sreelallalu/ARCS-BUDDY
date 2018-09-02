package com.acrs.buddies.ui.viewuser;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface ViewUser_i_Presenter<T extends ViewUserView> extends MvpPresenter<T> {
    void buddyList(HashMap<String, String> hashMap);
}
