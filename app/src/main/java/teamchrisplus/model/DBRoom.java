package teamchrisplus.model;

import java.util.Scanner;
import android.graphics.Rect;

public class DBRoom {

    private int _id;
    private String _building;
    private int _floor;
    private String _name;
    private int _number;
    private String _coordinates;
    private int[][] coordinateArray;
    private Rect roomRect;

    //Empty constructor
    public DBRoom(){}

    public DBRoom(String building, int floor, String name, int number, String coordinates)
    {
        this._building = building;
        this._name = name;
        this._floor = floor;
        this._number = number;
        this._coordinates = coordinates;
        updateCoordinates();
    }

    public int[][] getCoordinateArray() { return coordinateArray; }

    public Rect getRoomRect() { return roomRect; }

    private boolean updateRect()
    {
        //Check if you can even make a rect with the current points
        if(coordinateArray.length > 2 || coordinateArray.length == 0)
            return false;

        //Create the new rectangle with the points from the coordinateArray
        roomRect = new Rect(coordinateArray[0][0], coordinateArray[0][1], coordinateArray[1][0], coordinateArray[1][1]);

        return true;
    }

    private boolean updateCoordinates()
    {
        //Make sure the string is not null
        if(_coordinates == null)
            return false;
        Scanner scan = new Scanner(_coordinates);
        String num;
        int count = 0;
        while (scan.hasNext())
        {
            scan.next();
            count++;
        }

        scan = new Scanner(_coordinates);
        coordinateArray = new int[count/2][2];
        int place = 0;

        while(scan.hasNext())
        {
            try{
                num = scan.next();
                //Parse the String of the number to an Integer and add to the coordinate array
                int x = Integer.parseInt(num);
                num = scan.next();
                int y = Integer.parseInt(num);
                coordinateArray[place][0] = x;
                coordinateArray[place][1] = y;
                place++;
            }catch(Exception e){
                System.out.println("The coordinate String contained something that wasn't a number");
                return false;
            }
        }
        updateRect();
        return true;
    }

    public boolean hasCoordinates(int x, int y) {
        if(roomRect.contains(x, y))
            return true;
        else
            return false;
    }

    public int getCenterX() {
        return roomRect.centerX();
    }

    public int getCenterY() {
        return roomRect.centerY();
    }

    /*
    * Returns the info necessary for a room's PopupWindow. Right now, this is just the name.
    */
    public String getPopupInfo() {
        return _name;
    }

    public String get_coordinates() {
        return _coordinates;
    }

    public void set_coordinates(String _coordinates) { this._coordinates = _coordinates; updateCoordinates(); }

    public int get_floor() {
        return _floor;
    }

    public void set_floor(int _floor) {
        this._floor = _floor;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_number() {
        return _number;
    }

    public void set_number(int _number) {
        this._number = _number;
    }

    public String get_building() {
        return _building;
    }

    public void set_building(String _building) {
        this._building = _building;
    }
}
