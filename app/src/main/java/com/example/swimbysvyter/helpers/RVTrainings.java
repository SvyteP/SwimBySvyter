package com.example.swimbysvyter.helpers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.entity.Training;

import java.util.ArrayList;
import java.util.List;

public class RVTrainings extends RecyclerView.Adapter {

    private List<Training> items;
    private ClickItemListener clickItemListener;

    public RVTrainings(MutableLiveData<List<Training>> items,ClickItemListener clickItemListener) {
        List<Training> mItems = items.getValue();
        this.clickItemListener = clickItemListener;
        if (mItems != null) {
            this.items = mItems;
        }else {
            Log.e("RVTrainings","Constructor error: mItems null");
        }

    }

    public static class Holder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        TextView nameTraining;
        TextView inventories;
        ImageView arrowImg;

        Holder(View itemView, RVTrainings parent) {
            super(itemView);
            nameTraining =  itemView.findViewById(R.id.name_training_txt);
            inventories = itemView.findViewById(R.id.inventory_txt);
            arrowImg = itemView.findViewById(R.id.arrow_img);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.i("RVTrainings Holder onClick pos:",Integer.toString(pos));
            clickAction(pos);
        }

        public void clickAction(int pos) {
            //override
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training, parent, false);
        Holder fh = new Holder(v, this) {

            @Override
            public void clickAction(int pos) {
                Training cur = items.get(pos);
                Log.i("onCreateViewHolder", String.format("Selected item: ",
                        pos,
                        cur.getId(),
                        cur.getName(),
                        cur.getInventories().toString()));
                clickItemListener.clickItem(pos);
            }
        };
        return fh;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Holder holder = (Holder) viewHolder;
        Training curTraining = items.get(position);
        holder.nameTraining.setText(curTraining.getName());
        holder.inventories.setText(curTraining.getInventoriesStr());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
