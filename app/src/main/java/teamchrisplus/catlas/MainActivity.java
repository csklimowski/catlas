package teamchrisplus.catlas;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import teamchrisplus.model.Floor;
import teamchrisplus.model.Room;
import teamchrisplus.view.HighlightView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HighlightView hView;
    private Floor floor;
    private ArrayList<Floor> floorList = new ArrayList<Floor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hView = (HighlightView) findViewById(R.id.my_highlightView);
        hView.setOnTouchListener(this);

        LoadFloors load = new LoadFloors();
        floor = load.getFloor("GS Floor 9");

        //floor = new Floor("Gould Simpson : Floor 9 : ", new ArrayList<Room>(), null);
        floor.addRoom(new Room("GS 906", null, new Rect(2060, 812, 2368, 1310)));
        floor.addRoom(new Room("GS 918", null, new Rect(1555, 1139, 1934, 1310)));
        floor.addRoom(new Room("GS 934", null, new Rect(600, 1139, 916, 1310)));
        floor.addRoom(new Room("GS 938", null, new Rect(600, 971, 916, 1139)));
        floor.addRoom(new Room("GS 942", null, new Rect(600, 815, 916, 971)));
        TextView myTextView = (TextView) findViewById(R.id.my_textView);
        myTextView.setText(floor.getName());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int absoluteX = (int) event.getX() + hView.getScrollX();
        int relativeX = (int) event.getX();
        int y = (int) event.getY();
        Rect currentRect;
        boolean inRoom = false;
        
        if(event.getAction() == MotionEvent.ACTION_UP) {

            for(Room room : floor.getRooms()) {
                if(room.hasCoordinates(absoluteX, y)) {
                    inRoom = true;
                    currentRect = room.getRoomRect();
                    hView.setRect(currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);

//                    Log.d("absoluteX", ""+absoluteX);
//                    Log.d("relativeX", ""+relativeX);
//                    Log.d("y", ""+y);
//                    Log.d("roomX", ""+room.getCenterX());
//                    Log.d("roomY", ""+room.getCenterY());

                    showPopupWindow(room);
                }

            }

            if(!inRoom) {
                hView.setRect(0, 0, 0, 0);
                TextView myTextView = (TextView) findViewById(R.id.my_textView);
                myTextView.setText(floor.getName());
            }
        }
        return false;
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

}
