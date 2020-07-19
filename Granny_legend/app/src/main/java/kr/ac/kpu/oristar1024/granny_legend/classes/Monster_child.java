package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_child extends Monster {
    Monster_child(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_parent);
        frameWidth = bitmap.getWidth() / 2;
//        Log.d("Monster_child", "Image size = " + bitmap.getWidth() + "," + bitmap.getHeight());
        type = 3;
        bounding_box = new Rect(x-60, y-60, x+60, y+60);
    }

    @Override
    public void updatedir(int screen_width, int screen_height){
        if( bounding_box.left < 0) {
            dx = -dx;
            x = 61;
            updateBB();
        }
        if(bounding_box.right> screen_width) {
            dx = -dx;
            x = screen_width - 61;
            updateBB();
        }
        if(bounding_box.top < 0) {
            dy = -dy;
            y = 61;
            updateBB();
        }
        if (bounding_box.bottom > screen_height){
            dy = -dy;
            y = screen_height - 61;
            updateBB();
        }
    }

    @Override
    void updateBB(){
        bounding_box.left = (int)(x-60);
        bounding_box.right = (int)(x+60);
        bounding_box.bottom = (int)(y+60);
        bounding_box.top = (int)(y-60);
    }
}
