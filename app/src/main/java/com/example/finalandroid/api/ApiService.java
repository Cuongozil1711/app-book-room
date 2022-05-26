package com.example.finalandroid.api;

import com.example.finalandroid.model.User;
import com.example.finalandroid.dto.UserCodeDto;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.ReviewHotel;
import com.example.finalandroid.model.Room;
import com.example.finalandroid.model.UserHotel;
import com.example.finalandroid.model.UserHotelLike;
import com.example.finalandroid.model.UserRoom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    OkHttpClient client = new OkHttpClient();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    @GET("login")
    Call<UserCodeDto> login(@Query("phone") String phone);

    @POST("loginFaceOrGoogle")
    Call<User> loginFaceOrGoogle(@Body User us);

    @GET("checkSmsUser")
    Call<User> checkSmsUser(@Query("otp") String otp, @Query("uiId") Integer uiId);

    @GET("getHotel")
    Call<List<Hotel>> getHotel();

    @GET("getListHotelLike")
    Call<List<Hotel>> getListHotelLike();


    @GET("getListRoom")
    Call<List<Room>> getListRoom(@Query("idHotel") String idHotel);

    @Multipart
    @POST("updateUser")
    Call<User> updateUser(@Part MultipartBody.Part part, @Query("id") Integer id,
                             @Query("phone") String phone, @Query("email") String email,
                             @Query("date") String date,
                             @Query("gender") String gender,
                            @Header("Authorization") String token
                          );

    @GET("historyBook")
    Call<List<HistoryBookRoom>> getHistoryBook(@Query("id") Integer id, @Query("isDelete") String isDelete, @Header("Authorization") String token);

    @GET("registerUser")
    Call<UserCodeDto> registerUser(@Query("phone") String phone, @Query("name") String name);


    @POST("bookRoom")
    Call<Integer> bookRoom(@Body UserRoom us, @Header("Authorization") String token);

    @GET("getReview")
    Call<List<ReviewHotel>> getReview(@Query("idHotel") Integer idHotel);

    @POST("reviewHotel")
    Call<Integer> reviewHotel(@Body UserHotel us, @Header("Authorization") String token);

    @GET("listUserRoom")
    Call<List<UserRoom>> getListUserRoom(@Query("id") Integer id);

    @GET("listUserLike")
    Call<List<UserHotelLike>> listUserLike(@Query("idHotel") Integer idHotel);

    @POST("updateUserLike")
    Call<Integer> updateUserLike(@Body UserHotelLike userHotelLike);

    @GET("checkToken")
    Call<Integer> checkToken(@Header("Authorization") String token);

    @GET("deleteBookRoom")
    Call<Integer> deleteBookRoom(@Header("Authorization") String token, @Query("id") Integer id);

    @GET("findListHotelUserLike")
    Call<List<Hotel>> findListHotelUserLike(@Header("Authorization") String token, @Query("id") Integer id);
}
