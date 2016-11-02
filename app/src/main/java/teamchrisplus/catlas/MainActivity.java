package teamchrisplus.catlas;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Stack;

import teamchrisplus.model.DBRoom;
import teamchrisplus.model.Floor;
import teamchrisplus.model.FloorGraph;
import teamchrisplus.model.FloorNode;
import teamchrisplus.model.DBRoom;
import teamchrisplus.model.Room;
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
        db.populate();
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

        handleIntent(getIntent());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX() + hView.getScrollX();
        int absoluteX = (int) event.getX() + hView.getScrollX();
        int relativeX = (int) event.getX();
        int y = (int) event.getY();
        Rect currentRect;
        boolean inRoom = false;
        FloorNode selectedNode;
        Stack<FloorNode> path;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            for (DBRoom room : floor.getRooms()) {
                if (room.hasCoordinates((int) x, (int) y)) {
                    inRoom = true;
                    currentRect = room.getRoomRect();
                    hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);
                    selectRoom(x, y);
                    showPopupWindow(room);
                }
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            selectRoom(x, y);
            selectNode(x, y);
        }
        return false;
    }

    public void selectRoom(int x, int y) {
        Rect currentRect;
        boolean inRoom = false;
        for(DBRoom room : floor.getRooms()) {
            if(room.hasCoordinates(x, y)) {
                inRoom = true;
                currentRect = room.getRoomRect();
                hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);

                showPopupWindow(room);
            }
        }

        if(!inRoom) {
            hView.setRect(0, 0, 0, 0);
            hView.setDestinationNode(null);
        }
    }

    public void selectNode(int x, int y) {
        FloorNode selectedNode = floor.findNode( x, y);
        if(selectedNode != null) {
            Log.d("yo", "we found it");
            setDestinationNode(selectedNode);
        }
    }

    public void setDestinationNode(FloorNode node) {
        if(node != null) {
            destinationNode = node;
            floor.findShortestPath(sourceNode, node);
            hView.setDestinationNode(node);
        }
    }

    /*
    * Creates a PopupWindow at a specified Room, which shows that Room's information
    * and provides a Button for routing to that room. Also clears an existing window.
    *
    * Author: Miranda Motsinger
    */
    private void showPopupWindow(DBRoom room) {
        final int roomX = room.getCenterX();
        final int roomY = room.getCenterY();
        int x = room.getCenterX() - 150 - hView.getScrollX();
        int y = room.getCenterY() - 50;

        // Set up the layour and initialize the PopupWindow
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(
                inflater.inflate(R.layout.room_popup, (ViewGroup) findViewById(R.id.room_popup)),
                400,
                500,
                true
        );
        pw.setFocusable(true);

        // Set PopupWindow's text to Room's info
        ((TextView) pw.getContentView().findViewById(R.id.popup_text_view)).setText(room.getPopupInfo());

        // Add Button and listener
        Button routeButton = (Button) pw.getContentView().findViewById(R.id.route_button);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNode(roomX, roomY);
                setDestinationNode(destinationNode);
            }
        });
        Button startButton = (Button) pw.getContentView().findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceNode = floor.findNode(roomX, roomY);
            }
        });

        // Display the window @ Room's center
        pw.showAtLocation(hView, Gravity.CENTER, x, y);
        pw.update(x, y, 400, 450);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_list_menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_rooms).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_room_list:
                startActivity(new Intent(MainActivity.this, RoomDBActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Creates a PopupWindow at a specified Room, which shows that Room's information
    * and provides a Button for routing to that room. Also clears an existing window.
    *
    * Author: Miranda Motsinger
    */
    private void showPopupWindow(Room room) {
        int x = room.getCenterX();
        int y = room.getCenterY();

        // Set up the layour and initialize the PopupWindow
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(
                inflater.inflate(R.layout.room_popup, null, false),
                300,
                300,
                true
        );

        // Set PopupWindow's text to Room's info
        ((TextView) pw.getContentView().findViewById(R.id.popup_text_view)).setText(room.getPopupInfo());

        // Add Button and listener
        Button button = (Button) pw.getContentView().findViewById(R.id.route_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yeahboi", "clicked the button");
            }
        });

        // Display the window @ Room's center
        pw.showAtLocation(hView, Gravity.CENTER, x, y);
        pw.update(x - hView.getScrollX(), y, 300, 300);
    }

    /*
    * If the requested room exists, finds it on the floor and selects it.
    *
    */
    private void searchRooms(String query) {
        query = query.toLowerCase();
        for(final DBRoom room : floor.getRooms()) {
            System.out.println(room.get_name());
            if (room.get_name().toLowerCase().contains(query)) {
                Rect currentRect = room.getRoomRect();
                hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);
                hView.scrollTo(currentRect.left - 100, 0);
                selectNode(room.getCenterX(), room.getCenterY());
                showPopupWindow(room);
                break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchRooms(query);
        }
    }
}
