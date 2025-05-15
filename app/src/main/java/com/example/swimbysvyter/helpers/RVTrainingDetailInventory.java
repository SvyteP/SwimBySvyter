package com.example.swimbysvyter.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.entity.Inventory;

import java.util.ArrayList;
import java.util.List;

public class RVTrainingDetailInventory extends RecyclerView.Adapter {
    private List<Inventory> items;
    private ClickItemListener clickItemListener;

    public RVTrainingDetailInventory(List<Inventory> items, ClickItemListener clickItemListener) {
        this.items = items;
        this.clickItemListener = clickItemListener;
    }

    public static class Holder extends  RecyclerView.ViewHolder implements AdapterView.OnClickListener{
        private TextView nameInv;

        public Holder(@NonNull View itemView, RVTrainingDetailInventory parent) {
            super(itemView);
            nameInv = itemView.findViewById(R.id.detail_training_inv_name);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            clickAction(pos);
        }
        public void clickAction(int pos){

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_inventory,parent,false);
        Holder h = new Holder(v,this){
            @Override
            public void clickAction(int pos) {
                clickItemListener.clickItem(pos);
            }
        };
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Holder holder = (Holder) viewHolder;
        Inventory curInventory = items.get(position);
        holder.nameInv.setText(curInventory.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
