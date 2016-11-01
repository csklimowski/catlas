package teamchrisplus.catlas;

import teamchrisplus.model.DBRoom;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import java.util.ArrayList;

public class RoomDetailsActivity extends AppCompatActivity {

    private EditText editText_building;
    private EditText editText_floor;
    private EditText editText_name;
    private EditText editText_number;
    private EditText editText_coordinates;
    private Button button_save;
    private Button button_update;
    private Button button_delete;
    private DBRoom room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        editText_building = (EditText) findViewById(R.id.editText_Building);
        editText_floor = (EditText) findViewById(R.id.editText_floor);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_number = (EditText) findViewById(R.id.editText_number);
        editText_coordinates = (EditText) findViewById(R.id.editText_coordinates);
        button_save = (Button) findViewById(R.id.button_save);
        button_update = (Button) findViewById(R.id.button_update);
        button_delete = (Button) findViewById(R.id.button_delete);

        Bundle b2 = getIntent().getExtras();
        if(b2 != null)
        {
            int pos = b2.getInt("position");
            System.out.println("Position: " + pos + " was clicked");
            DBManager db = new DBManager(this);
            room = db.getAllRooms().get(pos);
            editText_building.setText(room.get_building());
            editText_floor.setText("" + room.get_floor());
            editText_name.setText(room.get_name());
            editText_number.setText("" + room.get_number());
            editText_coordinates.setText(room.get_coordinates());
            button_save.setVisibility(View.INVISIBLE);
        }else{
            button_update.setVisibility(View.INVISIBLE);
            button_delete.setVisibility(View.INVISIBLE);
        }


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoom();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoom();
            }
        });

    }

    private void deleteRoom()
    {
        DBManager db = new DBManager(this); //Reference to the database
        DBRoom r = new DBRoom(); //Create the room object for the room to be added

        //Check if all the fields have been filled out.
        if(!checkFields())
            return;

        //Set all the room properties from the fields
        r.set_id(room.get_id());
        r.set_building(editText_building.getText().toString());
        r.set_floor(Integer.parseInt(editText_floor.getText().toString()));
        r.set_name(editText_name.getText().toString());
        r.set_number(Integer.parseInt(editText_number.getText().toString()));
        r.set_coordinates(editText_coordinates.getText().toString());

        //Insert the new room into the database
        db.deleteRoom(r);
        finish();
    }

    private void updateRoom()
    {
        DBManager db = new DBManager(this); //Reference to the database
        DBRoom r = new DBRoom(); //Create the room object for the room to be added

        //Check if all the fields have been filled out.
        if(!checkFields())
            return;

        //Set all the room properties from the fields
        r.set_id(room.get_id());
        r.set_building(editText_building.getText().toString());
        r.set_floor(Integer.parseInt(editText_floor.getText().toString()));
        r.set_name(editText_name.getText().toString());
        r.set_number(Integer.parseInt(editText_number.getText().toString()));
        r.set_coordinates(editText_coordinates.getText().toString());

        //Insert the new room into the database
        db.updateRoom(r);
        finish();
    }

    private void saveRoom()
    {
        DBManager db = new DBManager(this); //Reference to the database
        DBRoom r = new DBRoom(); //Create the room object for the room to be added

        //Check if all the fields have been filled out.
        if(!checkFields())
            return;

        //Set all the room properties from the fields
        r.set_building(editText_building.getText().toString());
        r.set_floor(Integer.parseInt(editText_floor.getText().toString()));
        r.set_name(editText_name.getText().toString());
        r.set_number(Integer.parseInt(editText_number.getText().toString()));
        r.set_coordinates(editText_coordinates.getText().toString());

        //Insert the new room into the database
        db.insertRoom(r);
        finish();
    }

    private boolean checkFields()
    {
        if(editText_building.getText().length() == 0)
            return false;
        if(editText_floor.getText().length() == 0)
            return false;
        if(editText_name.getText().length() == 0)
            return false;
        if(editText_number.getText().length() == 0)
            return false;
        if(editText_coordinates.getText().length() == 0)
            return false;
        return true;
    }

}
