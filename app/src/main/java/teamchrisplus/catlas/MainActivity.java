package teamchrisplus.catlas;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Stack;

import teamchrisplus.model.DBRoom;
import teamchrisplus.model.Floor;
import teamchrisplus.model.FloorGraph;
import teamchrisplus.model.FloorNode;
import teamchrisplus.model.Room;
import teamchrisplus.view.HighlightView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, TabHost.OnTabChangeListener  {

    private HighlightView hView;
    private Floor floor;
    private FloorNode destinationNode;
    private FloorNode sourceNode;
    private FloorNode selectedNode;
    private ArrayList<Floor> floorList = new ArrayList<Floor>();
    private DBManager db;
    private int downX = 0;
    private int downY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hView = (HighlightView) findViewById(R.id.my_highlightView);
        hView.setOnTouchListener(this);

        TabHost host = (TabHost)findViewById(R.id.tabHost_floors);
        host.setOnTabChangedListener(this);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("2");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Floor 2");
        host.addTab(spec);

        spec = host.newTabSpec("9");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Floor 9");
        host.addTab(spec);

        db = new DBManager(this);
        ArrayList<DBRoom> rooms = db.getAllRooms();
        db.close();

        Bundle b2 = getIntent().getExtras();
        floor = db.getFloor(b2.getString("buildingName"), b2.getInt("floorNumber"));
        host.setCurrentTabByTag(b2.getInt("floorNumber") +"");

        sourceNode = floor.getFirstNode();

        TextView myTextView = (TextView) findViewById(R.id.my_textView);
        myTextView.setText(floor.getName());

        handleIntent(getIntent());
    }

    // change the current floor
    public void setFloor(int floorNum) {
        floor = db.getFloor("Gould Simpson", floorNum);
        String floorName = db.getFloorImage("Gould Simpson", floorNum);
        Resources res = getResources();
        int resID = res.getIdentifier(floorName.substring(0, floorName.length() - 4), "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID);
        ImageView img = (ImageView) findViewById(R.id.imageView_map);
        img.setImageDrawable(drawable);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX() + hView.getScrollX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = x;
            downY = y;
        }

        if(event.getAction() == MotionEvent.ACTION_UP && Math.abs(x - downX) < 10 && Math.abs(y - downY) < 10) {
            selectRoom(x, y);
        }
        return false;
    }

    public void selectRoom(int x, int y) {
        Rect currentRect;
        boolean inRoom = false;
        for(DBRoom room : floor.getRooms()) {
            selectedNode = floor.findNode(x, y);
            if(room.hasCoordinates(x, y) && selectedNode != null) {
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

    private void updateDestinationNode() {
        setDestinationNode(selectedNode);
    }

    private void updateSourceNode() {
        sourceNode = selectedNode;
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
                updateDestinationNode();
            }
        });
        Button startButton = (Button) pw.getContentView().findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSourceNode();
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
        ComponentName componentName = new ComponentName(MainActivity.this, SearchActivity.class);
        System.out.println(componentName);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_rooms).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
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
        } else if (intent.hasExtra("floor")) {
            setFloor(intent.getIntExtra("floor", 2));
            int selectedNumber = intent.getIntExtra("number", 0);
            DBRoom selectedRoom = null;
            for (DBRoom room : floor.getRooms()) {
                if (room.get_number() == selectedNumber)
                    selectedRoom = room;
            }
            if (selectedRoom != null) {
                Rect currentRect = selectedRoom.getRoomRect();
                hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);
                hView.scrollTo(currentRect.left - 100, 0);
                showPopupWindow(selectedRoom);
            }
        }
    }

    @Override
    public void onTabChanged(String tab) {
        if (db == null) {
            db = new DBManager(this);
            db.populate();
            db.close();
        }
        setFloor(Integer.parseInt(tab));
        setDestinationNode(null);
        hView.invalidate();
    }
}