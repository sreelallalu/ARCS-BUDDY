package com.acrs.buddies.ui.takepic;

import com.acrs.buddies.ui.base.MvpPresenter;

import java.util.HashMap;

public interface TakePicture_i_Presenter<T extends TakePictureView> extends MvpPresenter<T> {
    void viewCitizensApi(HashMap<String, String> hashMap);

    void submitTakepic(HashMap<String, String> hashMap);

}
