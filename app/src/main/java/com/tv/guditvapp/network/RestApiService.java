package com.tv.guditvapp.network;

import com.tv.guditvapp.dashboard.model.DashBoardModel;
import com.tv.guditvapp.login.model.LoginUserCreateModel;
import com.tv.guditvapp.login.model.LoginUserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.tv.guditvapp.network.ApiManager.DASH_BOARD_SECTIONS;
import static com.tv.guditvapp.network.ApiManager.USER_REGISTRATION;

public interface RestApiService {

   /* @GET("users/{user_id}/playlists")
    Call<List<Playlist> getUserPlaylists(@Path(value = "user_id", encoded = true) String userId);
*/
    @GET(DASH_BOARD_SECTIONS+"{screen_id}")
    Call<DashBoardModel> getAllScreenData(@Path(value = "screen_id", encoded = true) String screenId);

    @POST(USER_REGISTRATION)
    Call<LoginUserModel> postRegistration(@Body LoginUserCreateModel createModel);
}
