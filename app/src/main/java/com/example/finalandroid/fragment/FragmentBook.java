package com.example.finalandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.ItemHotelAcivity;
import com.example.finalandroid.R;
import com.example.finalandroid.adapter.RecycleViewHotelAdapter;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.dal.SqliteHelperHotel;
import com.example.finalandroid.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBook  extends Fragment implements RecycleViewHotelAdapter.ItemListener {

    private RecyclerView recyclerView;
    private RecycleViewHotelAdapter adapter;
    private List<Hotel> list;
    private RecycleViewHotelAdapter.ItemListener itemListener;
    private SqliteHelperHotel sqliteHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        sqliteHelper = new SqliteHelperHotel(getContext());
        adapter = new RecycleViewHotelAdapter();
        itemListener = this;
        adapter.setItemListener(itemListener);
        getListHotel();
        getHotelNearUser();
    }



    private void getHotelNearUser() {
        list = sqliteHelper.searchByAddress("Hà Nội");
        adapter.setmList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Hotel hotel = adapter.getItemPos(position);
        Intent intent = new Intent(getActivity(), ItemHotelAcivity.class);
        intent.putExtra("hotel", hotel);
        startActivity(intent);
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        getHotelNearUser();
//    }


    public void getListHotel(){
        ProgressDialogCustom progressDialogCustom = new ProgressDialogCustom(getContext());
        progressDialogCustom.show();
        ApiService.apiService.getHotel().enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                progressDialogCustom.hide();
                list = response.body();
                if(list != null){
                    deleteAllHotel();
                    for(Hotel item : list){
                        sqliteHelper.addHotel(item);
                    }
                }
                //adapter = new RecycleViewHotelAdapter();
                //adapter.setmList(list);
                //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                //recyclerView.setLayoutManager(linearLayoutManager);
                //recyclerView.setAdapter(adapter);
                //adapter.setItemListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                progressDialogCustom.hide();
                Toast.makeText(getContext(), "Call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAllHotel(){
        List<Hotel> getAll = sqliteHelper.getAllHotel();
        for(Hotel hotel : getAll){
            sqliteHelper.deleteAllHotel(hotel.getId());
        }
    }

}
