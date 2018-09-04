package com.acrs.buddies.di.component;


import com.acrs.buddies.di.PerActivity;
import com.acrs.buddies.di.module.ActivityModule;
import com.acrs.buddies.ui.base.BaseActivity;
import com.acrs.buddies.ui.login.LoginActvity;
import com.acrs.buddies.ui.medicineadd.MedicineAddActvity;
import com.acrs.buddies.ui.register.RegisterActivity;
import com.acrs.buddies.ui.takepic.TakePictureActivity;
import com.acrs.buddies.ui.useradd.UserAddActivity;
import com.acrs.buddies.ui.viewuser.ViewUserActivity;

import dagger.Component;


/**
 * Created by sreelal on 13/12/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
   void inject(BaseActivity activity);
   void inject(RegisterActivity activity);
   void inject(LoginActvity activity);
   void inject(MedicineAddActvity activity);
   void inject(UserAddActivity activity);
   void inject(ViewUserActivity activity);
   void inject(TakePictureActivity activity);
   /* void inject(BaseActivity activity);
    void inject(MainActvity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(ChangeActivity activity);

    void inject(ForgotActivity activity);
    void inject(SearchActivity activity);
    void inject(AppointmentActivity activity);

    void inject(ProfileActivity activity);
    void inject(NotificationActivity activity);
*/

}
