package com.acrs.buddies.ui.base;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.acrs.buddies.App;
import com.acrs.buddies.R;
import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.di.component.ActivityComponent;
import com.acrs.buddies.di.component.DaggerActivityComponent;
import com.acrs.buddies.di.module.ActivityModule;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity implements MvpView {
    private ActivityComponent activityComponent;
    protected Bitmap bitmapMain;
    protected int colorMain;

    protected String userToken;
    private ProgressDialog dialog;

    @Inject
   protected DataManager dataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();
        activityComponent.inject(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");

        if (dataManager.getFirebaseID() == null) {
            String firebaseID = FirebaseInstanceId.getInstance().getToken();
            if (firebaseID != null) {
                Log.e("firebase_token",firebaseID);
                dataManager.setFirebaseID(firebaseID);

            }

        }

    }


    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }


    @Override
    public void SnakBarId(int msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbarcolor));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    static Callback addCallback;

    public interface Callback {
        void back();
    }

    public void SnakBarCallback(String msg, Callback callback) {
        addCallback = callback;
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbarcolor));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {

                addCallback.back();

            }
        });


    }

    @Override
    public void SnakBarString(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbarsucc));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();

    }

    @Override
    public void SnakBarStringSucc(String msg) {
        try {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    msg, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getResources().getColor(R.color.snackbarsucc));
            TextView textView = (TextView) sbView
                    .findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            snackbar.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void SnakBarStringFail(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.snackbarerror));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void progresShow(boolean load) {
        if (dialog != null && load)

        {
            dialog.show();
        } else if (dialog != null && !load) {
            dialog.dismiss();
        }
    }

    @Override
    public void progresCancel(boolean cancel) {
        if (dialog != null && cancel) {
            dialog.setCancelable(cancel);
        } else if (dialog != null && !cancel)

        {
            dialog.setCancelable(cancel);
        }

    }

}
