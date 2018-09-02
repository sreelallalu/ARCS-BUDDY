package com.acrs.buddies.ui.login;

import com.acrs.buddies.ui.base.MvpView;

public interface LoginView extends MvpView {

    void initialize();
    void loginClick();
    void onFailerApi();
    void onSuccessApi();

}
