package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalandroid.adapter.RecyleViewHistoryAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.HistoryBookRoom;
import com.example.finalandroid.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewHotelAcitivity extends AppCompatActivity implements RecyleViewHistoryAdapter.ItemListener{

    private User user;
    private SqliteHelper sqliteHelper;
    private List<HistoryBookRoom> bookRoomList;
    private Context context;

    private RecyclerView recyclerView;
    private RecyleViewHistoryAdapter adapter;
    private RecyleViewHistoryAdapter.ItemListener itemListener;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_hotel_acitivity);

        sqliteHelper = new SqliteHelper(this);
        user = sqliteHelper.getUser();
        context = this;
        recyclerView = findViewById(R.id.recycleView);
        itemListener = this;
        getListHistoryBookRoom();
    }

    private void getIntentItem() {
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
    }


    private void getListHistoryBookRoom() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.getHistoryBook(Integer.valueOf(user.getId()), "1").enqueue(new Callback<List<HistoryBookRoom>>() {
            @Override
            public void onResponse(Call<List<HistoryBookRoom>> call, Response<List<HistoryBookRoom>> response) {
                progressDialogCustom.hide();
                bookRoomList = response.body();
                adapter = new RecyleViewHistoryAdapter(context);
                adapter.setmList(bookRoomList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setItemListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<HistoryBookRoom>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        HistoryBookRoom historyBookRoom = adapter.getItemPos(position);
        Intent intent = new Intent(context, ReviewDetailsHotel.class);
        intent.putExtra("hotel", historyBookRoom.getHotel());
        startActivity(intent);
    }
}