package com.example.finalandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.activity.hotel.ItemHotelAcivity;
import com.example.finalandroid.R;
import com.example.finalandroid.activity.hotel.SearchHotelActivity;
import com.example.finalandroid.activity.user.SearchProvince;
import com.example.finalandroid.adapter.RecycleViewHotelAdapter;
import com.example.finalandroid.adapter.RecycleViewHotelHomeAdapter;
import com.example.finalandroid.adapter.RecycleViewHotelHomeStarAdapter;
import com.example.finalandroid.adapter.RecycleViewHotelManyLikeAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.dal.SqliteHelperHotel;
import com.example.finalandroid.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment implements RecycleViewHotelManyLikeAdapter.ItemListener, RecycleViewHotelHomeAdapter.ItemListener, RecycleViewHotelHomeStarAdapter.ItemListener{

    private List<Hotel> list;
    private List<Hotel> listStar;
    private SqliteHelperHotel sqliteHelper;
    private ImageButton btSearch;
    private RecyclerView recyclerView, recyclerView1, recycleViewLike;
    private RecycleViewHotelHomeAdapter adapterSearch;
    private RecycleViewHotelHomeStarAdapter adapterStar;
    private RecycleViewHotelManyLikeAdapter adapterLike;
    private TextView idNameProvince;
    private RecycleViewHotelHomeAdapter.ItemListener itemListener;
    private RecycleViewHotelHomeStarAdapter.ItemListener itemListenerStar;
    private RecycleViewHotelManyLikeAdapter.ItemListener itemListenerLike;
    private TextView tvlistHotel, tvlistStar;
    private int i = R.drawable.two;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqliteHelper = new SqliteHelperHotel(getContext());
        itemListener = this;
        itemListenerStar = this;
        itemListenerLike = this;

        LinearLayout app_layer = (LinearLayout) view.findViewById (R.id.selectedProvince);
        app_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchProvince.class);
                startActivity(intent);
            }
        });
//        /getListHotel();
        getIntent();
        initView(view);
        getListHotel();
        getListHotelStar();
        getListHotelLike();
    }

    private void getListHotelLike() {
        ApiService.apiService.getListHotelLike().enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if(response.body() != null){
                    adapterLike = new RecycleViewHotelManyLikeAdapter();
                    List<Hotel> hotelList = response.body().subList(0, 2);
                    adapterLike.setmList(hotelList);
                    adapterLike.setItemListener(itemListenerLike);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recycleViewLike.setLayoutManager(linearLayoutManager);
                    recycleViewLike.setAdapter(adapterLike);
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {

            }
        });
    }

    private void getListHotelStar() {
        List<Hotel> hotelList = sqliteHelper.getAllHotel();
        listStar = new ArrayList<>();
        for(Hotel item : hotelList){
            if(Float.valueOf(item.getStar()) > 4.5){
                listStar.add(item);
            }
        }
        tvlistStar.setText(String.valueOf(listStar.size()) + " đánh giá tốt nhất");
        adapterStar = new RecycleViewHotelHomeStarAdapter();
        adapterStar.setmList(listStar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(adapterStar);
        adapterStar.setItemListener(itemListenerStar);
    }

    private void getIntent() {
        try {
//            String name = getActivity().getIntent().getExtras().getString("nameSearch");
//            if(name != null)
//            idNameProvince.setText(name);
        }
        catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

    private void initView(View view) {
        btSearch = view.findViewById(R.id.btSearch);
        idNameProvince = view.findViewById(R.id.idNameProvince);
        recyclerView = view.findViewById(R.id.recycleViewNear);
        recyclerView1 = view.findViewById(R.id.recycleViewStar);
        tvlistHotel = view.findViewById(R.id.tvlistHotel);
        tvlistStar = view.findViewById(R.id.tvlistStar);
        recycleViewLike = view.findViewById(R.id.recycleViewLike);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchHotelActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Hotel hotel = adapterSearch.getItemPos(position);
        Intent intent = new Intent(getActivity(), ItemHotelAcivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    public void getListHotel(){
        list = sqliteHelper.searchByAddress("Hà Nội");
        tvlistHotel.setText(String.valueOf(list.size()) + " khách sạn gần bạn");
        adapterSearch = new RecycleViewHotelHomeAdapter();
        adapterSearch.setmList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterSearch);
        adapterSearch.setItemListener(itemListener);
    }

    @Override
    public void onItemClickHotel(View view, int position) {
        Hotel hotel = adapterStar.getItemPos(position);
        Intent intent = new Intent(getActivity(), ItemHotelAcivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }

    @Override
    public void onItemClickHotelLike(View view, int position) {
        Hotel hotel = adapterLike.getItemPos(position);
        Intent intent = new Intent(getActivity(), ItemHotelAcivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }
}
