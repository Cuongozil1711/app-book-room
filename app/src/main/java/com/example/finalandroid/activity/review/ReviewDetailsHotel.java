package com.example.finalandroid.activity.review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.hotel.ItemHotelAcivity;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.User;
import com.example.finalandroid.model.UserHotel;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDetailsHotel extends AppCompatActivity {
    private ImageView tvImage;
    private RatingBar ratingBa45;
    private TextView reviewHotel;
    private Hotel hotel;
    private Button btnReview;
    private User user;
    private Context context;
    private ImageButton btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details_hotel);
        context = this;
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
        tvImage = findViewById(R.id.tvImage);
        ratingBa45 = findViewById(R.id.ratingBa45);
        reviewHotel = findViewById(R.id.reviewHotel);
        btnReview = findViewById(R.id.btnReview);
        btBack = findViewById(R.id.btBack);
        new ConfigGetData(tvImage)
                .execute(hotel.getImage());
        SqliteHelper sqliteHelper = new SqliteHelper(this);
        user = sqliteHelper.getUser();
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewDetailsHotel.this, ItemHotelAcivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);
            }
        });
    }
    private void review() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        UserHotel uh = new UserHotel();
        uh.setReview(reviewHotel.getText().toString());
        uh.setIdUser(user.getId());
        uh.setDateReview(new SimpleDateFormat("dd/mm/yyy").format(new Date()));
        uh.setStar(String.valueOf(ratingBa45.getRating()));
        uh.setIdHotel(hotel.getId());
        ApiService.apiService.reviewHotel(uh, user.getAccessToken()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                progressDialogCustom.hide();
                if(response.body() != null){
                    Intent intent = new Intent(context, ListReview.class);
                    intent.putExtra("hotel", hotel);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đánh giá khách sạn thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}