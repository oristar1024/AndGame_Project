package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Weapon implements GameObject {
    private float x;
    private float y;
    private float angle;
    private Bitmap bitmap;


    public Weapon(Resources res, float cx, float cy, float angle, float range){
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.weapon);
        setLocation(cx, cy, range);
        this.angle = angle;
    }

    public void setLocation(float cx, float cy, float range){
        x = cx + (float) (range * Math.cos(Math.toRadians(angle)));
        y = cy + (float) (range * Math.sin(Math.toRadians(angle)));
    }

    @Override
    public void update() {
        angle += 10;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
