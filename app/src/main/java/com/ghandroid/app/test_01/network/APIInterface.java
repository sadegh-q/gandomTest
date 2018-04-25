package com.ghandroid.app.test_01.network;

import com.ghandroid.app.test_01.ui.model.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface APIInterface {

    @GET("home")
    Call<PhotoResponse> getPhotosCategory();

}
