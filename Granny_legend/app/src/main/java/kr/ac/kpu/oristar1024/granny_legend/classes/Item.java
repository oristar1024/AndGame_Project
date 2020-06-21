package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Item implements GameObject {
    private Bitmap bitmap;
    private float dx, dy;
    private float x, y;
    private float speed;
    public int type;
    public Rect bounding_box;

    public Item(Resources res, int x, int y, float dx, float dy) {
        type = new Random().nextInt(3);
        if(type == 0)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.weapon);
        else if(type == 1)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.item_shield);
        else if(type == 2)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.item_range);
        this.x = x;
        this.y = y;
        bounding_box = new Rect(x-50, y-50, x+50, y+50);
        this.dx = dx;
        this.dy = dy;
        float dist = (float)(Math.sqrt(dx * dx + dy * dy));
        this.dx = this.dx / dist;
        this.dy = this.dy / dist;
        speed = 750;
    }

    private void updateBB(){
        bounding_box.left = (int)(x-50);
        bounding_box.right = (int)(x+50);
        bounding_box.bottom = (int)(y+50);
        bounding_box.top = (int)(y-50);
    }

    public void updatedir(int screen_width, int screen_height){
        if( bounding_box.left < 0) {
            dx = -dx;
            x = 51;
            updateBB();
        }
        if(bounding_box.right> screen_width) {
            dx = -dx;
            x = screen_width - 51;
            updateBB();
        }
        if(bounding_box.top < 0) {
            dy = -dy;
            y = 51;
            updateBB();
        }
        if (bounding_box.bottom > screen_height){
            dy = -dy;
            y = screen_height - 51;
            updateBB();
        }
    }

    @Override
    public void update(float eTime) {
        x = x + dx * speed * eTime;
        y = y + dy * speed * eTime;
        updateBB();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, bounding_box, null);
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

