package com.example.lab5_alarm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class Alarm implements Parcelable {
    int hour, min;

    boolean am;

    boolean[] days = new boolean[7];

    String desc;

    boolean able;

    long uuid;

    public Alarm(int hour, int min, boolean am, boolean[] days, String desc) {
        this.hour = hour;
        this.min = min;
        this.am = am;
        this.days = days;
        this.desc = desc;
        this.able = true;
        this.uuid = UUID.randomUUID().variant();
    }

    public boolean isAble() {
        return able;
    }

    public void setAble(boolean able) {
        this.able = able;
    }

    public void updateDays(int dayOfWeek, boolean on) {
        if (dayOfWeek < 0 && dayOfWeek > 7) return;
        days[dayOfWeek] = on;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isAm() {
        return am;
    }

    public void setAm(boolean am) {
        this.am = am;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hour);
        dest.writeInt(this.min);
        dest.writeByte(this.am ? (byte) 1 : (byte) 0);
        dest.writeBooleanArray(this.days);
        dest.writeString(this.desc);
        dest.writeByte(this.able ? (byte) 1 : (byte) 0);
        dest.writeLong(this.uuid);
    }

    public void readFromParcel(Parcel source) {
        this.hour = source.readInt();
        this.min = source.readInt();
        this.am = source.readByte() != 0;
        this.days = source.createBooleanArray();
        this.desc = source.readString();
        this.able = source.readByte() != 0;
        this.uuid = source.readLong();
    }

    protected Alarm(Parcel in) {
        this.hour = in.readInt();
        this.min = in.readInt();
        this.am = in.readByte() != 0;
        this.days = in.createBooleanArray();
        this.desc = in.readString();
        this.able = in.readByte() != 0;
        this.uuid = in.readLong();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel source) {
            return new Alarm(source);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
