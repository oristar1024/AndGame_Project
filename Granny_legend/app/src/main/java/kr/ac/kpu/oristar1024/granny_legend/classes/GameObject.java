package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {
    void update(float eTime);
    void draw(Canvas canvas);
    boolean collisionCheck(Rect other);
}
