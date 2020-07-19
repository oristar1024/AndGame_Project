package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Parent extends Monster {
    Monster_Parent(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_parent);
        frameWidth = bitmap.getWidth() / 2;
//        Log.d("Monster_Parent", "Image size = " + bitmap.getWidth() + "," + bitmap.getHeight());
        type = 2;
    }

    @Override
    public float getRadius() {
        return (float) (super.getRadius() * 0.65);
    }
}
