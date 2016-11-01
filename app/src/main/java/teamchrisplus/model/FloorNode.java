package teamchrisplus.model;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 10/30/2016.
 */

public class FloorNode {
    private ArrayList<FloorNode> adjacentNodes;
    private FloorNode prevNode;
    private boolean discovered;
    private Rect nodeRect;
    private float x, y;
    private double distance;

    public FloorNode(float x, float y, Rect nodeRect) {
        this.nodeRect = nodeRect;
        this.x = x;
        this.y = y;
        discovered = false;
        adjacentNodes = new ArrayList<FloorNode>();
        this.distance = Integer.MAX_VALUE;
        this.prevNode = null;
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

}
