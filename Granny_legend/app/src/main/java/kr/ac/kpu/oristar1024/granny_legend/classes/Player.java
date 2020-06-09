package kr.ac.kpu.oristar1024.granny_legend.classes;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Player implements GameObject{
    private Bitmap bitmap;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float range;
    private ArrayList<Weapon> weapons;


    public Player(Resources res){
        x = 500;
        y = 500;
        dx = 0;
        dy = 0;
        range = 200;
        if(bitmap == null)
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.character);
        weapons = new ArrayList<>();
        weapons.add(new Weapon(res, x, y, 0, range));
        weapons.add(new Weapon(res, x, y, 60, range));
        weapons.add(new Weapon(res, x, y, 120, range));
        weapons.add(new Weapon(res, x, y, 180, range));
        weapons.add(new Weapon(res, x, y, 240, range));
        weapons.add(new Weapon(res, x, y, 300, range));
    }

    public void move(float dx, float dy){
        this.dx += dx;
        this.dy += dy;
    }


    @Override
    public void update() {
        x += dx;
        y += dy;
        dx = 0;
        dy = 0;
        for(Weapon w : weapons){
            w.setLocation(x, y, range);
            w.update();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y-30, null);
        for(Weapon w : weapons){
            w.draw(canvas);
        }
    }
}