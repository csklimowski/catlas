package teamchrisplus.catlas;

import teamchrisplus.model.DBRoom;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper{

    private static final String DATABASE = "CatlasDB";
    private static final String TABLE = "rooms";
    private static final String BUILDING = "building";
    private static final String FLOOR = "floor";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String COORDINATES = "coordinates";

    public DBManager(Context context)
    {
        super(context, DATABASE, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE+" (id integer primary key, "+BUILDING+" text, "+FLOOR+" integer, "+NAME+" text, "+NUMBER+" integer, "+COORDINATES+" text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //This method is used to insert a new room into the database
    public boolean insertRoom(DBRoom room){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(BUILDING, room.get_building());
            contentValues.put(FLOOR, room.get_floor());
            contentValues.put(NAME, room.get_name());
            contentValues.put(NUMBER, room.get_number());
            contentValues.put(COORDINATES, room.get_coordinates());
            db.insert(TABLE, null, contentValues);
            return true;
        }catch(Exception e){
            return false;
        }
    }


    //This method is used to insert a new room into the database
    public boolean updateRoom(DBRoom room){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(BUILDING, room.get_building());
            contentValues.put(FLOOR, room.get_floor());
            contentValues.put(NAME, room.get_name());
            contentValues.put(NUMBER, room.get_number());
            contentValues.put(COORDINATES, room.get_coordinates());
            db.update(TABLE, contentValues, "id = ?", new String[]{String.valueOf(room.get_id())});
            return true;
        }catch(Exception e){
            return false;
        }
    }


    //This method is used to retrieve all of the rooms from the database.
    // As of right now, it retrieves all of the rooms, but later it will retrieve
    //   only the rooms from a given building
    public ArrayList<DBRoom> getAllRooms(){
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<DBRoom> rooms = new ArrayList<DBRoom>();
        try {
            Cursor cur = db.rawQuery("select * from " + TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                DBRoom room = new DBRoom();
                room.set_id(cur.getInt(cur.getColumnIndex("id")));
                room.set_building(cur.getString(cur.getColumnIndex(BUILDING)));
                room.set_floor(cur.getInt(cur.getColumnIndex(FLOOR)));
                room.set_name(cur.getString(cur.getColumnIndex(NAME)));
                room.set_number(cur.getInt(cur.getColumnIndex(NUMBER)));
                room.set_coordinates(cur.getString(cur.getColumnIndex(COORDINATES)));
                rooms.add(room);
                cur.moveToNext();
            }
            return rooms;
        }catch(Exception e){
            System.out.println("ERROR selecting rooms from database");
            return null;
        }
    }



}
