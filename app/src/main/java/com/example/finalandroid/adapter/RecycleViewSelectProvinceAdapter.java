package com.example.finalandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.R;
import com.example.finalandroid.dal.SqlSearch;
import com.example.finalandroid.fragment.FragmentHome;
import com.example.finalandroid.model.NameSearch;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewSelectProvinceAdapter extends RecyclerView.Adapter<RecycleViewSelectProvinceAdapter.HotelViewHolder>{
    private List<NameSearch> mList;
    private ItemListener itemListener;
    private Context context;

    public RecycleViewSelectProvinceAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setmList(List<NameSearch> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public NameSearch getItemPos(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        NameSearch nameSearch = mList.get(position);
        holder.tvtitle.setText(nameSearch.getNameSearch());
        holder.tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, nameSearch.getNameSearch(), Toast.LENGTH_SHORT);
//                Intent intent = new Intent(context, FragmentHome.class);
//                intent.putExtra("nameSearch", nameSearch.getNameSearch());
//                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvtitle;

        public HotelViewHolder(@NonNull View view) {
            super(view);
            tvtitle = view.findViewById(R.id.tvTitle);
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
