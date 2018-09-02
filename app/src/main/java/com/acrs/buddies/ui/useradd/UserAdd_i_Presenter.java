package com.acrs.buddies.ui.useradd;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface UserAdd_i_Presenter<T extends UserAddView> extends MvpPresenter<T> {
    void requestUser(HashMap<String,String>hashMap);

}
