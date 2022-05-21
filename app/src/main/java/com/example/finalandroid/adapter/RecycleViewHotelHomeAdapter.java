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

import java.util.ArrayList;
import java.util.List;

public class RecycleViewHotelHomeAdapter extends RecyclerView.Adapter<RecycleViewHotelHomeAdapter.HotelViewHolder>{
    private List<Hotel> mList;
    private ItemListener itemListener;

    public RecycleViewHotelHomeAdapter() {
        mList = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
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
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_home, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = mList.get(position);
        new ConfigGetData(holder.img)
                .execute(hotel.getImage());
        holder.tvTitle.setText(hotel.getName());
        holder.tvPrice.setText("150.000đ");
        holder.tvAddress.setText(hotel.getAddress());
        holder.idStar.setText(hotel.getStar());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView tvTitle, tvAddress, tvPrice, idStar;

        public HotelViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.tvImage);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvAddress = view.findViewById(R.id.tvAddress);
            idStar = view.findViewById(R.id.idStar);
            tvTitle = view.findViewById(R.id.tvTitle);
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
