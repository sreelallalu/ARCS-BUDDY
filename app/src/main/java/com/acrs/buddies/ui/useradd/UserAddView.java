package com.acrs.buddies.ui.useradd;

import com.acrs.buddies.ui.base.MvpView;

public interface UserAddView extends MvpView{

    void initialize();
    void addUserSucess();
    void addUserFailed();
}
