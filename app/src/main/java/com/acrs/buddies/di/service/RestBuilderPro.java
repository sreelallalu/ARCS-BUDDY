package com.acrs.buddies.di.service;

/**
 * Created by sreelal on 6/12/17.
 */


public class RestBuilderPro {
    private static ApiInterface service;
    public static <S> S getService(Class<S> classes) {

        return ServiceGeneratorpro.createService(classes);
    }
}