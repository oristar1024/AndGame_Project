package kr.ac.kpu.oristar1024.granny_legend.classes;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Player implements GameObject{
    private Bitmap bitmap;
    private float x, y;
    private float dx, dy;
    private float range;
    public ArrayList<Weapon> weapons;
    public Rect bounding_box;


    public Player(Resources res){
        x = 500;
        y = 500;
        dx = 0;
        dy = 0;
        range = 200;
        bounding_box = new Rect(460, 460, 540, 540);

        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.character);
        weapons = new ArrayList<>();
        weapons.add(new Weapon(res, x, y, 0, range));
        weapons.add(new Weapon(res, x, y, 60, range));
        weapons.add(new Weapon(res, x, y, 120, range));
        weapons.add(new Weapon(res, x, y, 180, range));
        weapons.add(new Weapon(res, x, y, 240, range));
        weapons.add(new Weapon(res, x, y, 300, range));
    }

    public void updateBB(){
        bounding_box.left = (int)(x-40);
        bounding_box.right = (int)(x+40);
        bounding_box.bottom = (int)(y+40);
        bounding_box.top = (int)(y-40);
    }

    public void move(float dx, float dy){
        this.dx += dx;
        this.dy += dy;
    }


    @Override
    public void update(float eTime) {
        x += dx;
        y += dy;
        dx = 0;
        dy = 0;
        updateBB();
        for(Weapon w : weapons){
            w.setLocation(x, y, range);
            w.update(eTime);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        Paint boxpaint = new Paint();
        canvas.drawRect(bounding_box, boxpaint);
        canvas.drawBitmap(bitmap, x-75, y-75, null);
        for(Weapon w : weapons){
            w.draw(canvas);
        }
    }

    @Override
    public boolean collisionCheck(Rect other) {
        if (bounding_box.left > other.right)
            return false;
        if (bounding_box.right < other.left)
            return false;
        if (bounding_box.top > other.bottom)
            return false;
        if (bounding_box.bottom < other.top)
            return false;
        return true;
    }
}