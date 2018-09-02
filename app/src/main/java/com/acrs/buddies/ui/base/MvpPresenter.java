package com.acrs.buddies.ui.base;

/**
 * Created by sreelal on 22/1/18.
 */

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();
}
