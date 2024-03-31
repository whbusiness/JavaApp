package com.example.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbClass extends SQLiteOpenHelper {

    private static int dbVersion = 1;
    private static String dbName = "EventsManager";
    private static String dbTable = "tblEvents";
    private static String id = "id";
    private static String eventName = "EventName";
    private static String eventTime = "EventTime";
    private static String eventInformation = "EventInformation";
    private static String eventVenue = "EventVenue";
    private String TimeUpdated = "DateUpdated";
    private String profilePic = "ProfilePicture";
    private static Cursor cursor;
    Context context;
    public static Boolean CorrectLogin;

    public dbClass(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + eventName +
                " TEXT," +eventTime +" TEXT, " +eventInformation+" TEXT, " + eventVenue+ " TEXT, " +
                TimeUpdated + " TEXT, " + profilePic + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        onCreate(sqLiteDatabase);
    }
    public void CreateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "create table " + dbTable + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + eventName +
                " TEXT," +eventTime +" TEXT, " +eventInformation+" TEXT, " + eventVenue+ " TEXT, " +
                TimeUpdated + " TEXT, " + profilePic + " TEXT)";
        db.execSQL(query);
    }
    public void DropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + dbTable);
    }

    public ArrayList<String> getUserNames(){
        ArrayList<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + eventName + " FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(eventName));
            //Events e1 = new Events(coll1);
            item.add(String.valueOf(coll1));
            cursor.moveToNext();
        }
        return item;
    }
    public String getProfilePicture(){
        String returnValue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + eventTime + " FROM " + dbTable + " WHERE " + eventName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            returnValue = c.getString(c.getColumnIndexOrThrow(eventTime));
            c.moveToNext();
        }
        return returnValue;
    }

    public boolean CheckIfEmailExists(String strEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        String emailcheck = "SELECT * FROM " + dbTable + " WHERE " + eventName + " = '" + strEmail+"'";
        //String passwordCheck = "SELECT * FROM " + dbTable + " WHERE " + eventTime + " = '" + Login.strPassword+"'";
        Cursor c1 = db.rawQuery(emailcheck, null);
        if(c1.getCount()>0){
            Toast.makeText(context, "Email already in use", Toast.LENGTH_SHORT).show();
            System.out.println("FOUND EMAIL");
            return true;
        }
        else{
            System.out.println("Not Found");
            return false;
        }
    }

    public boolean CheckForLogin(){
        SQLiteDatabase db = this.getWritableDatabase();
        String emailAndPasswordCheck = "SELECT * FROM " + dbTable + " WHERE " + eventName + " = '" + Login.strEmail+"'" + " AND " + eventTime + " = '" + Login.strPassword+"'";
        //String passwordCheck = "SELECT * FROM " + dbTable + " WHERE " + eventTime + " = '" + Login.strPassword+"'";
        Cursor c1 = db.rawQuery(emailAndPasswordCheck, null);
        if(c1.getCount()>0){
            System.out.println("FOUND EMAIL AND PASSWORD");
            return true;
        }
        else{
            System.out.println("Not Found");
            return false;
        }
        //Cursor c2 = db.rawQuery(passwordCheck, null);
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.execSQL("delete from "+ dbTable);
        //String query = "SELECT " + eventName + " FROM " + dbTable + " WHERE " + eventName MainActivity.EventName;
        String q = "SELECT * FROM " + dbTable + " WHERE " + eventName + " = '" + RegisterActivity.EventName+"'";
        cursor = db.rawQuery(q, null);
        System.out.println("HELLO " + q);
        if(cursor.getCount()>0)//if email already exists
        {
            System.out.println("Email Exists");
            Toast.makeText(context, "Email already in use, please try a different one", Toast.LENGTH_SHORT).show();
        }
        else{//if email doesnt exist
            System.out.println("EMail Doesn't Exist");
            Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
            values.put(eventName, events.getEventName());
            values.put(eventInformation, events.getEventInformation());
            values.put(eventVenue, events.getEventVenue());
            values.put(eventTime, events.getEventTime());
            values.put(TimeUpdated, events.getTimeUpdated());
            values.put(profilePic, events.getProfilePic());
            db.insert(dbTable, null, values);
            db.close();
        }
        cursor.close();
    }
    public boolean updateEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventName, events.getEventName());
        values.put(eventInformation, events.getEventInformation());
        values.put(eventVenue, events.getEventVenue());
        values.put(eventTime, events.getEventTime());
        String whereClause = eventName + " = \""+ MainScreen.strUserName + "\"";
        int endResult = db.update(dbTable, values, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }
    public String getProfilePic(){
        String returnValue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + profilePic + " FROM " + dbTable + " WHERE " + eventName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            returnValue = c.getString(c.getColumnIndexOrThrow(profilePic));
            c.moveToNext();
        }
        return returnValue;
    }
    public String getPassword(){
        String returnValue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + eventTime + " FROM " + dbTable + " WHERE " + eventName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            returnValue = c.getString(c.getColumnIndexOrThrow(eventTime));
            c.moveToNext();
        }
        return returnValue;
    }
    public String getHobbies(){
        String returnValue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + eventInformation + " FROM " + dbTable + " WHERE " + eventName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            returnValue = c.getString(c.getColumnIndexOrThrow(eventInformation));
            c.moveToNext();
        }
        return returnValue;
    }
    public String getPostcode(){
        String returnValue = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + eventVenue + " FROM " + dbTable + " WHERE " + eventName + " ='" + MainScreen.UserNameText+"'";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            returnValue = c.getString(c.getColumnIndexOrThrow(eventVenue));
            c.moveToNext();
        }
        return returnValue;
    }
    public int updateEmail(String oldEmail, String newEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventName, newEmail);
        String whereClause = eventName + " = '" + oldEmail + "'";
        return db.update(dbTable, values, whereClause, null);
    }
    public int updateTimeUpdated(String timeUpdated)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TimeUpdated, timeUpdated);
        String whereClause = eventName + " = '" + MainScreen.UserNameText + "'";
        return db.update(dbTable, values, whereClause, null);
    }
    public int updateProfilePic(String oldPic, String newPic) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(profilePic, newPic);
        String whereClause = profilePic + " = '" + oldPic + "'" + " AND " + eventName + " = '" + MainScreen.UserNameText+"'";
        return db.update(dbTable, values, whereClause, null);
    }
    public int updatePassword(String oldPassword, String newPassword){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventTime, newPassword);
        String whereClause = eventTime + " = '" + oldPassword + "'" + " AND " + eventName + " = '" + MainScreen.UserNameText+"'";
        return db.update(dbTable, values, whereClause, null);
    }
    public int updateHobbies(String oldHobbies, String newHobbies){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventInformation, newHobbies);
        String whereClause = eventInformation + " = '" + oldHobbies + "'" + " AND " + eventName + " = '" + MainScreen.UserNameText+"'";
        return db.update(dbTable, values, whereClause, null);
    }
    public int updatePostcode(String oldPostcode, String newPostcode){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventVenue, newPostcode);
        String whereClause = eventVenue + " = '" + oldPostcode + "'" + " AND " + eventName + " = '" + MainScreen.UserNameText+"'";
        return db.update(dbTable, values, whereClause, null);
    }
    public boolean deleteEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = eventName + " = \""+ MainScreen.strUserName + "\"";
        int endResult = db.delete(dbTable, whereClause, null);
        if(endResult > 0){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<Events> getAll(){
        ArrayList<Events> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + dbTable;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            int ColumnID = cursor.getInt(cursor.getColumnIndexOrThrow(id));
            String coll1 = cursor.getString(cursor.getColumnIndexOrThrow(eventName));
            String coll2 = cursor.getString(cursor.getColumnIndexOrThrow(eventTime));
            String coll3 = cursor.getString(cursor.getColumnIndexOrThrow(eventInformation));
            String coll4 = cursor.getString(cursor.getColumnIndexOrThrow(eventVenue));
            Events e1 = new Events(ColumnID, coll1, coll2, coll3, coll4);
            item.add(e1);
            cursor.moveToNext();

        }
        return item;
    }
}
