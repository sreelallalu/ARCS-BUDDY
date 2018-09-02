package com.acrs.buddies.ui.register;

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

public class  RegisterPresenter<T extends RegisterView> extends BasePresenter<T> implements Register_i_Presenter<T> {

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void register(HashMap<String, String> hashMap) {

        Log.e("params",hashMap.toString());
        RestBuilderPro.getService(RegisterWebApi.class).register(hashMap).enqueue(new Callback<ResponseBody>() {
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
                    getView().SnakBarStringFail("Something went wrong");
                    getView().onFailerApi();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error",t.getMessage());
                getView().SnakBarStringFail("connection failed");
                getView().onFailerApi();
            }
        });
    }

    private void successResponse(String res) throws JSONException {
        Log.e("response_regi", res);
        JSONObject jsonObject = new JSONObject(res);
        int succ = jsonObject.getInt("success");
        if (succ == 1) {

            JSONObject userdata = jsonObject.getJSONObject("data");
            String patientId = userdata.getString("id");
            getDataManager().setUserId(patientId);
            getDataManager().setUserDetails(userdata.toString());
            getView().onSuccessApi();


        } else {
            getView().SnakBarStringFail("Something went wrong");
            getView().onFailerApi();
        }


    }
}
