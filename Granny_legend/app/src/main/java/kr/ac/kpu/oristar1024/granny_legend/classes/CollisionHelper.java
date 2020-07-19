package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Rect;
//import android.util.Log;

public class CollisionHelper {
    public static boolean collides(Rect rect, DistanceCollidable obj) {
        return false;
    }

    public static boolean collides(DistanceCollidable o1, DistanceCollidable o2) {
        float dx = o1.getLocationX() - o2.getLocationX();
        float dy = o1.getLocationY() - o2.getLocationY();
        float rsum = o1.getRadius() + o2.getRadius();

        float dist_sq = dx * dx + dy * dy;
        if (dist_sq < rsum * rsum) {
//            String d1 = o1.getLocationX() + ", " + o1.getLocationY() + " <" + o1.getRadius() + "> " + o1;
//            String d2 = o2.getLocationX() + ", " + o2.getLocationY() + " <" + o2.getRadius() + "> " + o2;
//            Log.d("Collision", "rsum=" + rsum + " dsq=" + dist_sq + " o1 = " + d1 + " o2 = " + d2);
            return true;
        }

        return false;
    }
}
