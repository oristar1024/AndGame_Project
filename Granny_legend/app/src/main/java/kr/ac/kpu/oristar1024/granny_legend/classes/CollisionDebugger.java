package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class CollisionDebugger {
    public static boolean showsArea = false;
    private static Paint paint;

    public static void draw(Canvas canvas, GameObject obj) {
        if (!showsArea) return;

        Rect rect = null;
        if (obj instanceof Monster) {
            rect = ((Monster) obj).bounding_box;
        } else if (obj instanceof Player) {
            rect = ((Player) obj).bounding_box;
        } else if (obj instanceof Weapon) {
            rect = ((Weapon) obj).bounding_box;
        }

        if (paint == null) {
            createPaint();
        }
        if (rect != null) {
//            Log.d("Hello", "Rect = " + rect);
            canvas.drawRect(rect, paint);
        }
    }

    private static void createPaint() {
        paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setStyle(Paint.Style.FILL);
//        paint.setFlags();
    }
}
