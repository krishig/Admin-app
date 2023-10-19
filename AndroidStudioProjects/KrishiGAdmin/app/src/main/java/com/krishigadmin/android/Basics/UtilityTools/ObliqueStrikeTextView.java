package com.krishigadmin.android.Basics.UtilityTools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.krishigadmin.android.R;


public class ObliqueStrikeTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint paint;

    public ObliqueStrikeTextView(Context context) {
        super(context);
        init(context);
    }

    public ObliqueStrikeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ObliqueStrikeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        Resources resources = context.getResources();
        //replace with your color
        paint = new Paint();
        paint.setColor(resources.getColor(R.color.dark_color));
        //replace with your desired width
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 20, getWidth(), getHeight() - 20, paint);
    }
}