package com.example.taxfileapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "company_name")
    public String companyName;

    @ColumnInfo(name = "process_status")
    public String processStatus;

    public User(String fullName, String phone, String city, String companyName, String processStatus) {
        this.fullName = fullName;
        this.phone = phone;
        this.city = city;
        this.companyName = companyName;
        this.processStatus = processStatus;
    }

    protected User(Parcel in) {
        uid = in.readInt();
        fullName = in.readString();
        phone = in.readString();
        city = in.readString();
        companyName = in.readString();
        processStatus = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(fullName);
        dest.writeString(phone);
        dest.writeString(city);
        dest.writeString(companyName);
        dest.writeString(processStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
