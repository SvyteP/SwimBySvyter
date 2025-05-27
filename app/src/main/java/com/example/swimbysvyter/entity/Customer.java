package com.example.swimbysvyter.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import lombok.Data;

@Data
public class Customer implements Parcelable {
    private String login;
    private String email;
    private String token;

    public Customer() {}
    public Customer(String login, String email) {
        this.login = login;
        this.email = email;

    }

    public Customer(String login, String email, String token) {
        this.login = login;
        this.email = email;
        this.token = token;
    }

    public Customer(JSONObject object) {
        this.login = object.optString("login");
        this.email = object.optString("email");
        this.token = object.optString("token");
    }

    protected Customer(Parcel in) {
        login = in.readString();
        email = in.readString();

    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(email);
    }
}
