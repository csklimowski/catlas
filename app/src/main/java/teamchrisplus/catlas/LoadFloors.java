package teamchrisplus.catlas;

import teamchrisplus.model.*;
import java.util.*;

public class LoadFloors {

    private List<Floor> floorList = new ArrayList<Floor>();

    public LoadFloors()
    {
        Floor gsFloor9 = new Floor("GS Floor 9", new ArrayList<DBRoom>(), new FloorGraph());
        floorList.add(gsFloor9);
    }

    public boolean addRoom(String floorName, DBRoom room)
    {
        floorName = floorName.toLowerCase();
        for(int i = 0; i < floorList.size(); i++)
        {
            if(floorList.get(i).getName().toLowerCase().equals(floorName))
            {
                if(!floorList.get(i).getRooms().contains(room)) {
                    floorList.get(i).getRooms().add(room);
                    return true;
                }else
                    return false;
            }
        }
        return false;
    }

    public List<Floor> getFloors()
    {
        return floorList;
    }

    public List<DBRoom> getRoomList(String floorName)
    {
        floorName = floorName.toLowerCase();
        for(int i = 0; i < floorList.size(); i++)
        {
            if(floorList.get(i).getName().toLowerCase().equals(floorName))
                return floorList.get(i).getRooms();
        }
        return null;
    }

    public Floor getFloor(String floorName)
    {
        floorName = floorName.toLowerCase();
        for(int i = 0; i < floorList.size(); i++)
        {
            if(floorList.get(i).getName().toLowerCase().equals(floorName))
                return floorList.get(i);
        }
        return null;
    }

}
