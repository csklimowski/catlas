package teamchrisplus.catlas;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BuildingSearchActivity extends ListActivity {

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
            searchBuildings(query);
        }
    }

    private void searchBuildings(String query) {
        query = query.toLowerCase();
        DBManager db = new DBManager(this);

        for(String buildingName : db.getAllBuildings()) {
            System.out.println(buildingName);
            if (buildingName.toLowerCase().contains(query)) {
                startActivity(new Intent(BuildingSearchActivity.this, MainActivity.class));
            }
        }
    }
}
