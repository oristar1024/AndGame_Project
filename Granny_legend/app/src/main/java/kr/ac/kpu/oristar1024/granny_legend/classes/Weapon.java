package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Weapon implements GameObject {
    private float x, y;
    private float angle;
    private Bitmap bitmap;
    public Rect bounding_box;
    public int damage;
    float rotSpeed;


    Weapon(Resources res, float cx, float cy, float angle, float range, int level){
        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.weapon);
        bounding_box = new Rect((int)x-100, (int)y-50, (int)x+100, (int)y+50);
        setLocation(cx, cy, range);
        this.angle = angle;
        damage = 40+level*5;
        rotSpeed = 360;
    }

    private void setBB(){
        bounding_box.left = (int)x - 100;
        bounding_box.right = (int)x + 100;
        bounding_box.top = (int)y -50;
        bounding_box.bottom = (int)y +50;
    }
    void setLocation(float cx, float cy, float range){
        x = cx + (float) (range * Math.cos(Math.toRadians(angle)));
        y = cy + (float) (range * Math.sin(Math.toRadians(angle)));
        setBB();
    }

    @Override
    public void update(float eTime) {
        angle += rotSpeed * eTime;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, bounding_box, null);
        CollisionDebugger.draw(canvas, this);
    }

    @Override
    public boolean collisionCheck(Rect other) {
        if(bounding_box.left > other.right)
            return false;
        if (bounding_box.right < other.left)
            return false;
        if (bounding_box.top < other.bottom)
            return false;
        if (bounding_box.bottom > other.top)
            return false;
        return true;
    }
}
