package com.xiang.otcmedicinedelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiang.otcmedicinedelivery.R;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class PharmacyListAdapter extends RecyclerView.Adapter<PharmacyListAdapter.MyViewHolder> {

    private List<PharmacyModel> pharmacyModelList;
    private RestaurantListClickListener clickListener;

    public PharmacyListAdapter(List<PharmacyModel> pharmacyModelList, RestaurantListClickListener clickListener) {
        this.pharmacyModelList = pharmacyModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<PharmacyModel> pharmacyModelList) {
        this.pharmacyModelList = pharmacyModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restaurantName.setText(pharmacyModelList.get(position).getName());
        holder.restaurantAddress.setText("Address: "+ pharmacyModelList.get(position).getAddress());
//        holder.restaurantHours.setText("Today's hours: " + pharmacyModelList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(pharmacyModelList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(pharmacyModelList.get(position).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return pharmacyModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  restaurantName;
        TextView  restaurantAddress;
//        TextView  restaurantHours;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurantName);
            restaurantAddress = view.findViewById(R.id.restaurantAddress);
//            restaurantHours = view.findViewById(R.id.restaurantHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface RestaurantListClickListener {
        public void onItemClick(PharmacyModel pharmacyModel);
    }
}
