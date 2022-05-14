package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.model.BookRoomOfUser;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.Room;

public class BookRoom extends AppCompatActivity {

    private ImageView imgRoom;
    private TextView idStatus, idName, idPrice, idPriceSale, timeNhan, timeTra, tvMota;
    private Room room;
    private Hotel hotel;
    private Button btnBookRoom;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        context = this;
        initView();
        getIntentView();
        btnBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConfirmBookRoom.class);
                BookRoomOfUser roomOfUser = new BookRoomOfUser(timeNhan.getText().toString(), timeTra.getText().toString());
                intent.putExtra("hotel", hotel);
                intent.putExtra("roomOfUser", roomOfUser);
                intent.putExtra("room", room);
                startActivity(intent);
            }
        });
    }



    private void getIntentView() {
        room = (Room) getIntent().getSerializableExtra("room");
        hotel = (Hotel)  getIntent().getSerializableExtra("hotel");
        new ConfigGetData(imgRoom)
                .execute(room.getImage());
        idName.setText(room.getName());
        idPrice.setText(room.getPrice());
        tvMota.setText(room.getDescribe());
    }

    private void initView() {
        imgRoom = findViewById(R.id.tvImageHotel);
        idStatus = findViewById(R.id.idStatus);
        idName = findViewById(R.id.idName);
        idPrice = findViewById(R.id.idPrice);
        idPriceSale = findViewById(R.id.idPriceSale);
        timeNhan = findViewById(R.id.timeNhan);
        timeTra = findViewById(R.id.timeTra);
        tvMota = findViewById(R.id.tvMota);
        btnBookRoom = findViewById(R.id.btnBookRoom);
    }
}