package com.example.finalandroid.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.finalandroid.R;
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
        itemListener = (RecycleViewSelectProvinceAdapter.ItemListener) this;
        recyclerView = findViewById(R.id.recycleViewSearch);
        getListProvince();
        btBack = findViewById(R.id.btBack);
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
        list.add(new NameSearch(1, "H?? N???i"));
        list.add(new NameSearch(2, "B???c Ninh"));
        list.add(new NameSearch(3, "V??nh Ph??c"));
        list.add(new NameSearch(4, "H??ng Y??n"));
        list.add(new NameSearch(5, "B???c Giang"));
        list.add(new NameSearch(6, "H???i D????ng"));
        list.add(new NameSearch(7, "H??a B??nh"));
        list.add(new NameSearch(8, "H?? Nam"));
        list.add(new NameSearch(9, "Th??i Nguy??n"));
        list.add(new NameSearch(10, "Nam ?????nh"));
        list.add(new NameSearch(11, "Ph?? Th???"));
        list.add(new NameSearch(12, "Th??i B??nh"));
        list.add(new NameSearch(13, "Ninh B??nh"));
        list.add(new NameSearch(14, "Ninh B??nh"));
        list.add(new NameSearch(15, "H???i Ph??ng"));
        list.add(new NameSearch(16, "Tuy??n Quang"));
        list.add(new NameSearch(17, "B???c K???n"));
        list.add(new NameSearch(18, "Qu???ng Ninh"));
        list.add(new NameSearch(19, "Y??n B??i"));
        list.add(new NameSearch(20, "Thanh H??a"));
        list.add(new NameSearch(21, "L???ng S??n"));
        list.add(new NameSearch(22, "Qu???ng B??nh"));
        list.add(new NameSearch(23, "Cao B???ng"));
        list.add(new NameSearch(24, "S??n La"));
        list.add(new NameSearch(25, "H?? Giang"));
        list.add(new NameSearch(26, "L??o Cai"));
        list.add(new NameSearch(27, "Ngh??? An"));
        list.add(new NameSearch(28, "Lai Ch??u"));
        list.add(new NameSearch(29, "??i???n Bi??n"));
        list.add(new NameSearch(30, "H?? T??nh"));
        list.add(new NameSearch(31, "Qu???ng B??nh"));
        list.add(new NameSearch(32, "Qu???ng Tr???"));
        list.add(new NameSearch(33, "Th???a Thi??n Hu???"));
        list.add(new NameSearch(34, "???? N???ng"));
        list.add(new NameSearch(35, "Qu???ng Nam"));
        list.add(new NameSearch(36, "Qu???ng Ng??i"));
        list.add(new NameSearch(37, "Kon Tum"));
        list.add(new NameSearch(38, "Gia Lai"));
        list.add(new NameSearch(39, "B??nh ?????nh"));
        list.add(new NameSearch(40, "?????c Lak"));
        list.add(new NameSearch(41, "Ph?? Y??n"));
        list.add(new NameSearch(42, "?????c N??ng"));
        list.add(new NameSearch(43, "Kh??nh H??a"));
        list.add(new NameSearch(44, "L??m ?????ng"));
        list.add(new NameSearch(45, "T??y Ninh"));
        list.add(new NameSearch(46, "B??nh D????ng"));
        list.add(new NameSearch(47, "Ninh Thu???n"));
        list.add(new NameSearch(48, "?????ng Nai"));
        list.add(new NameSearch(49, "H??? Ch?? Minh"));
        list.add(new NameSearch(50, "Long An"));
        list.add(new NameSearch(51, "B??nh Thu???n"));
        list.add(new NameSearch(52, "?????ng Th??p"));
        list.add(new NameSearch(53, "Ti???n Giang"));
        list.add(new NameSearch(54, "An Giang"));
        list.add(new NameSearch(55, "B?? R???a - V??ng T??u"));
        list.add(new NameSearch(56, "V??nh Long"));
        list.add(new NameSearch(57, "B???n Tre"));
        list.add(new NameSearch(58, "Ki??n Giang"));
        list.add(new NameSearch(59, "C???n Th??"));
        list.add(new NameSearch(60, "Tr?? Vinh"));
        list.add(new NameSearch(61, "H???u Giang"));
        list.add(new NameSearch(62, "B???c Li??u"));
        list.add(new NameSearch(63, "C?? Mau"));

        adapter = new RecycleViewSelectProvinceAdapter(this);
        adapter.setItemListener(itemListener);
        adapter.setmList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}