package com.acrs.buddies.di.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sreelal on 6/12/17.
 */


public class ServiceGeneratorpro {

    private final static String BASEURL = "http://rescue.nyesteventuretech.com/";

    public static <S> S createService(Class<S> service) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();
//addConverterFactory(GsonConverterFactory.create(gson))
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return adapter.create(service);
    }
}