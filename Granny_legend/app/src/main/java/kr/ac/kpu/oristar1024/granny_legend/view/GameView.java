package kr.ac.kpu.oristar1024.granny_legend.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.oristar1024.granny_legend.classes.Player;

public class GameView extends View {
    private float dx;
    private float dy;
    private float tmpx;
    private float tmpy;
    private Player player;

    public GameView(Context context){
        super(context);
        initResources();
    }


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    private void initResources() {
        tmpx = 0;
        tmpy = 0;
        dx = 0;
        dy = 0;
        player = new Player(getResources());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tmpx = event.getX();
            tmpy = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            dx = event.getX() - tmpx;
            dy = event.getY() - tmpy;
            player.move(dx, dy);
            tmpx = event.getX();
            tmpy = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            tmpx = 0;
            tmpy = 0;
            dx = 0;
            dy = 0;
        }
        invalidate();
        return true;
    }

    public void update(){
        player.update();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);

    }
}

