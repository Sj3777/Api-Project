package com.example.foodapiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    LayoutInflater inflater;
    List<Food> foodList;
    List<Food> foodListAll;


    public Adapter(Context context, List<Food> food){
        this.inflater=LayoutInflater.from(context);
        this.foodList=food;
        this.foodListAll=new ArrayList<>(foodList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodName.setText(foodList.get(position).getFoodName());
        holder.foodDesc.setText(foodList.get(position).getFoodDesc());
        Picasso.get().load(foodList.get(position).getFoodImage()).into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Food> filterlist=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filterlist.addAll(foodList);
            }else{
                for(Food foodName: foodListAll){
                    if(foodName.getFoodName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterlist.add(foodName);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;
        }
        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodList.clear();
            foodList.addAll((Collection) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends  RecyclerView.ViewHolder{
         TextView foodName,foodDesc;
         CircleImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName=itemView.findViewById(R.id.food_name);
            foodDesc=itemView.findViewById(R.id.food_desc);
            foodImage=itemView.findViewById(R.id.food_img);
        }
    }
}
