package teamchrisplus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * TODO: document your custom view class.
 */

public class HighlightView extends HorizontalScrollView {

    private Paint paint = new Paint();
    private float x1, x2, y1, y2;

    public HighlightView(Context context) {
        super(context);
    }

    public HighlightView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HighlightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRect(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#10D070"));
        canvas.drawRect(x1, y1, x2, y2, paint);
    }
}
