package kr.ac.kpu.oristar1024.granny_legend.classes;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Player implements GameObject{
    private Bitmap bitmap;
    private float x;
    private float y;

    public Player(Resources res){
        x = 500;
        y = 500;
        if(bitmap == null)
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.character);
    }

    public void move(float dx, float dy){
        x += dx;
        y += dy;
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
}