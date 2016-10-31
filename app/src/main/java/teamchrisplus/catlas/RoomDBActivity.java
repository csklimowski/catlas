package teamchrisplus.catlas;

import teamchrisplus.model.DBRoom;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class RoomDBActivity extends AppCompatActivity {

    private ListView listView_allRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_db);
        listView_allRooms = (ListView) findViewById(R.id.listView_allRooms);



        listView_allRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomDBActivity.this, RoomDetailsActivity.class);
                Bundle b = new Bundle();
                b.putInt("position", position);
                intent.putExtras(b);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the add room menu to the toolbar of the RoomDBActivity
        getMenuInflater().inflate(R.menu.room_db_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_new_room:
                startActivity(new Intent(RoomDBActivity.this, RoomDetailsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        DBManager db = new DBManager(this);
        ArrayList<String> roomNames = new ArrayList<String>();
        ArrayList<DBRoom> rooms = db.getAllRooms();

        //Populate the list with all the rooms from the database
        for (int i = 0; i < rooms.size(); i++){
            roomNames.add(rooms.get(i).get_name());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, roomNames);
        listView_allRooms.setAdapter(adapter);
        super.onResume();
    }
}
