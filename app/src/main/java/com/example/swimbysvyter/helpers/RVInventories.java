package com.example.swimbysvyter.helpers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.swimbysvyter.R;
import com.example.swimbysvyter.entity.Inventory;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.List;

public class RVInventories extends RecyclerView.Adapter {
    private List<Inventory> items;
    private ClickItemListener clickItemListener;


    public RVInventories(List<Inventory> items, ClickItemListener clickItemListener) {
        this.items = items;
        this.clickItemListener = clickItemListener;
    }

    public static class Holder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        SwitchMaterial name;

        Holder(View itemView, RVInventories parent) {
            super(itemView);
            name = itemView.findViewById(R.id.item_check_inventory);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            name.setChecked(!name.isChecked());
            clickAction(getAdapterPosition(), name.isChecked());
        }

        public void clickAction(int pos, boolean isChecked) {
        }
    }

    public void addInventory(Inventory inv) {
        items.add(inv);
        notifyDataSetChanged();
    }

    public void removeInventory(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_inventory, parent, false);
        Holder fh = new Holder(v, this) {

            @Override
            public void clickAction(int pos, boolean isChecked) {
                Inventory cur = items.get(pos);
                cur.setIsStock(isChecked);
                Log.i("RVInventory",String.format("Selected item: ",
                        pos,
                        cur.getId(),
                        cur.getName()));
                clickItemListener.clickItem(pos);
            }
        };

        return fh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).name.setText(items.get(position).getName());
        ((Holder) holder).name.setChecked(items.get(position).getIsStock());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
