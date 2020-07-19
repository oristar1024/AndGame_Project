package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Boss extends Monster {


    private float shootTime;
    private float shootDelay = 5.0f;

    Monster_Boss(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_boss);
        bounding_box = new Rect(x-150, y-150, x+150, y+150);
        type = 3;
        srcRect.bottom = 450;
    }

    @Override
    void updateBB(){
        bounding_box.left = (int)(x-150);
        bounding_box.right = (int)(x+150);
        bounding_box.bottom = (int)(y+150);
        bounding_box.top = (int)(y-150);
    }

    @Override
    public void update(float eTime) {
        delayTime += eTime;
        shootTime += eTime;
        if(shootTime > shootDelay){
            canShoot = true;
            shootTime -= shootDelay;
        }
        if(rotSpeedItemOn) {
            rotSpeedItemTime += eTime;
            if (rotSpeedItemTime > rotSpeedItemDurationTime) {
                hitDelay = hitDelay * 2;
                rotSpeedItemTime = 0;
                rotSpeedItemOn = false;
            }
        }
    }

    @Override
    public void updatedir(int screen_width, int screen_height){

    }

    @Override
    void updateFrame(){
        count++;
        if(count == 10){
            frame = (frame+1)%2;
            count = 0;
        }
        srcRect.left = frame * 350;
        srcRect.right = srcRect.left + 350;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint txtPaint = new Paint();
        txtPaint.setColor(0xFF0000CC);
        txtPaint.setTextSize(60);
        canvas.drawBitmap(bitmap, srcRect, bounding_box, null);
        canvas.drawText(" "+hp, x-80, y-40, txtPaint);
        updateFrame();

        CollisionDebugger.draw(canvas, this);
    }
}
