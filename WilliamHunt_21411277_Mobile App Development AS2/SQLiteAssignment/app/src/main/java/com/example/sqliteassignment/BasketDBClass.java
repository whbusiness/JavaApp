package com.example.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BasketDBClass extends SQLiteOpenHelper {

    private static int dbVersion = 1;
    private static String dbName = "BasketTable";
    private static String dbTable = "tblBasket";
    private static String id = "id";
    private String UserName = "Email";
    private String ProductNames = "ProductNames";
    private String BasketPrice = "BasketPrice";
    Context context;

    public BasketDBClass(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + UserName
                + " TEXT, " + ProductNames + " TEXT, " + BasketPrice + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<String> getBasket(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String coll2 = cursor.getString(cursor.getColumnIndexOrThrow(ProductNames));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(BasketPrice));
            item.add(coll2);
            item.add(String.valueOf(coll3));
            cursor.moveToNext();
        }
        return item;
    }
    public ArrayList<String> getCurrentProducts(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String e = "SELECT " + ProductNames + " FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor cursor = db.rawQuery(e, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String coll2 = cursor.getString(cursor.getColumnIndexOrThrow(ProductNames));
            item.add(coll2);
            cursor.moveToNext();
        }
        return item;
    }
    public boolean UpdateBasketPrice(String productNames, int priceUpdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductNames, productNames);
        contentValues.put(BasketPrice, priceUpdate);
        String whereClause = UserName + " = \""+ MainScreen.UserNameText + "\"";
        int endResult = db.update(dbTable, contentValues, whereClause , null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public int getCurrentBasketPrice(){
        int coll2 = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String e = "SELECT " + BasketPrice + " FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor cursor = db.rawQuery(e, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            coll2 = cursor.getInt(cursor.getColumnIndexOrThrow(BasketPrice));
            cursor.moveToNext();
        }
        return coll2;
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("Create Basket");
        values.put(UserName, events.getUsername());
        values.put(ProductNames, events.getProductName());
        values.put(BasketPrice, events.getProductPrice());
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

    public boolean updateEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("Update Basket");
        values.put(UserName, events.getUsername());
        values.put(ProductNames, events.getProductName());
        values.put(BasketPrice, events.getProductPrice());
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

    public void DeleteRowUnderCurrentUsername(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'");
    }

    public void DropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + dbTable);
    }

    public void CreateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + UserName
                + " TEXT, " + ProductNames + " TEXT, " + BasketPrice + " INTEGER)";
        db.execSQL(query);
    }

    public void ClearRowUnderCurrentUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT * FROM " + dbTable + " WHERE " + UserName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        if(c.getCount()>0){
            String whereClause = UserName + " = \""+ MainScreen.UserNameText + "\"";
            db.delete(dbTable, whereClause, null);
        }
    }

    public boolean deleteEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = UserName + " = \""+ MainScreen.UserNameText + "\"";
        int endResult = db.delete(dbTable, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
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
    public String getProductNamesInBasket(String getProducts){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + ProductNames + " FROM " + dbTable + " WHERE " + UserName + " = '" + MainScreen.UserNameText+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            getProducts = cursor.getString(cursor.getColumnIndexOrThrow(ProductNames));
            System.out.println(getProducts);
            cursor.moveToNext();
        }
        return getProducts;
    }
    public ArrayList<String> getAll(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            int coll1 = cursor.getInt(cursor.getColumnIndexOrThrow(UserName));
            int coll2 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductNames));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(BasketPrice));
            item.add(String.valueOf(coll1));
            item.add(String.valueOf(coll2));
            item.add(String.valueOf(coll3));
            cursor.moveToNext();
        }
        return item;
    }

}
