package com.example.swimbysvyter.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.dto.TrainingDto;

import java.util.List;
import java.util.StringJoiner;
import lombok.Data;
import lombok.Setter;

@Data
public class Training implements Parcelable {
    private Long id;
    private String name;
    private String warmUp;
    private String main;
    private String hitch;
    private boolean isCompleted;
    private boolean isLike;
    private List<Inventory> inventories;
    public static final Creator<Training> CREATOR = new Creator<>() {
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

    public Training(Long id, TrainingDto training, boolean isLike, boolean isCompleted) {
        this.id = id;
        this.name = training.name();
        this.warmUp = training.warmUp();
        this.main = training.mainTraining();
        this.hitch = training.hitch();
        this.inventories = training.inventories();
        this.isLike = isLike;
        this.isCompleted = isCompleted;
    }

    public Training(Long id, String name, String warmUp, String main, String hitch, List<Inventory> inventories, boolean isCompleted, boolean isLike) {
        this.id = id;
        this.name = name;
        this.warmUp = warmUp;
        this.main = main;
        this.hitch = hitch;
        this.isCompleted = isCompleted;
        this.isLike = isLike;
        this.inventories = inventories;
    }


    public Training(Parcel in) {
        id = in.readByte() == 0 ? null : in.readLong();
        name = in.readString();
        warmUp = in.readString();
        main = in.readString();
        hitch = in.readString();
        inventories = in.createTypedArrayList(Inventory.CREATOR);
        isLike = in.readBoolean();
        isCompleted = in.readBoolean();
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
        dest.writeBoolean(isLike);
        dest.writeBoolean(isCompleted);
    }

    public int getFavoriteIcon(){
        if (isLike){
            return R.drawable.ic_baseline_star_24;
        }
        return R.drawable.ic_baseline_star_border_24;
    }

    // Подгрузить иконки
    public int getCompletedIcon(){
        if (isCompleted()){
            return 0;
        }
        return 0;
    }
}
