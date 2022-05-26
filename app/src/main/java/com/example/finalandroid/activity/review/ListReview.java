package com.example.finalandroid.activity.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.hotel.ItemHotelAcivity;
import com.example.finalandroid.adapter.RecycleViewRoomApdater;
import com.example.finalandroid.adapter.RecycleViewRoomDetailsApdater;
import com.example.finalandroid.adapter.RevycleViewReviewHotelAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.ReviewHotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListReview extends AppCompatActivity {

    private Context context;
    private Hotel hotel;
    private RecyclerView recyclerView;
    private RevycleViewReviewHotelAdapter adapter;
    private List<ReviewHotel> listReview;
    private ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_list_review);
        recyclerView = findViewById(R.id.recycleView);
        btBack = findViewById(R.id.btBack);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListReview.this, ItemHotelAcivity.class);
                startActivity(intent);
            }
        });
        getIntentItem();
        getListReview();
    }

    private void getIntentItem() {
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
    }

    private void getListReview() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.getReview(Integer.valueOf(hotel.getId())).enqueue(new Callback<List<ReviewHotel>>() {
            @Override
            public void onResponse(Call<List<ReviewHotel>> call, Response<List<ReviewHotel>> response) {
                progressDialogCustom.hide();
                if(response.body() != null){
                    listReview = response.body();
                    if(listReview.size() > 0){
                        adapter = new RevycleViewReviewHotelAdapter();
                        adapter.setmList(listReview);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<ReviewHotel>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}