package com.example.finalandroid.api;

import com.example.finalandroid.User;
import com.example.finalandroid.dto.Userbean;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.ReviewHotel;
import com.example.finalandroid.model.Room;
import com.example.finalandroid.model.UserHotel;
import com.example.finalandroid.model.UserRoom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);



    @GET("getListUser")
    Call<List<User>> getListUser();

    @GET("login")
    Call<User> login(@Query("phone") String phone);

    @GET("getHotel")
    Call<List<Hotel>> getHotel();


    @GET("getListRoom")
    Call<List<Room>> getListRoom(@Query("idHotel") String idHotel);

    @Multipart
    @POST("updateUser")
    Call<User> updateUser(@Part MultipartBody.Part part, @Query("id") Integer id,
                             @Query("phone") String phone, @Query("email") String email,
                             @Query("date") String date,
                             @Query("gender") String gender
                             );

    @GET("historyBook")
    Call<List<HistoryBookRoom>> getHistoryBook(@Query("id") Integer id, @Query("isDelete") String isDelete);

    @GET("registerUser")
    Call<User> registerUser(@Query("phone") String phone, @Query("name") String name);


    @POST("bookRoom")
    Call<Integer> bookRoom(@Body UserRoom us);

    @GET("getReview")
    Call<List<ReviewHotel>> getReview(@Query("idHotel") Integer idHotel);

    @POST("reviewHotel")
    Call<Integer> reviewHotel(@Body UserHotel us);

}
