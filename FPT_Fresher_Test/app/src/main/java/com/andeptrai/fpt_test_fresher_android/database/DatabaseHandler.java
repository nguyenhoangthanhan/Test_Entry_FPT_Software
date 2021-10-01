package com.andeptrai.fpt_test_fresher_android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.andeptrai.fpt_test_fresher_android.database.INFO_DB.KEY_ID;
import static com.andeptrai.fpt_test_fresher_android.database.INFO_DB.KEY_KIND;
import static com.andeptrai.fpt_test_fresher_android.database.INFO_DB.KEY_NAME;
import static com.andeptrai.fpt_test_fresher_android.database.INFO_DB.KEY_PRICE;
import static com.andeptrai.fpt_test_fresher_android.database.INFO_DB.TABLE_NAME;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, INFO_DB.DATABASE_NAME, null, INFO_DB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createVehiclesTable = String.format("CREATE TABLE IF NOT EXISTS %s(%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)"
                , TABLE_NAME, KEY_ID, KEY_NAME, KEY_KIND, KEY_PRICE);
        sqLiteDatabase.execSQL(createVehiclesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_vehicles_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_vehicles_table);
        onCreate(sqLiteDatabase);
    }
}
