package com.example.finalandroid.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.hotel.ItemHotelAcivity;
import com.example.finalandroid.adapter.RecycleViewKeySearchAdapter;
import com.example.finalandroid.adapter.RecycleViewResultSearchAdapter;
import com.example.finalandroid.dal.SqlSearch;
import com.example.finalandroid.dal.SqliteHelperHotel;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.NameSearch;

import java.util.List;

public class SearchHotelActivity extends AppCompatActivity implements RecycleViewResultSearchAdapter.ItemListener{

    private TextView tvSearch, idResult;
    private List<NameSearch> searchList;
    private RecyclerView recyclerView;
    private RecycleViewKeySearchAdapter adapterSearch;
    private EditText evKeySearch;
    private SqliteHelperHotel sqliteHelperHotel;
    private RecyclerView recyclerView1;
    private RecycleViewResultSearchAdapter adapter;
    private RecycleViewResultSearchAdapter.ItemListener itemListener;
    private Context context;
    private ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotel);
        initview();
        getSearch();
        context = this;
        itemListener = this;
        sqliteHelperHotel = new SqliteHelperHotel(this);
        evKeySearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                List<Hotel> listHotel = sqliteHelperHotel.searchByAddress(s.toString());
                List<Hotel> listHotel1 = sqliteHelperHotel.searchByName(s.toString());
                List<Hotel> listResult = listHotel;
                if(listResult.size() > 0){
                    for(Hotel hotel: listHotel1){
                        boolean checkId = false;
                        for(Hotel hotel1 : listResult){
                            if(hotel1.getId() == hotel.getId()){
                                checkId = true;
                                break;
                            }
                        }
                        if(!checkId) listResult.add(hotel);
                    }
                    idResult.setVisibility(View.VISIBLE);
                    idResult.setText(listResult.size() + " kết quả");

                    adapter = new RecycleViewResultSearchAdapter();
                    adapter.setmList(listHotel1);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    recyclerView1.setLayoutManager(linearLayoutManager);
                    recyclerView1.setAdapter(adapter);
                    adapter.setItemListener(itemListener);

                    recyclerView.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.GONE);
                }
                else{
                    idResult.setVisibility(View.GONE);
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getSearch() {
        SqlSearch sqlSearch = new SqlSearch(this);
        List<NameSearch> nameSearches = sqlSearch.getAllKeySearch();
        if(nameSearches.size() > 0) tvSearch.setVisibility(View.VISIBLE);
        adapterSearch = new RecycleViewKeySearchAdapter(this);
        adapterSearch.setmList(nameSearches);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterSearch);
    }

    private void initview() {
        tvSearch = findViewById(R.id.tvSearch);
        idResult = findViewById(R.id.idResult);
        idResult.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycleViewSearch);
        evKeySearch = findViewById(R.id.evKeySearch);
        recyclerView1 = findViewById(R.id.recycleViewHotel);
        btBack = findViewById(R.id.btBack);
    }

    @Override
    public void onItemClick(View view, int position) {
        Hotel hotel = adapter.getItemPos(position);
        Intent intent = new Intent(context, ItemHotelAcivity.class);
        SqlSearch sqlSearch = new SqlSearch(this);
        sqlSearch.addSearch(new NameSearch(hotel.getName()));
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}