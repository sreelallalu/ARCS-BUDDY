package com.acrs.buddies.ui.login;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface Login_i_Presenter <T extends LoginView> extends MvpPresenter<T> {
    void loginApiCall(HashMap<String,String> hashMap);
}
