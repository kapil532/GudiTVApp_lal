package com.tv.guditvapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tv.guditvapp.network.ApiManager.BASE_URL;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static RestApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RestApiService.class);
    }
}
