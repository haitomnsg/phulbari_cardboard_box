package com.haitomns.phulbari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyFlowerAdapter extends RecyclerView.Adapter<MyFlowerAdapter.MyFlowerViewHolder> {
    private Context context;
    private List<MyFlowerModel> flowerList;

    public MyFlowerAdapter(Context context, List<MyFlowerModel> flowerList) {
        this.context = context;
        this.flowerList = flowerList;
    }

    @Override
    public MyFlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myflower_item, parent, false);
        return new MyFlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFlowerViewHolder holder, int position) {
        MyFlowerModel flower = flowerList.get(position);
        holder.flowerNameTextView.setText(flower.getFlowerName());
        holder.waterRequirementTextView.setText(flower.getWaterRequirement());
        holder.sunlightRequirementTextView.setText(flower.getSunlightRequirement());
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public static class MyFlowerViewHolder extends RecyclerView.ViewHolder {
        TextView flowerNameTextView;
        TextView waterRequirementTextView;
        TextView sunlightRequirementTextView;

        public MyFlowerViewHolder(View itemView) {
            super(itemView);
            flowerNameTextView = itemView.findViewById(R.id.flower_name);
            waterRequirementTextView = itemView.findViewById(R.id.water_requirement);
            sunlightRequirementTextView = itemView.findViewById(R.id.sunlight_requirement);
        }
    }
}
