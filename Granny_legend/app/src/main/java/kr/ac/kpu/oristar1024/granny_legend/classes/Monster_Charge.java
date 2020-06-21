package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_Charge extends Monster {
    private float chargeDelay;
    private float chargeTime;
    public Monster_Charge(Resources res, int x, int y, float dx, float dy, int hp) {
        super(res, x, y, dx, dy, hp);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_charge);
        chargeDelay = 5.0f;
        chargeTime = 0;
        type = 1;
    }

    @Override
    public void update(float eTime) {
        delayTime += eTime;
        birthTime += eTime;
        chargeTime += eTime;

        if(chargeTime > chargeDelay)
            speed = 600;
        if(chargeTime > chargeDelay+2){
            speed = 400;
            chargeTime -= 6;
        }

        if(rotSpeedItemOn){
            rotSpeedItemTime += eTime;
            if(rotSpeedItemTime > rotSpeedItemDurationTime){
                hitDelay = hitDelay * 2;
                rotSpeedItemTime = 0;
                rotSpeedItemOn = false;
            }
        }
        x = x + dx * speed * eTime;
        y = y + dy * speed * eTime;

        updateBB();
    }
}
