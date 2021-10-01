package com.andeptrai.fpt_test_fresher_android.model;

import java.io.Serializable;
import java.util.Comparator;

public class Vehicle implements Serializable {

    private String id;
    private String name;
    private String kind;
    private long price;

    public Vehicle(String id, String name, String kind, long price) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    //arrange list a->z
    public static Comparator<Vehicle> vehicleComparatorAZ = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle vehicle, Vehicle t1) {
            String name1 = vehicle.getName();
            String name2 = t1.getName();
            return name1.toLowerCase().compareTo(name2.toLowerCase());
        }
    };

    //arrange list z->a
    public static Comparator<Vehicle> vehicleComparatorZA = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle vehicle, Vehicle t1) {
            String name1 = vehicle.getName();
            String name2 = t1.getName();
            return name2.toLowerCase().compareTo(name1.toLowerCase());
        }
    };

    //arrange ascending price
    public static Comparator<Vehicle> vehicleComparatorAscending = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle vehicle, Vehicle t1) {
            int price1 = (int) vehicle.getPrice();
            int price2 = (int) t1.getPrice();
            return price1 - price2;
        }
    };

    //arrange descending price
    public static Comparator<Vehicle> vehicleComparatorDescending = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle vehicle, Vehicle t1) {
            int price1 = (int) vehicle.getPrice();
            int price2 = (int) t1.getPrice();
            return price2 - price1;
        }
    };

}
