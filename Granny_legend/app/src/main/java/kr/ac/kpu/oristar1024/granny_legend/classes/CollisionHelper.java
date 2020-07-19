package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Rect;

public class CollisionHelper {
    public static boolean collides(Rect rect, DistanceCollidable obj) {
        return false;
    }

    public static boolean collides(DistanceCollidable o1, DistanceCollidable o2) {
        float dx = o1.getLocationX() - o2.getLocationX();
        float dy = o1.getLocationY() - o2.getLocationY();
        float rsum = o1.getRadius() + o2.getRadius();

        float dist_sq = dx * dx + dy + dy;
        return dist_sq < rsum * rsum;
    }
}
