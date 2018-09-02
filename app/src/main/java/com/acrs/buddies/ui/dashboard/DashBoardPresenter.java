package com.acrs.buddies.ui.dashboard;

import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.ui.base.BasePresenter;

public class DashBoardPresenter <T extends DashBoardView> extends BasePresenter<T> implements DashBoard_i_Presenter<T> {
    public DashBoardPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
