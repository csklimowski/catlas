package teamchrisplus.catlas;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
        float x = event.getX() + hView.getScrollX();
        float y = event.getY();
        Rect currentRect;
        boolean inRoom = false;
        
        if(event.getAction() == MotionEvent.ACTION_UP) {

            for(Room room : floor.getRooms()) {
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
            }
        }
        return false;
    }

}
