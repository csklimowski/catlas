package teamchrisplus.model;


public class DBRoom {

    private int _id;
    private String _building;
    private int _floor;
    private String _name;
    private int _number;
    private String _coordinates;

    public String get_coordinates() {
        return _coordinates;
    }

    public void set_coordinates(String _coordinates) {
        this._coordinates = _coordinates;
    }

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
