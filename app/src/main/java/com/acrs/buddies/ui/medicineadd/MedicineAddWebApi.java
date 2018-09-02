package com.acrs.buddies.ui.medicineadd;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MedicineAddWebApi {


    @POST("BuddyIndex.php")
    @FormUrlEncoded
    Call<ResponseBody> viewcitizen(@FieldMap HashMap<String,String> hashMap);



}
