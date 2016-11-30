package teamchrisplus.catlas;

import teamchrisplus.model.DBRoom;
import teamchrisplus.model.FloorGraph;
import teamchrisplus.model.FloorNode;
import teamchrisplus.model.Floor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper{

    private static final String DATABASE = "CatlasDB";


    //Database tables and their fields
    private static final String TABLE = "rooms";
    private static final String BUILDING = "building";
    private static final String FLOOR = "floor";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String COORDINATES = "coordinates";

    private static final String NODE_TABLE = "nodes";
    private static final String NODE_BUILDING = "building";
    private static final String NODE_FLOOR = "floor";
    private static final String NODE_ID = "number";
    private static final String NODE_COORDINATES = "coordinates";

    private static final String ADJ_TABLE = "adjacencies";
    private static final String ADJ_BUILDING = "building";
    private static final String ADJ_FLOOR = "floor";
    private static final String ADJ_NODE_ONE = "node1";
    private static final String ADJ_NODE_TWO = "node2";

    private static final String BUILDING_TABLE = "buildings";
    private static final String BUILDING_BUILDING = "building";
    private static final String BUILDING_FLOOR = "floor";
    private static final String BUILDING_ABREVIATION = "abreviation";
    private static final String BUILDING_IMAGE = "image";

    //Constructor
    public DBManager(Context context)
    {
        super(context, DATABASE, null, 1);
    }


    //This method is called when a DBManager object is created
    //  Creates tables if they are missing in the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE+" (id integer primary key, "+BUILDING+" text, "+FLOOR+" integer, "+NAME+" text, "+NUMBER+" integer, "+COORDINATES+" text)");
        db.execSQL("create table if not exists "+NODE_TABLE+" (id integer primary key, "+NODE_BUILDING+" text, "+NODE_FLOOR+" integer, "+NODE_ID+" integer, "+NODE_COORDINATES+" text)");
        db.execSQL("create table if not exists "+ADJ_TABLE+" (id integer primary key, "+ADJ_BUILDING+" text, "+ADJ_FLOOR+" integer, "+ADJ_NODE_ONE+" integer, "+ADJ_NODE_TWO+" integer)");
        db.execSQL("create table if not exists "+BUILDING_TABLE+" (id integer primary key, "+BUILDING_BUILDING+" text, "+BUILDING_FLOOR+" integer, "+BUILDING_ABREVIATION+" text, "+BUILDING_IMAGE+" text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean populate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("There are " + getAllRooms().size() + " rooms in the database");
        if(getAllRooms().size() < 1) {
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


    //This method is used to insert a new room into the rooms table
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
            System.out.println("There was an error inserting into the room table");
            return false;
        }
    }


    //This method is used to insert new nodes into the node table
    public boolean insertFloorNode(String building, int floor, int nodeID, String coordinates){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(NODE_BUILDING, building);
            contentValues.put(NODE_FLOOR, floor);
            contentValues.put(NODE_ID, nodeID);
            contentValues.put(NODE_COORDINATES, coordinates);
            db.insert(NODE_TABLE, null, contentValues);
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error inserting into the node table");
            return false;
        }
    }


    //This method is used to insert new nodes into the node adjacency table
    public boolean insertAdjacency(String building, int floor, int node1, int node2){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(ADJ_BUILDING, building);
            contentValues.put(ADJ_FLOOR, floor);
            contentValues.put(ADJ_NODE_ONE, node1);
            contentValues.put(ADJ_NODE_TWO, node2);
            db.insert(ADJ_TABLE, null, contentValues);
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error inserting into the adjacency table");
            return false;
        }
    }


    //This method is used to insert new buildings into the buildings table
    public boolean insertBuilding(String building, int floor, String abreviation, String image){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(BUILDING_BUILDING, building);
            contentValues.put(BUILDING_FLOOR, floor);
            contentValues.put(BUILDING_ABREVIATION, abreviation);
            contentValues.put(BUILDING_IMAGE, image);
            db.insert(BUILDING_TABLE, null, contentValues);
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error inserting into the building table");
            return false;
        }
    }


    //This method is used to update a room in the database
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
            System.out.println("There was an error updating the room table");
            return false;
        }
    }


    //This method is used to update a node in the database
    public boolean updateNode(String building, int floor, FloorNode node){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(NODE_BUILDING, building);
            contentValues.put(NODE_FLOOR, floor);
            contentValues.put(NODE_ID, node.getNodeID());
            contentValues.put(NODE_COORDINATES, node.get_coordinates());
            db.update(NODE_TABLE, contentValues, "id = ?", new String[]{String.valueOf(node.get_id())});
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error updating the node table");
            return false;
        }
    }


    //This method is used to delete a room from the database
    public boolean deleteRoom(DBRoom room){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        try{
            db.delete(TABLE, "id = ?", new String[]{String.valueOf(room.get_id())});
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error deleting from the room table");
            return false;
        }
    }


    //This method is used to delete a node from the database
    public boolean deleteNode(FloorNode node){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        try{
            db.delete(NODE_TABLE, "id = ?", new String[]{String.valueOf(node.get_id())});
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error deleting from the node table");
            return false;
        }
    }


    //This method is used to delete an adjacency from the database
    public boolean deleteAdjacency(String building, int floor, FloorNode node){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        try{
            db.delete(ADJ_TABLE, "building = ? and floor = ? and node1 = ?", new String[]{building, String.valueOf(floor), String.valueOf(node.getNodeID())});
            db.delete(ADJ_TABLE, "building = ? and floor = ? and node2 = ?", new String[]{building, String.valueOf(floor), String.valueOf(node.getNodeID())});
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error deleting from the adjacency table");
            return false;
        }
    }


    //This method is used to delete a node from the database
    public boolean deleteBuilding(String building){
        SQLiteDatabase db = this.getWritableDatabase(); //Get a reference to the database
        try{
            db.delete(BUILDING_TABLE, "building = ?", new String[]{building});
            db.close();
            return true;
        }catch(Exception e){
            db.close();
            System.out.println("There was an error deleting a building from the building tale");
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
            System.out.println("ERROR selecting rooms from rooms table in database");
            return null;
        }
    }


    public Floor getFloor(String building, int floor)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<DBRoom> rooms = new ArrayList<DBRoom>();
        try {
            Cursor cur = db.rawQuery("select * from " + TABLE + " WHERE building=? and floor=?", new String[]{building, String.valueOf(floor)});
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if(cur.getString(cur.getColumnIndex(BUILDING)).equals(building)) {
                    DBRoom room = new DBRoom();
                    room.set_id(cur.getInt(cur.getColumnIndex("id")));
                    room.set_building(cur.getString(cur.getColumnIndex(BUILDING)));
                    room.set_floor(cur.getInt(cur.getColumnIndex(FLOOR)));
                    room.set_name(cur.getString(cur.getColumnIndex(NAME)));
                    room.set_number(cur.getInt(cur.getColumnIndex(NUMBER)));
                    room.set_coordinates(cur.getString(cur.getColumnIndex(COORDINATES)));
                    rooms.add(room);
                }
                cur.moveToNext();
            }
            db.close();
            return new Floor("Floor " + floor, rooms, new FloorGraph());
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting rooms from rooms table in database");
            return null;
        }
    }


    public ArrayList<String> getFloorsInBuilding(){
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<String> buildings = new ArrayList<String>();
        try {
            Cursor cur = db.rawQuery("select * from " + BUILDING_TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast())
            {
                if(!buildings.contains(cur.getString(cur.getColumnIndex(BUILDING_BUILDING))))
                    buildings.add(cur.getString(cur.getColumnIndex(BUILDING_BUILDING)));
                cur.moveToNext();
            }
            db.close();
            return buildings;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting buildings from buildings table in database");
            return null;
        }
    }


    public ArrayList<String> getAllBuildings(){
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<String> buildings = new ArrayList<String>();
        try {
            Cursor cur = db.rawQuery("select * from " + BUILDING_TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast())
            {
                if(!buildings.contains(cur.getString(cur.getColumnIndex(BUILDING_BUILDING))))
                    buildings.add(cur.getString(cur.getColumnIndex(BUILDING_BUILDING)));
                cur.moveToNext();
            }
            db.close();
            return buildings;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting buildings from buildings table in database");
            return null;
        }
    }


}
