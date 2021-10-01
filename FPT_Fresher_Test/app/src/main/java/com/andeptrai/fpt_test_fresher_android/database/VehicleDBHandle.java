package com.andeptrai.fpt_test_fresher_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.andeptrai.fpt_test_fresher_android.model.Vehicle;

import java.util.ArrayList;

public class VehicleDBHandle extends DatabaseHandler {

    public VehicleDBHandle(@Nullable Context context) {
        super(context);
    }

    public void addVehicle(Vehicle vehicle){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INFO_DB.KEY_ID, vehicle.getId());
        values.put(INFO_DB.KEY_NAME, vehicle.getName());
        values.put(INFO_DB.KEY_KIND, vehicle.getKind());
        values.put(INFO_DB.KEY_PRICE, vehicle.getPrice() + "");

        sqLiteDatabase.insert(INFO_DB.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public Vehicle getVehicle(String vehicleId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(INFO_DB.TABLE_NAME, null
                , INFO_DB.KEY_ID + " = ?", new String[]{vehicleId}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        long price = Long.parseLong(cursor.getString(3));
        Vehicle vehicle = new Vehicle(cursor.getString(0), cursor.getString(1), cursor.getString(2), price);
        return vehicle;
    }

    public ArrayList<Vehicle> getAllVehicles(){
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + INFO_DB.TABLE_NAME + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            long price = Long.parseLong(cursor.getString(3));
            Vehicle vehicle = new Vehicle(cursor.getString(0), cursor.getString(1), cursor.getString(2), price);
            vehicleArrayList.add(vehicle);
            cursor.moveToNext();
        }

        return vehicleArrayList;
    }

    public void updateVehicle(Vehicle vehicle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INFO_DB.KEY_NAME, vehicle.getName());
        values.put(INFO_DB.KEY_KIND, vehicle.getKind());
        values.put(INFO_DB.KEY_PRICE, vehicle.getPrice());

        db.update(INFO_DB.TABLE_NAME, values, INFO_DB.KEY_ID + " = ?", new String[]{vehicle.getId()});
        db.close();
    }

    public void deleteVehicle(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INFO_DB.TABLE_NAME, INFO_DB.KEY_ID + " = ?", new String[]{id});
        db.close();
    }
}
