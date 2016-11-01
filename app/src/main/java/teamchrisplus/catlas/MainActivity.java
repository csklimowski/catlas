package teamchrisplus.catlas;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

import teamchrisplus.model.Floor;
import teamchrisplus.model.FloorGraph;
import teamchrisplus.model.FloorNode;
import teamchrisplus.model.Room;
import teamchrisplus.view.HighlightView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HighlightView hView;
    private Floor floor;
    private FloorNode node1;
    private ArrayList<Floor> floorList = new ArrayList<Floor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hView = (HighlightView) findViewById(R.id.my_highlightView);
        hView.setOnTouchListener(this);

        LoadFloors load = new LoadFloors();
        //floor = load.getFloor("GS Floor 9");

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("GS 906", null, new Rect(2060, 812, 2368, 1310)));
        rooms.add(new Room("GS 918", null, new Rect(1555, 1139, 1934, 1310)));
        rooms.add(new Room("GS 934", null, new Rect(600, 1139, 916, 1310)));
        rooms.add(new Room("GS 938", null, new Rect(600, 971, 916, 1139)));
        rooms.add(new Room("GS 942", null, new Rect(600, 815, 916, 971)));

        FloorGraph graph = new FloorGraph();

        node1 = new FloorNode(2200, 1050, new Rect(2060, 812, 2368, 1310));

        FloorNode node2 = new FloorNode(1730, 1220, new Rect(1555, 1139, 1934, 1310));
        FloorNode node3 = new FloorNode(750, 1220, new Rect(600, 1139, 916, 1310));
        FloorNode node4 = new FloorNode(750, 1050, new Rect(600, 971, 916, 1139));
        FloorNode node5 = new FloorNode(750, 880, new Rect(600, 815, 916, 971));

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node4);
        graph.addEdge(node2, node5);
        graph.addEdge(node4, node5);


        floor = new Floor("Gould Simpson : Floor 9 : ", rooms, graph);

        TextView myTextView = (TextView) findViewById(R.id.my_textView);
        myTextView.setText(floor.getName());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX() + hView.getScrollX();
        float y = event.getY();
        Rect currentRect;
        boolean inRoom = false;
        FloorNode selectedNode;
        Stack<FloorNode> path;
        if(event.getAction() == MotionEvent.ACTION_UP) {

            /*for(Room room : floor.getRooms()) {
                if(room.hasCoordinates((int) x, (int) y)) {
                    inRoom = true;
                    currentRect = room.getRoomRect();
                    hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);

                    TextView myTextView = (TextView) findViewById(R.id.my_textView);
                    myTextView.setText(floor.getName() +room.getName());
                }

            }

            if(!inRoom) {
                hView.setRect(0, 0, 0, 0);
                TextView myTextView = (TextView) findViewById(R.id.my_textView);
                myTextView.setText(floor.getName());
            }*/

            selectedNode = floor.findNode((int) x, (int) y);
            if(selectedNode != null) {
                floor.findShortestPath(node1, selectedNode);
                hView.setDestinationNode(selectedNode);
            }


        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println("I tried to open the menu");
        switch(item.getItemId()){
            case R.id.menu_room_list:
                startActivity(new Intent(MainActivity.this, RoomDBActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
