package com.example.finalandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.R;
import com.example.finalandroid.custom.ConfigGetData;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.NameSearch;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewResultSearchAdapter extends RecyclerView.Adapter<RecycleViewResultSearchAdapter.HotelViewHolder>{
    private List<Hotel> mList;
    private RecycleViewResultSearchAdapter.ItemListener itemListener;

    public RecycleViewResultSearchAdapter() {
        mList = new ArrayList<>();
    }

    public void setItemListener(RecycleViewResultSearchAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setmList(List<Hotel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public Hotel getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public RecycleViewResultSearchAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_search, parent, false);
        return new RecycleViewResultSearchAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewResultSearchAdapter.HotelViewHolder holder, int position) {
        Hotel hotel = mList.get(position);
        holder.tvTitle.setText(hotel.getName());
        holder.tvAddress.setText(hotel.getAddress());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle, tvAddress;

        public HotelViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvAddress = view.findViewById(R.id.tvAddress);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
