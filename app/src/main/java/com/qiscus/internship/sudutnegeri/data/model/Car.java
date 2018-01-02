package com.qiscus.internship.sudutnegeri.data.model;

/**
 * Created by vizyan on 02/01/18.
 */

public class Car {
    private int id;
    private String year;
    private String make;
    private String model;
    private String imageUrl = "https://auto.ndtvimg.com/car-images/medium/hyundai/verna/hyundai-verna.jpg";

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
}
