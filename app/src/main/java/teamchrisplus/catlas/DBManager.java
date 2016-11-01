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
        populate();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private boolean populate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(getAllRooms().size() == 0) {
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 906", 906, "2060 812 2368 1310"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 918", 918, "1555 1139 1934 1310"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 934", 934, "600 1139 916 1310"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 938", 938, "600 971 916 1139"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 942", 942, "600 815 916 971"));
            db.close();
        }else{
            db.close();
            return false;
        }
        return true;
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
            db.close();
            return true;
        }catch(Exception e){
            db.close();
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
            db.close();
            return true;
        }catch(Exception e){
            db.close();
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
            db.close();
            return rooms;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting rooms from database");
            return null;
        }
    }



}
