package com.example.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OrderDBClass extends SQLiteOpenHelper {

    private static int dbVersion = 1;
    private static String dbName = "OrderTable";
    private static String dbTable = "tblOrder";
    private static String id = "id";
    private String UserName = "Email";
    private String Products = "Products";
    private String OrderPrice = "OrderPrice";
    Context context;

    public OrderDBClass(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + UserName
                + " TEXT, " + Products + " TEXT, " + OrderPrice + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<String> getAllOrdersUnderCurrentUser(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String ProductsList = cursor.getString(cursor.getColumnIndexOrThrow(Products));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(OrderPrice));
            item.add(ProductsList);
            item.add(String.valueOf(coll3));
            cursor.moveToNext();
        }
        return item;
    }

    public ArrayList<String> getAllOrders(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + dbTable;
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String UserNames = cursor.getString(cursor.getColumnIndexOrThrow(UserName));
            String ProductsList = cursor.getString(cursor.getColumnIndexOrThrow(Products));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(OrderPrice));
            item.add(UserNames);
            item.add(ProductsList);
            item.add(String.valueOf(coll3));
            cursor.moveToNext();
        }
        return item;
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("Create Basket");
        values.put(UserName, events.getUsername());
        values.put(Products, events.getProductName());
        values.put(OrderPrice, events.getProductPrice());
        db.insert(dbTable, null, values);
        db.close();
    }

    public boolean CheckForUserExist(){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        if(c.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean updateEmail(String strEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        String emailAndPasswordCheck = "SELECT * FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        //String passwordCheck = "SELECT * FROM " + dbTable + " WHERE " + eventTime + " = '" + Login.strPassword+"'";
        Cursor c1 = db.rawQuery(emailAndPasswordCheck, null);
        if(c1.getCount()>0){
            ContentValues values = new ContentValues();
            values.put(UserName, strEmail);
            String whereClause = UserName + " = \""+ MainScreen.UserNameText + "\"";
            int endResult = db.update(dbTable, values, whereClause, null);
            if(endResult>0) {
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean updateEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("Update Basket");
        values.put(UserName, events.getUsername());
        values.put(Products, events.getProductName());
        values.put(OrderPrice, events.getProductPrice());
        String whereClause = UserName + " = \""+ MainScreen.UserNameText + "\"";
        int endResult = db.update(dbTable, values, whereClause, null);
        if(endResult > 0){
            System.out.println("Worked");
            return true;
        }else{
            System.out.println("Didnt Work");
            return false;
        }
    }

    /*public void FindAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Search = "SELECT " + CategoryName + " FROM " + dbTable;
        Cursor cursor = db.rawQuery(Search, null);
        cursor.moveToFirst();
    }*/

    public void DeleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ dbTable);
    }

    public void DropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + dbTable);
    }

    public void CreateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + UserName
                + " TEXT, " + Products + " TEXT, " + OrderPrice + " INTEGER)";
        db.execSQL(query);
    }

    public boolean deleteEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = UserName + " = \""+ MainScreen.strUserName + "\"";
        int endResult = db.delete(dbTable, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }
}
