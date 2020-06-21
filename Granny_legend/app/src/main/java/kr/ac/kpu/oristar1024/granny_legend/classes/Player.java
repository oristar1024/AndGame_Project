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
    public float x, y;
    private float dx, dy;
    private float range;
    public ArrayList<Weapon> weapons;
    public Rect bounding_box;
    private float itemDurationTime = 5.0f;
    private float itemTime = 0.f;
    private boolean itemOn = false;
    public boolean shieldItemOn = false;
    public int weaponLevel;
    public float damageInStage;


    public Player(Resources res, float x, float y, int level){
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
        range = 200;
        damageInStage = 0;
        weaponLevel = level;
        bounding_box = new Rect(460, 460, 540, 540);


        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.character);
        weapons = new ArrayList<>();
        weapons.add(new Weapon(res, x, y, 0, range, weaponLevel));
        weapons.add(new Weapon(res, x, y, 60, range, weaponLevel));
        weapons.add(new Weapon(res, x, y, 120, range, weaponLevel));
        weapons.add(new Weapon(res, x, y, 180, range, weaponLevel));
        weapons.add(new Weapon(res, x, y, 240, range, weaponLevel));
        weapons.add(new Weapon(res, x, y, 300, range, weaponLevel));
    }

    private void updateBB(){
        bounding_box.left = (int)(x-40);
        bounding_box.right = (int)(x+40);
        bounding_box.bottom = (int)(y+40);
        bounding_box.top = (int)(y-40);
    }

    public void move(float dx, float dy){
        this.dx += dx;
        this.dy += dy;
    }

    public void rotSpeedItem(){
        if(!itemOn){
            itemOn = true;
            for(Weapon w : weapons)
                w.rotSpeed = w.rotSpeed * 2;
        }
    }

    public void shieldItem(){
        if(!itemOn){
            itemOn = true;
            shieldItemOn = true;
        }
    }

    public void rangeItem(){
        if(!itemOn){
            itemOn = true;
            range = 300;
        }
    }


    @Override
    public void update(float eTime) {
        x += dx;
        y += dy;
        dx = 0;
        dy = 0;

        if(x > 1030)
            x = 1030;
        if(x < 50)
            x = 50;
        if (y > 1700)
            y = 1700;
        if (y < 50)
            y = 50;

        updateBB();
        if(itemOn){
            itemTime += eTime;
            if(itemTime > itemDurationTime)
                itemEnd();
        }
        for(Weapon w : weapons){
            w.setLocation(x, y, range);
            w.update(eTime);
        }

    }

    private void itemEnd() {
        itemOn = false;
        itemTime = 0.f;
        for (Weapon w : weapons)
            w.rotSpeed = 360;
        shieldItemOn = false;
        range = 200;
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