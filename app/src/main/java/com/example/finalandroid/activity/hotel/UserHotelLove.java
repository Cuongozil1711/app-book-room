package com.example.finalandroid.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.review.ReviewDetailsHotel;
import com.example.finalandroid.adapter.RecycleViewHotelAdapter;
import com.example.finalandroid.adapter.RecyleViewHistoryAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHotelLove extends AppCompatActivity implements RecycleViewHotelAdapter.ItemListener{

    private User user;
    private SqliteHelper sqliteHelper;
    private List<Hotel> bookRoomList;
    private Context context;

    private RecyclerView recyclerView;
    private RecycleViewHotelAdapter adapter;
    private RecycleViewHotelAdapter.ItemListener itemListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_hotel_love);
        sqliteHelper = new SqliteHelper(this);
        user = sqliteHelper.getUser();
        context = this;
        recyclerView = findViewById(R.id.recycleView);
        itemListener = (RecycleViewHotelAdapter.ItemListener) this;
        getListHistoryBookRoom();
    }



    private void getListHistoryBookRoom() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.findListHotelUserLike(user.getAccessToken(), Integer.valueOf(user.getId())).enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                progressDialogCustom.hide();
                bookRoomList = response.body();
                adapter = new RecycleViewHotelAdapter();
                adapter.setmList(bookRoomList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setItemListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Hotel hotel = adapter.getItemPos(position);
        Intent intent = new Intent(context, ItemHotelAcivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        getListHistoryBookRoom();
        super.onResume();
    }
}