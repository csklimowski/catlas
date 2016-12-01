package teamchrisplus.model;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 10/30/2016.
 */

public class FloorNode {
    private int _id;
    private int nodeID;
    private ArrayList<FloorNode> adjacentNodes;
    private FloorNode prevNode;
    private boolean discovered;
    private Rect nodeRect;
    private float x, y;
    private double distance;

    public FloorNode(float x, float y, Rect nodeRect) {
        this(x, y, nodeRect, -1, -1);
    }

    public FloorNode(float x, float y, int nodeID, int id){ this(x, y, new Rect((int)x-100, (int)y-100, (int)x+100, (int)y+100), nodeID, id); }

    public FloorNode(float x, float y, Rect nodeRect, int nodeID, int id){
        this.nodeRect = nodeRect;
        this.x = x;
        this.y = y;
        discovered = false;
        adjacentNodes = new ArrayList<FloorNode>();
        this.distance = Integer.MAX_VALUE;
        this.prevNode = null;
        this._id = id;
        this.nodeID = nodeID;
    }

    public boolean hasCoordinates(int x, int y) {
        if(nodeRect.contains(x, y))
            return true;
        else
            return false;
    }

    public void setDiscovered(boolean b) {
        discovered = b;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void addAdjacentNode(FloorNode node) {
        adjacentNodes.add(node);
    }

    public ArrayList<FloorNode> getAdjacent() {
        return adjacentNodes;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setPrevNode(FloorNode node) {
        this.prevNode = node;
    }

    public FloorNode getPrevNode() {
        return prevNode;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int get_id() { return _id; }

    public int getNodeID() { return nodeID; }

    public String get_coordinates() { return x + " " + y; }

}
