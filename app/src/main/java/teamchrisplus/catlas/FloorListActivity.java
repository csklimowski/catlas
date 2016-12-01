package teamchrisplus.catlas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FloorListActivity extends ListActivity {

    private String buildingName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_list);

        Bundle b2 = getIntent().getExtras();
        buildingName = b2.getString("buildingName");
        populateFloorListView(buildingName);
        assignOnItemClickListener();
    }

    private void populateFloorListView(String buildingName) {
        DBManager db = new DBManager(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, db.getFloorsInBuilding(buildingName));
        setListAdapter(adapter);
    }

    private void assignOnItemClickListener() {
        ListView floorList = this.getListView();

        floorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FloorListActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("buildingName", buildingName);
                b.putInt("floorNumber", (Integer) parent.getItemAtPosition(position));
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }
}
