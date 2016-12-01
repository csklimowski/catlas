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
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Scanner;

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
        //db.execSQL("create table if not exists "+NODE_TABLE+" (id integer primary key, "+NODE_BUILDING+" text, "+NODE_FLOOR+" integer, "+NODE_ID+" integer, "+NODE_COORDINATES+" text)");
        //db.execSQL("create table if not exists "+ADJ_TABLE+" (id integer primary key, "+ADJ_BUILDING+" text, "+ADJ_FLOOR+" integer, "+ADJ_NODE_ONE+" integer, "+ADJ_NODE_TWO+" integer)");
        //db.execSQL("create table if not exists "+BUILDING_TABLE+" (id integer primary key, "+BUILDING_BUILDING+" text, "+BUILDING_FLOOR+" integer, "+BUILDING_ABREVIATION+" text, "+BUILDING_IMAGE+" text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Pupulates the database with some information if the database is empty
    public boolean populate()
    {
        boolean populated = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NODE_TABLE, null, null);
        db.execSQL("create table if not exists "+NODE_TABLE+" (id integer primary key, "+NODE_BUILDING+" text, "+NODE_FLOOR+" integer, "+NODE_ID+" integer, "+NODE_COORDINATES+" text)");
        db.execSQL("create table if not exists "+ADJ_TABLE+" (id integer primary key, "+ADJ_BUILDING+" text, "+ADJ_FLOOR+" integer, "+ADJ_NODE_ONE+" integer, "+ADJ_NODE_TWO+" integer)");
        db.execSQL("create table if not exists "+BUILDING_TABLE+" (id integer primary key, "+BUILDING_BUILDING+" text, "+BUILDING_FLOOR+" integer, "+BUILDING_ABREVIATION+" text, "+BUILDING_IMAGE+" text)");
        System.out.println("There are " + getAllRooms().size() + " rooms in the database");
        if(getAllRooms().size() < 1) {
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 906", 906, "1788 710 2040 1125"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 918", 918, "1361 978 1682 1125"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 934", 934, "570 978 840 1122"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 938", 938, "570 844 840 978"));
            insertRoom(new DBRoom("Gould Simpson", 9, "GS 942", 942, "570 710 840 844"));
            populated = true;
        }
        if(getAllBuildings() == null || getAllBuildings().size() < 1)
        {
            insertBuilding("Gould Simpson", 9, "GS", "gs9.xml");
            insertBuilding("Gould Simpson", 2, "GS", "gs2.xml");
            populated = true;
        }
        if(getNodesOnFloor("Gould Simpson", 9).size() < 1) {
            insertFloorNode("Gould Simpson", 9, 1, "1914 918");
            insertFloorNode("Gould Simpson", 9, 2, "1522 1052");
            insertFloorNode("Gould Simpson", 9, 3, "705 1050");
            insertFloorNode("Gould Simpson", 9, 4, "705 911");
            insertFloorNode("Gould Simpson", 9, 5, "705 777");
            insertFloorNode("Gould Simpson", 9, 6, "2400 360");
            insertFloorNode("Gould Simpson", 9, 7, "2100 360");
            insertFloorNode("Gould Simpson", 9, 8, "2100 520");
            insertFloorNode("Gould Simpson", 9, 9, "2100 1150");
            insertFloorNode("Gould Simpson", 9, 10, "1914 1150");
            insertFloorNode("Gould Simpson", 9, 11, "1522 1150");
            insertFloorNode("Gould Simpson", 9, 12, "705 1150");
            insertFloorNode("Gould Simpson", 9, 13, "550 1150");
            insertFloorNode("Gould Simpson", 9, 14, "550 925");
            insertFloorNode("Gould Simpson", 9, 15, "550 785");
            insertFloorNode("Gould Simpson", 9, 16, "550 525");
            insertFloorNode("Gould Simpson", 9, 17, "790 525");
            insertFloorNode("Gould Simpson", 9, 18, "790 420");
            insertFloorNode("Gould Simpson", 9, 19, "1800 420");
            insertFloorNode("Gould Simpson", 9, 20, "1800 520");

            insertAdjacency("Gould Simpson", 9, 6, 7);
            insertAdjacency("Gould Simpson", 9, 7, 8);
            insertAdjacency("Gould Simpson", 9, 8, 20);
            insertAdjacency("Gould Simpson", 9, 8, 9);
            insertAdjacency("Gould Simpson", 9, 9, 10);
            insertAdjacency("Gould Simpson", 9, 10, 11);
            insertAdjacency("Gould Simpson", 9, 11, 12);
            insertAdjacency("Gould Simpson", 9, 12, 13);
            insertAdjacency("Gould Simpson", 9, 13, 14);
            insertAdjacency("Gould Simpson", 9, 14, 15);
            insertAdjacency("Gould Simpson", 9, 15, 16);
            insertAdjacency("Gould Simpson", 9, 16, 17);
            insertAdjacency("Gould Simpson", 9, 17, 18);
            insertAdjacency("Gould Simpson", 9, 18, 19);
            insertAdjacency("Gould Simpson", 9, 19, 20);
            insertAdjacency("Gould Simpson", 9, 10, 1);
            insertAdjacency("Gould Simpson", 9, 11, 2);
            insertAdjacency("Gould Simpson", 9, 12, 3);
            insertAdjacency("Gould Simpson", 9, 14, 4);
            insertAdjacency("Gould Simpson", 9, 15, 5);
            populated = true;
        }
        db.close();
        if(populated)
            System.out.println("I populated the database");
        return populated;
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
            System.out.println("ERROR inserting into the room table");
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
            System.out.println("ERROR inserting into the node table");
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
            System.out.println("ERROR inserting into the adjacency table");
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
            System.out.println("ERROR inserting into the building table");
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
            System.out.println("ERROR updating the room table");
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
            System.out.println("ERROR updating the node table");
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
            System.out.println("ERROR deleting from the room table");
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
            System.out.println("ERROR deleting from the node table");
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
            System.out.println("ERROR deleting from the adjacency table");
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
            System.out.println("ERROR deleting a building from the building tale");
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


    //Used to get a specific floor of a building
    //  Returns a Floor object, which also contains a list of DBRoom objects for every room on that floor
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
            ArrayList<FloorNode> nodes = getNodesOnFloor(building, floor);
            FloorGraph graph = new FloorGraph(nodes);

            ArrayList<Point> adjacencies = new ArrayList<Point>();
            for(FloorNode n : nodes){
                for(int i : getAdjacenciesTo(building, floor, n.getNodeID())){
                    if(!adjacencies.contains(new Point(n.getNodeID(), i)) && !adjacencies.contains(new Point(i, n.getNodeID()))) {
                        adjacencies.add(new Point(n.getNodeID(), i));
                        graph.addEdge(n, nodes.get(i-1));
                        System.out.println("Added edge for: " + n.getNodeID() + " and " + i);
                    }
                }
            }

            return new Floor("Floor " + floor, rooms, graph);
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting floor from rooms table in database");
            return null;
        }
    }


    //Used to get a list of all the nodes on a given floor of a given building
    public ArrayList<FloorNode> getNodesOnFloor(String building, int floor)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<FloorNode> nodes = new ArrayList<FloorNode>();
        try {
            Cursor cur = db.rawQuery("select * from " + NODE_TABLE + " WHERE building=? and floor=?", new String[]{building, String.valueOf(floor)});
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if(cur.getString(cur.getColumnIndex(NODE_BUILDING)).equals(building) && cur.getInt(cur.getColumnIndex(NODE_FLOOR)) == floor) {
                    String coords = cur.getString(cur.getColumnIndex(NODE_COORDINATES));

                    //Get the x and y coordinates from the coordinate string from the database
                    Scanner scan = new Scanner(coords);
                    int x = scan.nextInt();
                    int y = scan.nextInt();
                    scan.close();

                    //Create a floor node and add it to the list
                    FloorNode node = new FloorNode((float)x, (float)y, cur.getInt(cur.getColumnIndex(NODE_ID)), cur.getInt(cur.getColumnIndex("id")));
                    nodes.add(node);
                }
                cur.moveToNext();
            }
            db.close();
            return nodes;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting nodes from nodes table in database");
            return null;
        }
    }


    //Used to get a list of all the nodes on a given floor of a given building
    public ArrayList<Integer> getAdjacenciesTo(String building, int floor, int nodeID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        try {
            Cursor cur = db.rawQuery("select * from " + ADJ_TABLE + " WHERE building=? and floor=? and node1=?", new String[]{building, String.valueOf(floor), String.valueOf(nodeID)});
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if(cur.getString(cur.getColumnIndex(ADJ_BUILDING)).equals(building) && cur.getInt(cur.getColumnIndex(ADJ_FLOOR)) == floor
                        && cur.getInt(cur.getColumnIndex(ADJ_NODE_ONE)) == nodeID) {
                    //Get the adjacent node's nodeID from the table
                    int node = cur.getInt(cur.getColumnIndex(ADJ_NODE_TWO));
                    if(!nodes.contains(node))
                        nodes.add(node);
                }
                cur.moveToNext();
            }

            db.rawQuery("select * from " + ADJ_TABLE + " WHERE building=? and floor=? and node2=?", new String[]{building, String.valueOf(floor), String.valueOf(nodeID)});
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if(cur.getString(cur.getColumnIndex(ADJ_BUILDING)).equals(building) && cur.getInt(cur.getColumnIndex(ADJ_FLOOR)) == floor
                        && cur.getInt(cur.getColumnIndex(ADJ_NODE_ONE)) == nodeID) {
                    //Get the adjacent node's nodeID from the table
                    int node = cur.getInt(cur.getColumnIndex(ADJ_NODE_TWO));
                    if(!nodes.contains(node))
                        nodes.add(node);
                }
                cur.moveToNext();
            }

            db.close();
            return nodes;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting nodes from nodes table in database");
            return null;
        }
    }


    //Used to get a list of all the floors in a specific building
    //  The list is just a list of integers (or floor numbers)
    //  Call the getFloor method to get a Floor object with a list of all the rooms
    public ArrayList<Integer> getFloorsInBuilding(String building){
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        ArrayList<Integer> floors = new ArrayList<Integer>();
        try {
            Cursor cur = db.rawQuery("select * from " + BUILDING_TABLE, null);
            cur.moveToFirst();
            while (!cur.isAfterLast())
            {
                if(!floors.contains(cur.getInt(cur.getColumnIndex(BUILDING_FLOOR)))
                        && cur.getString(cur.getColumnIndex(BUILDING_BUILDING)).equals(building))
                    floors.add(cur.getInt(cur.getColumnIndex(BUILDING_FLOOR)));
                cur.moveToNext();
            }
            db.close();
            return floors;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting floors from buildings table in database");
            return null;
        }
    }


    //Used to get a list of names of all the buildings
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


    //Used to get the image name for a given floor of a given building
    public String getFloorImage(String building, int floor)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Get a reference to the database
        String image = "";
        try {
            Cursor cur = db.rawQuery("select * from " + BUILDING_TABLE + " WHERE building=? and floor=?", new String[]{building, String.valueOf(floor)});
            cur.moveToFirst();
            while (!cur.isAfterLast())
            {
                if(cur.getString(cur.getColumnIndex(BUILDING_BUILDING)).equals(building) && cur.getInt(cur.getColumnIndex(BUILDING_FLOOR)) == floor) {
                    image = cur.getString(cur.getColumnIndex(BUILDING_IMAGE));
                    break;
                }
                cur.moveToNext();
            }
            db.close();
            return image;
        }catch(Exception e){
            db.close();
            System.out.println("ERROR selecting floor image from buildings table in database");
            return null;
        }
    }

}
