package teamchrisplus.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Christopher on 10/30/2016.
 */

public class FloorGraph {
    private ArrayList<FloorNode> nodes;

    public FloorGraph(ArrayList<FloorNode> nodes) {
        this.nodes = nodes;
    }

    public FloorGraph() {
        this.nodes = new ArrayList<FloorNode>();
    }

    public ArrayList<FloorNode> getNodes() {
        return nodes;
    }

    public void addNode(FloorNode node) {
        nodes.add(node);
    }

    public void addEdge(FloorNode firstNode, FloorNode secondNode) {
        firstNode.addAdjacentNode(secondNode);
        secondNode.addAdjacentNode(firstNode);
    }

    public void clearNodeValues() {
        for(FloorNode node : nodes) {
            node.setDistance(Integer.MAX_VALUE);
            node.setPrevNode(null);
        }
    }

    public double getEdgeLength(FloorNode firstNode, FloorNode secondNode) {
        double x1 = firstNode.getX();
        double x2 = secondNode.getX();
        double y1 = firstNode.getY();
        double y2 = secondNode.getY();

        double length = Math.abs(x1 - x2);
        double height = Math.abs(y1 - y2);
        return (length*length) + (height*height);
    }

    public void initPaths(FloorNode source, FloorNode destination) {
        Queue<FloorNode> queue = new LinkedList<FloorNode>();
        FloorNode currentNode;
        double distance;

        clearNodeValues();

        source.setDistance(0);
        queue.add(source);

        while(!queue.isEmpty()) {
            currentNode = queue.remove();
            for (FloorNode node : currentNode.getAdjacent()) {
                distance = currentNode.getDistance() + getEdgeLength(currentNode, node);
                if (node.getDistance() > distance) {
                    node.setDistance(distance);
                    node.setPrevNode(currentNode);
                    queue.add(node);
                }
            }
        }
    }
}
