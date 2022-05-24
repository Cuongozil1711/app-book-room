package com.example.finalandroid.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.finalandroid.ConfirmBookRoom;
import com.example.finalandroid.R;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.model.BookRoomOfUser;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.Room;
import com.example.finalandroid.model.UserRoom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRoom extends AppCompatActivity {

    private ImageView imgRoom;
    private ImageButton btBack;
    private TextView nameTabar, idName, idPrice, idPriceSale, timeNhan, timeTra, tvMota;
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
                boolean isTime = checkTime();
                if(isTime){
                    Intent intent = new Intent(context, ConfirmBookRoom.class);
                    BookRoomOfUser roomOfUser = new BookRoomOfUser(timeNhan.getText().toString(), timeTra.getText().toString());
                    intent.putExtra("hotel", hotel);
                    intent.putExtra("roomOfUser", roomOfUser);
                    intent.putExtra("room", room);
                    startActivity(intent);
                }

            }

        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LinearLayout btTimeNhan = (LinearLayout) findViewById (R.id.btTimeNhan);
        btTimeNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int selectedHour = c.get(Calendar.HOUR);
                int selectedMinute = c.get(Calendar.MINUTE);
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        boolean is24HView = true;


                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        String date = "";
                                        if(m > 8) {
                                            date = d + "/" + (m + 1) + "/" + y;
                                        }
                                        else{
                                            date = d + "/0" + (m + 1) + "/" + y;
                                        }
                                        timeNhan.setText(hourOfDay + ":" + minute +" " + date);
                                    }
                                }, selectedHour, selectedMinute, is24HView);
                        timePickerDialog.show();
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        LinearLayout btTimeTra = (LinearLayout) findViewById (R.id.btTimeTra);
        btTimeTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int selectedHour = c.get(Calendar.HOUR);
                int selectedMinute = c.get(Calendar.MINUTE);
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        boolean is24HView = true;


                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        String date = "";
                                        if(m > 8) {
                                            date = d + "/" + (m + 1) + "/" + y;
                                        }
                                        else{
                                            date = d + "/0" + (m + 1) + "/" + y;
                                        }
                                        timeTra.setText(hourOfDay + ":" + minute +" " + date);
                                    }
                                }, selectedHour, selectedMinute, is24HView);
                        timePickerDialog.show();
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }



    private void getIntentView() {
        room = (Room) getIntent().getSerializableExtra("room");
        hotel = (Hotel)  getIntent().getSerializableExtra("hotel");
        new ConfigGetData(imgRoom)
                .execute(room.getImage());
        idName.setText(room.getName());
        nameTabar.setText(room.getName());
        idPrice.setText(room.getPrice());
        tvMota.setText(room.getDescribe());
    }

    private void initView() {
        imgRoom = findViewById(R.id.tvImageHotel);
        nameTabar = findViewById(R.id.nameTabar);
        idName = findViewById(R.id.idName);
        idPrice = findViewById(R.id.idPrice);
        idPriceSale = findViewById(R.id.idPriceSale);
        timeNhan = findViewById(R.id.timeNhan);
        timeTra = findViewById(R.id.timeTra);
        tvMota = findViewById(R.id.tvMota);
        btnBookRoom = findViewById(R.id.btnBookRoom);
        btBack = findViewById(R.id.btBack);
    }


    private boolean checkTime() {
        try {
            SimpleDateFormat sp = new SimpleDateFormat("HH:MM YYYY/MM/DD");
            Date dateNhan = sp.parse(String.valueOf(timeNhan.getText()));
            Date dateTra = sp.parse(String.valueOf(timeTra.getText()));
            if(dateTra.compareTo(dateNhan) <= 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ZoZoy thông báo");
                builder.setMessage("Ngày nhận, trả không hợp lệ");
                builder.setIcon(R.drawable.ic_baseline_mobile_screen_share_24);
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
            else{
                ApiService.apiService.getListUserRoom(room.getIdRoom()).enqueue(new Callback<List<UserRoom>>() {
                    @Override
                    public void onResponse(Call<List<UserRoom>> call, Response<List<UserRoom>> response) {
                        List<UserRoom> list = response.body();
                        for(UserRoom item: list){
                            SimpleDateFormat sp1 = new SimpleDateFormat("HH:MM YYYY/MM/DD");
                            Date dateFrom = null;
                            try {
                                dateFrom = sp1.parse(item.getDateFrom());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(dateFrom.compareTo(dateNhan) <= 0){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("ZoZoy thông báo");
                                builder.setMessage("Thời gian này phòng đã được đặt");
                                builder.setIcon(R.drawable.ic_baseline_mobile_screen_share_24);
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserRoom>> call, Throwable t) {

                    }
                });
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}