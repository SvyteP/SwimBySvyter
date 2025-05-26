package com.example.swimbysvyter.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.Data;

@Data
public class Inventory implements Parcelable {
    private Long id;
    private String name;
    private Boolean isStock;

    public static final Creator<Inventory> CREATOR = new Creator<>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

    public Inventory() {
    }

    public Inventory(Long id, String name, Boolean isStock) {
        this.id = id;
        this.name = name;
        this.isStock = isStock;
    }

    public Inventory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        isStock = in.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (id != null ? 1 : 0)); // Добавляем проверку null
        if (id != null) {
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeBoolean(isStock);
    }
}
