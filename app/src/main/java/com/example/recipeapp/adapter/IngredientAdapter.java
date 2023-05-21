package com.example.recipeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.model.IngredientModel;

import java.util.ArrayList;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private ArrayList<IngredientModel> listdata;

    public IngredientAdapter(ArrayList<IngredientModel> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ingredientitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @NonNull


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         IngredientModel myListData =  listdata.get(position);
        holder.ingredientName.setText(myListData.getIngredientName());
        holder.ingredientQuantity.setText(myListData.getIngredinetQuantity());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public TextView ingredientQuantity;
        public ViewHolder(View itemView) {
            super(itemView);
            this.ingredientQuantity = (TextView) itemView.findViewById(R.id.ingredientQuantity);
            this.ingredientName = (TextView) itemView.findViewById(R.id.ingredientName);
        }
    }
}
