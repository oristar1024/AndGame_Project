package kr.ac.kpu.oristar1024.granny_legend.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import kr.ac.kpu.oristar1024.granny_legend.MainActivity;
import kr.ac.kpu.oristar1024.granny_legend.classes.Monster_normal;
import kr.ac.kpu.oristar1024.granny_legend.classes.Player;

public class GameView extends View {
    private final static String TAG = MainActivity.class.getSimpleName();
    private float dx;
    private float dy;
    private float tmpx;
    private float tmpy;
    private Player player;
    private Monster_normal monster1;
    private Monster_normal monster2;
    int screen_width;
    int screen_height;

    public GameView(Context context){
        super(context);
        initResources();
    }


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    private void postFrameCallback(){
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                update(frameTimeNanos);
                invalidate();
                postFrameCallback();
            }
        });
    }

    private void initResources() {
        tmpx = 0;
        tmpy = 0;
        dx = 0;
        dy = 0;
        WindowManager wm = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        screen_width = size.x;
        screen_height = size.y;
        player = new Player(getResources());

        monster1 = new Monster_normal(getResources(), 500, 500, 0,1, 2000);
        monster2 = new Monster_normal(getResources(),350, 300, 1, 1, 2000);

        postFrameCallback();
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

    public void update(long frameTimeNanos){

        player.update();
        monster1.update();
        monster2.update();
        monster1.updatedir(screen_width, screen_height);
        monster2.updatedir(screen_width, screen_height);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);
        monster1.draw(canvas);
        monster2.draw(canvas);
    }
}

