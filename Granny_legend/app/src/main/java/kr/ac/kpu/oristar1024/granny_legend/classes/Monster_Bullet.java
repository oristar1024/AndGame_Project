package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Bullet extends Monster {
    Monster_Bullet(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_bullet);
        type = 1;
        bounding_box = new Rect(x-30, y-30, x+30, y+30);
        speed = 200;
    }

    @Override
    void updateBB(){
        bounding_box.left = (int)(x-30);
        bounding_box.right = (int)(x+30);
        bounding_box.bottom = (int)(y+30);
        bounding_box.top = (int)(y-30);
    }

    @Override
    public void hitByWeapon(int damage){

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, bounding_box, null);
    }
}
