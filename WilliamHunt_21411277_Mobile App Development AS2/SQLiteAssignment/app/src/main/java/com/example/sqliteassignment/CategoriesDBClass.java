package com.example.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoriesDBClass extends SQLiteOpenHelper {

    private static int dbVersion = 1;
    private static String dbName = "CategoryTable";
    private static String dbTable = "tblCategory";
    private static String id = "CategoryID";
    public static String CategoryName = "CategoryName";
    private String ProductID = "ProductID";
    Context context;

    public CategoriesDBClass(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + CategoryName +
                " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(sqLiteDatabase);
    }


    public ArrayList<String> getCategoryNames(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + CategoryName + " FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));
            //Events e1 = new Events(coll1);
            item.add(String.valueOf(coll1));
            cursor.moveToNext();
        }
        return item;
    }
    public void BasketCheck(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " LIKE " + "'%Basket%'";
        //System.out.println(q);
        String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + "Basket"+"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.getCount() == 0){
            System.out.println("ADD BASKET CATEGORY");
            Events e1 = new Events("Basket");
            addEvents(e1);
        }
        cursor.close();
    }
    public void OrderCheck(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " LIKE " + "'%Basket%'";
        //System.out.println(q);
        String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + "Order"+"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.getCount() == 0){
            System.out.println("ADD Order CATEGORY");
            Events e1 = new Events("Order");
            addEvents(e1);
        }
        cursor.close();
    }
    public void ProfileCheck(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " LIKE " + "'%Basket%'";
        //System.out.println(q);
        String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + "Profile"+"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.getCount() == 0){
            System.out.println("ADD Profile CATEGORY");
            Events e1 = new Events("Profile");
            addEvents(e1);
        }
        cursor.close();
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryName, events.getEventName());
        db.insert(dbTable, null, values);
        db.close();

    }
    public boolean updateEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryName, events.getEventName());
        int endResult = db.update(dbTable, values, id + "=?", new String[]{String.valueOf(events.getId())});
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public void SearchCategoryName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Search = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = ''" + MainScreen.EditCategory+"''";
        Cursor cursor = db.rawQuery(Search,null);
        cursor.moveToFirst();
    }

    public Boolean SearchCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Search = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + MainScreen.AddCategories+"'";
        Cursor cursor = db.rawQuery(Search, null);
        if(cursor.getCount() == 0){
            return true;
        }
        else{
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
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + CategoryName +
                " TEXT)";
        db.execSQL(query);
    }


    public boolean deleteEvents(String deleteCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = CategoryName + " = \""+ deleteCategory + "\"";
        System.out.println(whereClause);
        int endResult = db.delete(dbTable, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<String> getAll(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + CategoryName + " FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));
            //Log.d("YEAH ", coll1);
            Events e1 = new Events(coll1);
            item.add(String.valueOf(e1));
            cursor.moveToNext();
        }
        return item;
    }

}
