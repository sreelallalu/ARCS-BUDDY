package com.acrs.buddies.di.component;

import android.app.Application;
import android.content.Context;

import com.acrs.buddies.App;
import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.di.ApplicationContext;
import com.acrs.buddies.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sreelal on 13/12/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

   void inject(App app);

   @ApplicationContext
   Context context();


   Application application();
   DataManager getDataManager();

}
