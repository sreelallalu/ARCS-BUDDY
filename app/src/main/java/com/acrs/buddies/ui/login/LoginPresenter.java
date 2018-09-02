package com.acrs.buddies.ui.login;

import android.util.Log;

import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.di.service.RestBuilderPro;
import com.acrs.buddies.ui.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter<T extends LoginView> extends BasePresenter<T> implements Login_i_Presenter<T>{
    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loginApiCall(HashMap<String, String> hashMap) {
        RestBuilderPro.getService(LoginWebApi.class).login(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        successResponse(res);
                    } catch (Exception e) {
                        e.printStackTrace();

                        getView().SnakBarStringFail("Response error");
                        getView().onFailerApi();

                    }

                } else {
                    getView().SnakBarStringFail("Login failed");
                    getView().onFailerApi();

                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getView().SnakBarStringFail("connection failed");
                getView().onFailerApi();
            }
        });
    }
    private void successResponse(String res) {

        try {
            Log.e("response_login",res);

            JSONObject jsonObject = new JSONObject(res);
            int succ = jsonObject.getInt("success");
            if (succ == 1) {

                JSONObject userdata = jsonObject.getJSONObject("data");

                String id = userdata.has("buddyid")?userdata.getString("buddyid"):"12";
                getDataManager().setUserId(id);
                getDataManager().setUserDetails(userdata.toString());
                getView().onSuccessApi();
            }else{
                getView().SnakBarStringFail("Login failed");
                getView().onFailerApi();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            getView().SnakBarStringFail("Login failed");
            getView().onFailerApi();

        }


    }

}
