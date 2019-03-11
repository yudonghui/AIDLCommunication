package com.ruihuo.processprovider;

import android.os.Parcel;
import android.os.Parcelable;

public class Pay implements Parcelable {
    private String name;
    private int num;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static final Creator<Pay> CREATOR = new Creator<Pay>() {
        @Override
        public Pay createFromParcel(Parcel in) {
            Pay pay = new Pay();
            pay.name = in.readString();
            pay.num = in.readInt();
            pay.phone = in.readString();
            return pay;
        }

        @Override
        public Pay[] newArray(int size) {
            return new Pay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(num);
        parcel.writeString(phone);
    }
}
