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
import com.example.finalandroid.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewRoomDetailsApdater extends RecyclerView.Adapter<RecycleViewRoomDetailsApdater.RoomViewHolder>{
    private List<Room> mList;
    private ItemListener itemListener;

    public RecycleViewRoomDetailsApdater() {
        mList = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setmList(List<Room> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public Room getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public RecycleViewRoomDetailsApdater.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_details, parent, false);
        return new RecycleViewRoomDetailsApdater.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewRoomDetailsApdater.RoomViewHolder holder, int position) {
        Room room = mList.get(position);
        new ConfigGetData(holder.img)
                .execute(room.getImage());
        holder.tvTitle.setText(room.getName());
        holder.tvDis.setText(room.getDescribe());
        holder.tvPrice.setText(room.getPrice());
        holder.tvFloor.setText("Số phòng" + room.getFloor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView tvPrice, tvTitle, tvDis, tvStatus, tvFloor;

        public RoomViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.tvImage);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDis = view.findViewById(R.id.tvDis);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvFloor = view.findViewById(R.id.tvFloor);
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
