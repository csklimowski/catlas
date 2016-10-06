package teamchrisplus.catlas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.my_scrollView);
        scrollView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            float x = event.getX();
            float y = event.getY();
            TextView myTextView = (TextView) findViewById(R.id.my_textView);
            myTextView.setText("X: " + x + " Y: " + y);
        }
        return false;
    }

}
