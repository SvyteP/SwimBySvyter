package com.example.swimbysvyter.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import lombok.Data;

@Data
public class Training implements Parcelable {
    private Long id;
    private String name;
    private String warmUp;
    private String main;
    private String hitch;
    private List<Inventory> inventories;
    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

    public Training() {
    }

    public Training(Long id, String name, String warmUp, String main, String hitch, List<Inventory> inventories) {
        this.id = id;
        this.name = name;
        this.warmUp = warmUp;
        this.main = main;
        this.hitch = hitch;
        this.inventories = inventories;
    }

    public Training(Parcel in) {
        id = in.readByte() == 0 ? null : in.readLong();
        name = in.readString();
        warmUp = in.readString();
        main = in.readString();
        hitch = in.readString();
        inventories = in.createTypedArrayList(Inventory.CREATOR);
    }

    public String getInventoriesStr(){
        StringJoiner joiner = new StringJoiner(",");
        inventories.forEach(n ->{
            joiner.add(n.getName());
        });
        return joiner.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (id != null ? 1 : 0));
        if (id != null) {
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(warmUp);
        dest.writeString(main);
        dest.writeString(hitch);
        dest.writeTypedList(inventories);
    }
}
