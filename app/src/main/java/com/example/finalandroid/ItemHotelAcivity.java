package com.example.finalandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalandroid.adapter.RecycleViewHotelAdapter;
import com.example.finalandroid.adapter.RecycleViewRoomApdater;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.fragment.FragmentBook;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.ReviewHotel;
import com.example.finalandroid.model.Room;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemHotelAcivity extends AppCompatActivity implements RecycleViewRoomApdater.ItemListener{

    private Hotel hotel;
    private ImageView imageHotel;
    private TextView phone, star, title, address, tvMota, nameReview, dayReview, reviewStar, idReview, sumReivew, idNameTabar;
    private ImageButton btBack, idFavorite, idFavoriteTick;
    private RecyclerView recyclerView;
    private RecycleViewRoomApdater adapter;
    private RecycleViewRoomApdater.ItemListener itemListener;
    private List<Room> list;
    private List<ReviewHotel> listReview;
    private Context context;
    private boolean isExpanded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_hotel_acivity);
        imageHotel = findViewById(R.id.tvImageHotel);
        phone = findViewById(R.id.idPhone);
        star = findViewById(R.id.idStar);
        title = findViewById(R.id.idName);
        address = findViewById(R.id.idAdress);
        btBack = findViewById(R.id.btBack);
        tvMota = findViewById(R.id.tvMota);
        nameReview = findViewById(R.id.nameReview);
        dayReview = findViewById(R.id.dayReview);
        reviewStar = findViewById(R.id.reviewStar);
        idReview = findViewById(R.id.idReview);
        sumReivew = findViewById(R.id.sumReivew);
        idNameTabar = findViewById(R.id.idNameTabar);
        idFavorite = findViewById(R.id.idFavorite);
        idFavoriteTick = findViewById(R.id.idFavoriteTick);
        getIntentItem();
        recyclerView = findViewById(R.id.recycleView);
        initToolBar();
        itemListener = this;
        context = this;
        getListRoom();
        getListReview();
        initToolBarAnimation();
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        idFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idFavoriteTick.setVisibility(View.VISIBLE);
                idFavorite.setVisibility(View.GONE);
            }
        });

        idFavoriteTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idFavorite.setVisibility(View.VISIBLE);
                idFavoriteTick.setVisibility(View.GONE);
            }
        });
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
                        nameReview.setText(listReview.get(0).getNameReview());
                        dayReview.setText(listReview.get(0).getDayReview());
                        reviewStar.setText(listReview.get(0).getReviewStar());
                        idReview.setText(listReview.get(0).getIdReview());
                        sumReivew.setText(String.valueOf(listReview.size()));
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

    private void initToolBar() {
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }

    private void initToolBarAnimation(){
//        collapsingToolbarLayout.setTitle(hotel.getName());
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.two);
//
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(@Nullable Palette palette) {
//                int myColor = palette.getVibrantColor(getResources().getColor(R.color.colorChecked));
//                collapsingToolbarLayout.setContentScrimColor(myColor);
//                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary));
//            }
//        });
//
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(Math.abs(verticalOffset) > 200){
//                    isExpanded = false;
//                }
//                else{
//                    isExpanded = true;
//                }
//                invalidateOptionsMenu();
//            }
//        });
    }

    private void getListRoom() {
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.show();
        ApiService.apiService.getListRoom(String.valueOf(hotel.getId())).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                progressDialogCustom.hide();
                list = response.body();
                adapter = new RecycleViewRoomApdater();
                adapter.setmList(list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
//                {
//                    @Override
//                    public boolean canScrollVertically() {
//                        return false;
//                    }
//                };
                recyclerView.setLayoutManager(linearLayoutManager);
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

    private void getIntentItem() {
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
        new ConfigGetData(imageHotel)
                .execute(hotel.getImage());
        phone.setText(hotel.getPhone());
        star.setText(hotel.getStar());
        title.setText(hotel.getName());
        idNameTabar.setText(hotel.getName());
        address.setText(hotel.getAddress());
        tvMota.setText(hotel.getDescribe());
    }

    public  void backBook(){
        Intent i = new Intent(ItemHotelAcivity.this, FragmentBook.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        Room room = adapter.getItemPos(position);
        Intent intent = new Intent(this, BookRoom.class);
        intent.putExtra("room", room);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    public void listRoom(View view){
        Intent intent = new Intent(this, ListRoomOfHotel.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    public void listReview(View view){
        Intent intent = new Intent(this, ListReview.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}