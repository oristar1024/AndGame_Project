package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Parent extends Monster {
    Monster_Parent(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_parent);
        type = 2;
    }
}
