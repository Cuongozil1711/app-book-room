package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalandroid.adapter.RecycleViewRoomApdater;
import com.example.finalandroid.adapter.RecycleViewRoomDetailsApdater;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRoomOfHotel extends AppCompatActivity implements RecycleViewRoomDetailsApdater.ItemListener {

    private RecyclerView recyclerView;
    private RecycleViewRoomDetailsApdater adapter;
    private RecycleViewRoomDetailsApdater.ItemListener itemListener;
    private List<Room> list;
    private Context context;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_of_hotel);
        getIntentView();
        recyclerView = findViewById(R.id.recycleView);
        itemListener = this;
        context = this;
        getListRoom();
    }

    private void getListRoom() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.getListRoom(String.valueOf(hotel.getId())).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                progressDialogCustom.hide();
                list = response.body();
                adapter = new RecycleViewRoomDetailsApdater();
                adapter.setmList(list);
                GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
//                {
//                    @Override
//                    public boolean canScrollVertically() {
//                        return false;
//                    }
//                };
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setItemListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getApplicationContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentView() {
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
    }

    @Override
    public void onItemClick(View view, int position) {
        Room room = adapter.getItemPos(position);
        Intent intent = new Intent(this, BookRoom.class);
        intent.putExtra("room", room);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }
}