package com.qiscus.internship.sudutnegeri.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vizyan on 02/01/18.
 */

public class Car implements Parcelable {
    private int id;
    private String year;
    private String make;
    private String model;
    private String imageUrl = "https://auto.ndtvimg.com/car-images/medium/hyundai/verna/hyundai-verna.jpg";

    protected Car(Parcel in) {
        id = in.readInt();
        year = in.readString();
        make = in.readString();
        model = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(year);
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(imageUrl);
    }
}
