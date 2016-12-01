package teamchrisplus.catlas;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BuildingSearchActivity extends ListActivity {

    private ArrayList<String> results;
    private ListView listView_allBuildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_search);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            populateBuildingListView(query);
            assignOnItemClickListener();
        }
    }

    private void populateBuildingListView(String query) {
        query = query.toLowerCase();
        DBManager db = new DBManager(this);
        results = new ArrayList<String>();

        for(String buildingName : db.getAllBuildings()) {
            if (buildingName.toLowerCase().contains(query)) {
                results.add(buildingName);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, results);
        setListAdapter(adapter);
    }

    private void assignOnItemClickListener() {
        listView_allBuildings = this.getListView();

        listView_allBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuildingSearchActivity.this, FloorListActivity.class);
                Bundle b = new Bundle();
                b.putString("buildingName", (String) parent.getItemAtPosition(position));
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }
}
