package com.acrs.buddies.ui.register;

import com.acrs.buddies.ui.base.MvpView;

public interface RegisterView extends MvpView{

    void initialize();
    void onSuccessApi();
    void onFailerApi();
    void register();

}
