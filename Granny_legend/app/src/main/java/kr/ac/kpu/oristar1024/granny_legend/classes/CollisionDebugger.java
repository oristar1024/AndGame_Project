package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CollisionDebugger {
    public static boolean showsArea = false;
    private static Paint paintForRect;
    private static Paint paintForCircle;

    public static void draw(Canvas canvas, GameObject obj) {
        if (!showsArea) return;

        if (paintForRect == null) {
            createPaint();
        }

        if (obj instanceof DistanceCollidable) {
            DistanceCollidable dcobj = (DistanceCollidable) obj;
            float x = dcobj.getLocationX();
            float y = dcobj.getLocationY();
            float r = dcobj.getRadius();
            canvas.drawCircle(x, y, r, paintForCircle);
        }
        Rect rect = null;
        if (obj instanceof Monster) {
            rect = ((Monster) obj).bounding_box;
        } else if (obj instanceof Player) {
            rect = ((Player) obj).bounding_box;
        } else if (obj instanceof Weapon) {
            rect = ((Weapon) obj).bounding_box;
        }

        if (rect != null) {
//            Log.d("Hello", "Rect = " + rect);
            canvas.drawRect(rect, paintForRect);
        }
    }

    private static void createPaint() {
        paintForRect = new Paint();
        paintForRect.setColor(0x7fff0000);
        paintForRect.setStyle(Paint.Style.FILL);
//        paint.setFlags();
        paintForCircle = new Paint();
        paintForCircle.setColor(0x7f0000ff);
        paintForCircle.setStyle(Paint.Style.FILL);
    }
}
