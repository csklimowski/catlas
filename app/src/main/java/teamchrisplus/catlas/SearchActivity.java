package teamchrisplus.catlas;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import teamchrisplus.model.DBRoom;


public class SearchActivity extends ListActivity {

    private ArrayList<DBRoom> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        handleIntent(getIntent());
    }

    private void searchRooms(String query) {
        query = query.toLowerCase();
        DBManager db = new DBManager(this);
        ArrayList<DBRoom> rooms = db.getAllRooms();
        results = new ArrayList<>();

        for(final DBRoom room : rooms) {
            if (room.get_name().toLowerCase().contains(query)) {
                results.add(room);
            }
        }
        ArrayList<String> roomNames = new ArrayList<>();

        for (int i = 0; i < results.size(); i++){
            roomNames.add(results.get(i).get_name());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, roomNames);
        setListAdapter(adapter);
    }

    private void handleIntent(Intent intent) {
        System.out.println("Tried to search");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchRooms(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchRooms(query);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DBRoom selectedResult = results.get(position);
        Intent intent = new Intent();
        intent.putExtra("floor", selectedResult.get_floor());
        intent.putExtra("number", selectedResult.get_number());
        intent.setClass(SearchActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
