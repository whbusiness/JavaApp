package com.example.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductsDBClass extends SQLiteOpenHelper {

    private static int dbVersion = 1;
    private static String dbName = "ProductsTable";
    private static String dbTable = "tblProducts";
    private static String id = "id";
    public static String CategoryName = "ProductName";
    private String ProductDesc = "ProductDescription";
    private String ProductPrice = "ProductPrice";
    private String ProductListPrice = "ProductListPrice";
    private String ProductRetailPrice = "ProductRetailPrice";
    private String ProductDateCreated = "ProductDateCreated";
    private String ProductDateUpdated = "ProductDateUpdated";
    private String CategoryTblName = "CategoryName";
    Context context;

    public ProductsDBClass(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + CategoryName +
                " TEXT, " + ProductDesc + " TEXT, " + ProductPrice + " INTEGER, " +  ProductListPrice + " INTEGER, " + ProductRetailPrice +
                " INTEGER, " + ProductDateCreated + " TEXT, " + ProductDateUpdated + " TEXT, " + CategoryTblName
                + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(sqLiteDatabase);
    }

    public void CreateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE " + dbTable);
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + CategoryName +
                " TEXT, " + ProductDesc + " TEXT, " + ProductPrice + " INTEGER, " +  ProductListPrice + " INTEGER, " + ProductRetailPrice +
                " INTEGER, " + ProductDateCreated + " TEXT, " + ProductDateUpdated + " TEXT, " + CategoryTblName
                + " TEXT)";
        db.execSQL(query);
    }
    public void DropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + dbTable);
    }
    public void DelData(){
        SQLiteDatabase db = this.getWritableDatabase();

    }

    public boolean productExists(String strProduct){
        SQLiteDatabase db = this.getWritableDatabase();
        String productCheck = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + strProduct+"'";
        //String passwordCheck = "SELECT * FROM " + dbTable + " WHERE " + eventTime + " = '" + Login.strPassword+"'";
        Cursor c1 = db.rawQuery(productCheck, null);
        if(c1.getCount()>0){
            Toast.makeText(context, "Product already Exists", Toast.LENGTH_SHORT).show();
            System.out.println("FOUND Product");
            return true;
        }
        else{
            System.out.println("Not Found");
            return false;
        }
    }

    public ArrayList<String> getProductDetails(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + dbTable + " WHERE " + CategoryName + " = '" + MainScreen.SelectedProductName+"'";
        System.out.println(query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String productDesc = cursor.getString(cursor.getColumnIndexOrThrow(ProductDesc));
            int productPrice = cursor.getInt(cursor.getColumnIndexOrThrow(ProductPrice));
            int productListPrice = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListPrice));
            int productRetailPrice = cursor.getInt(cursor.getColumnIndexOrThrow(ProductRetailPrice));
            System.out.println("Desc " + productDesc + " Price " + productPrice + " ListPrice " + productListPrice + " RetailPrice " + productRetailPrice);
            item.add(String.valueOf(productDesc));
            item.add(String.valueOf(productPrice));
            item.add(String.valueOf(productListPrice));
            item.add(String.valueOf(productRetailPrice));
            cursor.moveToNext();
        }
        return item;
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryName, events.getProductName());
        values.put(ProductDesc, events.getProductDesc());
        values.put(ProductPrice, events.getProductPrice());
        values.put(ProductListPrice, events.getProductListPrice());
        values.put(ProductRetailPrice, events.getProductRetailPrice());
        values.put(ProductDateCreated, events.getProductDateCreated());
        values.put(ProductDateUpdated, events.getProductDateUpdated());
        values.put(CategoryTblName, events.getCategoryTblName());
        db.insert(dbTable, null, values);
        db.close();

    }
    public boolean updateEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryName, events.getProductName());
        values.put(ProductDesc, events.getProductDesc());
        values.put(ProductPrice, events.getProductPrice());
        values.put(ProductListPrice, events.getProductListPrice());
        values.put(ProductRetailPrice, events.getProductRetailPrice());
        values.put(ProductDateUpdated, events.getProductDateUpdated());
        values.put(CategoryTblName, events.getCategoryTblName());
        String whereClause = CategoryName + " = \""+ MainScreen.SelectedProductName + "\"";
        int endResult = db.update(dbTable, values, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
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

    public int GetProductsPrice(){
        int coll3 = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + ProductPrice + " FROM " + dbTable + " WHERE " + CategoryName + " = '" + MainScreen.SelectedProductItem+"'";
        System.out.println(q);
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductPrice));
            System.out.println("Get ProductPrice " + coll3);
            cursor.moveToNext();
        }
        return coll3;
    }
    public boolean deleteEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = CategoryName + " = \""+ MainScreen.SelectedProductName + "\"";
        int endResult = db.delete(dbTable, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<String> GetProductNames(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + CategoryName + " FROM " + dbTable;
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));
            System.out.println(coll1 + " YEAH ");
            //Events e1 = new Events(coll1);
            item.add(String.valueOf(coll1));
            cursor.moveToNext();
        }
        return item;
    }
    public ArrayList<String> GetProducts(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + dbTable + " WHERE " + CategoryTblName + " = '" + MainScreen.SelectedCategoryItem+"'";
        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));
            String coll2 = cursor.getString(cursor.getColumnIndexOrThrow(ProductDesc));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductPrice));
            //Events e1 = new Events(coll1);
            item.add(String.valueOf(coll1));
            item.add(String.valueOf(coll2));
            item.add(String.valueOf(coll3));
            cursor.moveToNext();
        }
        return item;
    }

    public ArrayList<String> getAll(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));
            String coll2 = cursor.getString(cursor.getColumnIndexOrThrow(ProductDesc));
            int coll3 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductPrice));
            int coll4 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListPrice));
            int coll5 = cursor.getInt(cursor.getColumnIndexOrThrow(ProductRetailPrice));
            String coll6 = cursor.getString(cursor.getColumnIndexOrThrow(ProductDateCreated));
            String coll7 = cursor.getString(cursor.getColumnIndexOrThrow(ProductDateUpdated));
            String coll8 = cursor.getString(cursor.getColumnIndexOrThrow(CategoryTblName));
            //System.out.println(coll1);
            Events e1 = new Events(coll1, coll2, coll3, coll4, coll5, coll6, coll7, coll8);
            item.add(String.valueOf(e1));
            cursor.moveToNext();
        }
        return item;
    }

}
