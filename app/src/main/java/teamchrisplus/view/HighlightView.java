package teamchrisplus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * TODO: document your custom view class.
 */

public class HighlightView extends HorizontalScrollView {

    private Paint paint = new Paint();
    private Path highlightPath = new Path();

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
        highlightPath.reset();
        highlightPath.moveTo(x1, y1);
        highlightPath.lineTo(x2, y1);
        highlightPath.lineTo(x2, y2);
        highlightPath.lineTo(x1, y2);
        highlightPath.close();
        invalidate();
    }

    public void setShape(float[][] coords) {
        highlightPath.reset();
        highlightPath.moveTo(coords[0][0], coords[0][1]);
        for (int i = 1; i < coords.length; i++) {
            highlightPath.lineTo(coords[i][0], coords[i][1]);
        }
        highlightPath.close();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#10D070"));
        canvas.drawPath(highlightPath, paint);
    }
}
