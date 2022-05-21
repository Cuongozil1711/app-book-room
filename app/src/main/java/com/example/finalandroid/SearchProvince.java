package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.finalandroid.adapter.RecycleViewResultSearchAdapter;
import com.example.finalandroid.adapter.RecycleViewSelectProvinceAdapter;
import com.example.finalandroid.fragment.FragmentHome;
import com.example.finalandroid.model.NameSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchProvince extends AppCompatActivity implements RecycleViewSelectProvinceAdapter.ItemListener{

    private RecycleViewSelectProvinceAdapter.ItemListener itemListener;
    private RecyclerView recyclerView;
    private RecycleViewSelectProvinceAdapter adapter;
    private List<NameSearch> list;
    private ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_province);

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorChecked));
        getListProvince();
        itemListener = this;
        recyclerView = findViewById(R.id.recycleViewSearch);
        btBack = findViewById(R.id.btBack);
        adapter = new RecycleViewSelectProvinceAdapter(this);
        adapter.setmList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(itemListener);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        NameSearch nameSearch = adapter.getItemPos(position);
        Intent intent = new Intent(this, FragmentHome.class);
        intent.putExtra("nameSearch", nameSearch.getNameSearch());
        startActivity(intent);
    }

    public void getListProvince(){
        list = new ArrayList<>();
        list.add(new NameSearch(1, "Hà Nội"));
        list.add(new NameSearch(2, "Bắc Ninh"));
        list.add(new NameSearch(3, "Vĩnh Phúc"));
        list.add(new NameSearch(4, "Hưng Yên"));
        list.add(new NameSearch(5, "Bắc Giang"));
        list.add(new NameSearch(6, "Hải Dương"));
        list.add(new NameSearch(7, "Hòa Bình"));
        list.add(new NameSearch(8, "Hà Nam"));
        list.add(new NameSearch(9, "Thái Nguyên"));
        list.add(new NameSearch(10, "Nam Định"));
        list.add(new NameSearch(11, "Phú Thọ"));
        list.add(new NameSearch(12, "Thái Bình"));
        list.add(new NameSearch(13, "Ninh Bình"));
        list.add(new NameSearch(14, "Ninh Bình"));
        list.add(new NameSearch(15, "Hải Phòng"));
        list.add(new NameSearch(16, "Tuyên Quang"));
        list.add(new NameSearch(17, "Bắc Kạn"));
        list.add(new NameSearch(18, "Quảng Ninh"));
        list.add(new NameSearch(19, "Yên Bái"));
        list.add(new NameSearch(20, "Thanh Hóa"));
        list.add(new NameSearch(21, "Lạng Sơn"));
        list.add(new NameSearch(22, "Quảng Bình"));
        list.add(new NameSearch(23, "Cao Bằng"));
        list.add(new NameSearch(24, "Sơn La"));
        list.add(new NameSearch(25, "Hà Giang"));
        list.add(new NameSearch(26, "Lào Cai"));
        list.add(new NameSearch(27, "Nghệ An"));
        list.add(new NameSearch(28, "Lai Châu"));
        list.add(new NameSearch(29, "Điện Biên"));
        list.add(new NameSearch(30, "Hà Tĩnh"));
        list.add(new NameSearch(31, "Quảng Bình"));
        list.add(new NameSearch(32, "Quảng Trị"));
        list.add(new NameSearch(33, "Thừa Thiên Huế"));
        list.add(new NameSearch(34, "Đà Nẵng"));
        list.add(new NameSearch(35, "Quảng Nam"));
        list.add(new NameSearch(36, "Quảng Ngãi"));
        list.add(new NameSearch(37, "Kon Tum"));
        list.add(new NameSearch(38, "Gia Lai"));
        list.add(new NameSearch(39, "Bình Định"));
        list.add(new NameSearch(40, "Đắc Lak"));
        list.add(new NameSearch(41, "Phú Yên"));
        list.add(new NameSearch(42, "Đắc Nông"));
        list.add(new NameSearch(43, "Khánh Hòa"));
        list.add(new NameSearch(44, "Lâm Đồng"));
        list.add(new NameSearch(45, "Tây Ninh"));
        list.add(new NameSearch(46, "Bình Dương"));
        list.add(new NameSearch(47, "Ninh Thuận"));
        list.add(new NameSearch(48, "Đồng Nai"));
        list.add(new NameSearch(49, "Hồ Chí Minh"));
        list.add(new NameSearch(50, "Long An"));
        list.add(new NameSearch(51, "Bình Thuận"));
        list.add(new NameSearch(52, "Đồng Tháp"));
        list.add(new NameSearch(53, "Tiền Giang"));
        list.add(new NameSearch(54, "An Giang"));
        list.add(new NameSearch(55, "Bà Rịa - Vũng Tàu"));
        list.add(new NameSearch(56, "Vĩnh Long"));
        list.add(new NameSearch(57, "Bến Tre"));
        list.add(new NameSearch(58, "Kiên Giang"));
        list.add(new NameSearch(59, "Cần Thơ"));
        list.add(new NameSearch(60, "Trà Vinh"));
        list.add(new NameSearch(61, "Hậu Giang"));
        list.add(new NameSearch(62, "Bạc Liêu"));
        list.add(new NameSearch(63, "Cà Mau"));
    }
}