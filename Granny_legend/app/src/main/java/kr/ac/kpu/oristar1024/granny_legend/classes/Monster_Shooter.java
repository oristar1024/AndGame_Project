package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Shooter extends Monster {
    private float shootDelay = 5.0f;
    private float shootTime = 0;
    Monster_Shooter(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_shooter);
//        frameWidth = bitmap.getWidth() / 2;
    }

    @Override
    public void updatedir(int screen_width, int screen_height){

    }

    @Override
    public void update(float eTime) {
        shootTime += eTime;
        if(shootTime > shootDelay){
            canShoot = true;
            shootTime -= shootDelay;
        }
        delayTime += eTime;
        if(rotSpeedItemOn){
            rotSpeedItemTime += eTime;
            if(rotSpeedItemTime > rotSpeedItemDurationTime){
                hitDelay = hitDelay * 2;
                rotSpeedItemTime = 0;
                rotSpeedItemOn = false;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint txtPaint = new Paint();
        txtPaint.setColor(0xFFCC0000);
        txtPaint.setTextSize(60);
        canvas.drawBitmap(bitmap, null, bounding_box, null);
        canvas.drawText(" "+hp, x-80, y-40, txtPaint);

        CollisionDebugger.draw(canvas, this);
    }

}
