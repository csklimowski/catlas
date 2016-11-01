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

import teamchrisplus.model.DBRoom;
import teamchrisplus.model.Floor;
import teamchrisplus.model.FloorGraph;
import teamchrisplus.model.FloorNode;
import teamchrisplus.model.DBRoom;
import teamchrisplus.view.HighlightView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HighlightView hView;
    private Floor floor;
    private FloorNode destinationNode;
    private FloorNode sourceNode;
    private ArrayList<Floor> floorList = new ArrayList<Floor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hView = (HighlightView) findViewById(R.id.my_highlightView);
        hView.setOnTouchListener(this);

        LoadFloors load = new LoadFloors();
        //floor = load.getFloor("GS Floor 9");

        DBManager db = new DBManager(this);
        ArrayList<DBRoom> rooms = db.getAllRooms();
        db.close();

        FloorGraph graph = new FloorGraph();

        FloorNode node1 = new FloorNode(2200, 1050, new Rect(2060, 812, 2368, 1310));
        FloorNode node2 = new FloorNode(1730, 1220, new Rect(1555, 1139, 1934, 1310));
        FloorNode node3 = new FloorNode(750, 1220, new Rect(600, 1139, 916, 1310));
        FloorNode node4 = new FloorNode(750, 1050, new Rect(600, 971, 916, 1139));
        FloorNode node5 = new FloorNode(750, 880, new Rect(600, 815, 916, 971));

        FloorNode node6 = new FloorNode(2800, 380, new Rect(2700, 300, 2900, 460));
        FloorNode node7 = new FloorNode(2400, 380, new Rect(2300, 300, 2500, 460));
        FloorNode node8 = new FloorNode(2400, 560, new Rect(2300, 480, 2500, 620));
        FloorNode node9 = new FloorNode(2400, 1380, new Rect(2300, 1300, 2500, 1460));
        FloorNode node10 = new FloorNode(2200, 1380, new Rect(2100, 1300, 2300, 1460));
        FloorNode node11 = new FloorNode(1730, 1380, new Rect(1630, 1300, 1830, 1460));
        FloorNode node12 = new FloorNode(750, 1380, new Rect(650, 1300, 850, 1460));
        FloorNode node13 = new FloorNode(540, 1380, new Rect(440, 1300, 640, 1460));
        FloorNode node14 = new FloorNode(540, 1050, new Rect(440, 980, 640, 1130));
        FloorNode node15 = new FloorNode(540, 880, new Rect(440, 800, 640, 960));
        FloorNode node16 = new FloorNode(540, 570, new Rect(440, 490, 640, 650));
        FloorNode node17 = new FloorNode(870, 570, new Rect(770, 490, 970, 650));
        FloorNode node18 = new FloorNode(870, 440, new Rect(770, 360, 970, 520));
        FloorNode node19 = new FloorNode(2100, 440, new Rect(2000, 360, 2200, 520));
        FloorNode node20 = new FloorNode(2100, 560, new Rect(2000, 480, 2200, 620));

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addNode(node9);
        graph.addNode(node10);
        graph.addNode(node11);
        graph.addNode(node12);
        graph.addNode(node13);
        graph.addNode(node14);
        graph.addNode(node15);
        graph.addNode(node16);
        graph.addNode(node17);
        graph.addNode(node18);
        graph.addNode(node19);
        graph.addNode(node20);

        graph.addEdge(node6, node7);
        graph.addEdge(node7, node8);
        graph.addEdge(node8, node20);
        graph.addEdge(node8, node9);
        graph.addEdge(node9, node10);
        graph.addEdge(node10, node11);
        graph.addEdge(node11, node12);
        graph.addEdge(node12, node13);
        graph.addEdge(node13, node14);
        graph.addEdge(node14, node15);
        graph.addEdge(node15, node16);
        graph.addEdge(node16, node17);
        graph.addEdge(node17, node18);
        graph.addEdge(node18, node19);
        graph.addEdge(node19, node20);

        graph.addEdge(node10, node1);
        graph.addEdge(node11, node2);
        graph.addEdge(node12, node3);
        graph.addEdge(node14, node4);
        graph.addEdge(node15, node5);

        sourceNode = node6;


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

            for(DBRoom room : floor.getRooms()) {
                if(room.hasCoordinates((int) x, (int) y)) {
                    inRoom = true;
                    currentRect = room.getRoomRect();
                    hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);

                    TextView myTextView = (TextView) findViewById(R.id.my_textView);
                    myTextView.setText(floor.getName() +room.get_name());
                }

            }

            if(!inRoom) {
                hView.setRect(0, 0, 0, 0);

            }

            selectedNode = floor.findNode((int) x, (int) y);
            if(selectedNode != null) {
                floor.findShortestPath(sourceNode, selectedNode);
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

        switch(item.getItemId()){
            case R.id.menu_room_list:
                startActivity(new Intent(MainActivity.this, RoomDBActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
