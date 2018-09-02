package com.acrs.buddies.ui.register;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface Register_i_Presenter<T extends RegisterView> extends MvpPresenter<T> {

     void register(HashMap<String,String> hashMap);


}
