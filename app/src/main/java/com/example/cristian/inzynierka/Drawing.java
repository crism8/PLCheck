package com.example.cristian.inzynierka;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Cristian on 2017-09-04.
 */

public class Drawing extends View {

    Paint paint;

    public Drawing (Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(Color.rgb(220,220,220));
        for (int i = 0; i<= canvas.getHeight(); i+=10) {
            canvas.drawLine(i, 0, i,canvas.getHeight(), paint);
            canvas.drawLine(0, i,canvas.getWidth(), i, paint);
        }
    }
}

