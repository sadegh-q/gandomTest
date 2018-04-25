package com.ghandroid.app.test_01.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit instance;
    private static String baseUrl = "http://gandom.co/devTest/1/";

    public static Retrofit getInstance() {
        if (instance == null)
            instance = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return instance;
    }

}
