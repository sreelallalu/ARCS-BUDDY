package com.acrs.buddies.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.acrs.buddies.di.ActivityContext;
import com.acrs.buddies.ui.login.LoginPresenter;
import com.acrs.buddies.ui.login.LoginView;
import com.acrs.buddies.ui.login.Login_i_Presenter;
import com.acrs.buddies.ui.medicineadd.MedicineAddPresenter;
import com.acrs.buddies.ui.medicineadd.MedicineAddView;
import com.acrs.buddies.ui.medicineadd.MedicineAdd_i_Presenter;
import com.acrs.buddies.ui.register.RegisterPresenter;
import com.acrs.buddies.ui.register.RegisterView;
import com.acrs.buddies.ui.register.Register_i_Presenter;
import com.acrs.buddies.ui.takepic.TakePicturePresenter;
import com.acrs.buddies.ui.takepic.TakePictureView;
import com.acrs.buddies.ui.takepic.TakePicture_i_Presenter;
import com.acrs.buddies.ui.useradd.UserAddPresenter;
import com.acrs.buddies.ui.useradd.UserAddView;
import com.acrs.buddies.ui.useradd.UserAdd_i_Presenter;
import com.acrs.buddies.ui.viewuser.ViewUserPresenter;
import com.acrs.buddies.ui.viewuser.ViewUserView;
import com.acrs.buddies.ui.viewuser.ViewUser_i_Presenter;

import dagger.Module;
import dagger.Provides;


/**
 * Created by sreelal on 13/12/17.
 */
@Module
public class ActivityModule {
    AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {

        this.activity = activity;
    }


    @Provides
    @ActivityContext
    Context provideContext() {

        return activity;
    }


    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }


    @Provides
    Register_i_Presenter<RegisterView> register_i_presenter(RegisterPresenter<RegisterView> presenter) {
        return presenter;
    }


    @Provides
    Login_i_Presenter<LoginView> login_i_presenter(LoginPresenter<LoginView> presenter) {
        return presenter;
    }

    @Provides
    MedicineAdd_i_Presenter<MedicineAddView> medicine_presenter(MedicineAddPresenter<MedicineAddView> presenter) {
        return presenter;
    }

    @Provides
    UserAdd_i_Presenter<UserAddView> useradd_preseter(UserAddPresenter<UserAddView> presenter) {
        return presenter;
    }

    @Provides
    ViewUser_i_Presenter<ViewUserView> viewuser_preseter(ViewUserPresenter<ViewUserView> presenter) {
        return presenter;
    }

    @Provides
    TakePicture_i_Presenter<TakePictureView> take_preseter(TakePicturePresenter<TakePictureView> presenter) {
        return presenter;
    }
}
