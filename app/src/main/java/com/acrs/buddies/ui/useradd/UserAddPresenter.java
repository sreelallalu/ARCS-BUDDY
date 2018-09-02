package com.acrs.buddies.ui.useradd;

import android.util.Log;

import com.acrs.buddies.R;
import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.di.service.RestBuilderPro;
import com.acrs.buddies.ui.base.BasePresenter;
import com.acrs.buddies.ui.medicineadd.MedicineAddWebApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddPresenter<T extends UserAddView> extends BasePresenter<T> implements UserAdd_i_Presenter<T> {

    @Inject
    public UserAddPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void requestUser(HashMap<String, String> hashMap) {
        RestBuilderPro.getService(MedicineAddWebApi.class).viewcitizen(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        successResponse(res);
                    } catch (Exception e) {
                        e.printStackTrace();

                        getView().SnakBarString("Response error");
                        getView().addUserFailed();

                    }

                } else {
                    getView().SnakBarString("Something went wrong");
                    getView().addUserFailed();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                getView().SnakBarId(R.string.notconnect);
                getView().addUserFailed();

            }
        });
    }

    private void successResponse(String res) throws JSONException {

        JSONObject jsonObject = new JSONObject(res);
        int succ = jsonObject.getInt("success");
        if (succ == 1) {

            if(jsonObject.get("data") instanceof  JSONObject)
            {

                getView().addUserSucess();
            }else{
                getView().SnakBarString("Request Already Send");
                getView().addUserFailed();
            }


        }else{
            getView().SnakBarString("Request Faild !");
            getView().addUserFailed();
        }
    }
}
