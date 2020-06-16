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
    private ArrayList<Weapon> weapons;
    private int frame;
    private int count;
    public Rect bounding_box;
    private Rect srcRect;


    public Player(Resources res){
        x = 500;
        y = 500;
        dx = 0;
        dy = 0;
        range = 200;
        bounding_box = new Rect(400, 400, 600, 600);
        srcRect = new Rect(0, 0, 160, 160);

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
        bounding_box.left = (int)(x-100);
        bounding_box.right = (int)(x+100);
        bounding_box.bottom = (int)(y+100);
        bounding_box.top = (int)(y-100);
    }

    public void updateFrame(){
        count++;
        if(count == 15){
            frame = (frame+1)%2;
        }
        if(count == 20){
            frame = (frame+1)%2;
            count = 0;
        }
        srcRect.left = frame * 160;
        srcRect.right = srcRect.left + 160;
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
        updateBB();
        for(Weapon w : weapons){
            w.setLocation(x, y, range);
            w.update();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        Paint boxpaint = new Paint();
        canvas.drawRect(bounding_box, boxpaint);
        canvas.drawBitmap(bitmap, srcRect, bounding_box, null);
        updateFrame();
        for(Weapon w : weapons){
            w.draw(canvas);
        }
    }
}