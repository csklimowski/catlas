package teamchrisplus.catlas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import teamchrisplus.view.HighlightView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HighlightView hView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hView = (HighlightView) findViewById(R.id.my_highlightView);
        hView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        hView.setRect(x - 200, y - 100, x + 200, y + 100);
        return true;
    }
}
