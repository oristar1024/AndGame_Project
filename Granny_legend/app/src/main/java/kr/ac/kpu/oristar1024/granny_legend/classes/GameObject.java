package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {
    public void update(float eTime);
    public void draw(Canvas canvas);
    public boolean collisionCheck(Rect other);
}
