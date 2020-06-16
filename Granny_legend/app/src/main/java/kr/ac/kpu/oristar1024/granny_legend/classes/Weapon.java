package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Weapon implements GameObject {
    private float x, y;
    private float angle;
    private Bitmap bitmap;
    public Rect bounding_box;
    private int damage;
    private Rect srcRect;


    public Weapon(Resources res, float cx, float cy, float angle, float range){
        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.weapon);
        bounding_box = new Rect((int)x-100, (int)y-50, (int)x+100, (int)y+50);
        setLocation(cx, cy, range);
        this.angle = angle;
        srcRect = new Rect(0, 0, 200, 100);
        damage = 50;
    }

    public void setBB(){
        bounding_box.left = (int)x - 100;
        bounding_box.right = (int)x + 100;
        bounding_box.top = (int)y -50;
        bounding_box.bottom = (int)y +50;
    }
    public void setLocation(float cx, float cy, float range){
        x = cx + (float) (range * Math.cos(Math.toRadians(angle)));
        y = cy + (float) (range * Math.sin(Math.toRadians(angle)));
        setBB();
    }

    @Override
    public void update() {
        angle += 10;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint bbPaint = new Paint();
        canvas.drawRect(bounding_box, bbPaint);
        canvas.drawBitmap(bitmap, null, bounding_box, null);
    }
}
