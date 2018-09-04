package com.acrs.buddies.ui.takepic;

import android.util.Log;

import com.acrs.buddies.R;
import com.acrs.buddies.data.DataManager;
import com.acrs.buddies.di.service.RestBuilderPro;
import com.acrs.buddies.ui.base.BasePresenter;
import com.acrs.buddies.ui.medicineadd.CitizenModel;
import com.acrs.buddies.ui.medicineadd.MedicineAddWebApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakePicturePresenter<T extends TakePictureView> extends BasePresenter<T> implements TakePicture_i_Presenter<T> {

    @Inject
    public TakePicturePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void viewCitizensApi(HashMap<String, String> hashMap) {
        Log.e("params", hashMap.toString());
        RestBuilderPro.getService(MedicineAddWebApi.class).viewcitizen(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        successResponseCitizen(res);
                    } catch (Exception e) {
                        e.printStackTrace();

                        getView().SnakBarString("Response error");
                        getView().viewUserListFailed();

                    }

                } else {
                    getView().SnakBarString("Something went wrong");
                    getView().viewUserListFailed();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                getView().SnakBarId(R.string.notconnect);
                getView().viewUserListFailed();
            }
        });
    }

    @Override
    public void submitTakepic(HashMap<String, String> hashMap) {

        Log.e("params", hashMap.toString());
        RestBuilderPro.getService(MedicineAddWebApi.class).viewcitizen(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        Log.e("response", res);
                        getView().requestSucc();
                    } catch (Exception e) {
                        e.printStackTrace();

                        getView().SnakBarString("Response error");
                        getView().requestFail();

                    }

                } else {
                    getView().SnakBarString("Something went wrong");
                    getView().requestFail();


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                getView().SnakBarId(R.string.notconnect);
                getView().requestFail();

            }
        });

    }

    private void successResponseCitizen(String res) throws JSONException {

        Log.e("response_", res);
        JSONObject jsonObject = new JSONObject(res);
        int succ = jsonObject.getInt("success");
        if (succ == 1) {

            List<CitizenModel> list = new ArrayList<>();
            JSONArray userdata = jsonObject.getJSONArray("data");
            for (int i = 0; i < userdata.length(); i++) {
                {


                    JSONObject jsonObject1 = userdata.getJSONObject(i);
                    CitizenModel model = new CitizenModel();
                    model.setAge(jsonObject1.getString("age"));
                    model.setEmail(jsonObject1.getString("email"));
                    model.setGender(jsonObject1.getString("gender"));
                    model.setPhone_no(jsonObject1.getString("phone_no"));
                    model.setUserid(jsonObject1.getString("userid"));
                    model.setUniqueId(jsonObject1.getString("uniqueId"));
                    model.setUsername(jsonObject1.getString("username"));

                    list.add(model);


                }
                getView().viewUserListSuccess(list);

            }


        } else {

            getView().SnakBarString("Something went wrong");
            getView().viewUserListFailed();
        }

    }


}
