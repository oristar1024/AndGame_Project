package kr.ac.kpu.oristar1024.granny_legend.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.oristar1024.granny_legend.MainActivity;
import kr.ac.kpu.oristar1024.granny_legend.classes.Monster;
import kr.ac.kpu.oristar1024.granny_legend.classes.Player;
import kr.ac.kpu.oristar1024.granny_legend.classes.Weapon;

public class GameView extends View {
    private final static String TAG = MainActivity.class.getSimpleName();
    private float dx;
    private float dy;
    private float tmpx;
    private float tmpy;
    private Player player;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> trash_monsters;
    int screen_width;
    int screen_height;
    private float timeDiff;
    private float frameTime;

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
        trash_monsters = new ArrayList<>();
        monsters = new ArrayList<>();
        monsters.add(new Monster(getResources(), 300, 0, 0,1, 300));
        monsters.add(new Monster(getResources(),500, 0, 0, 1, 300));

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
        float curTime = frameTimeNanos / 1000000000.0f;
        if(frameTime == 0){
            frameTime = curTime;
            return;
        }
        timeDiff = curTime - frameTime;
        frameTime = curTime;

        player.update(timeDiff);
        for(Monster m : monsters){
            m.update(timeDiff);
            m.updatedir(screen_width, screen_height);
            if(player.collisionCheck(m.bounding_box))
                Log.d(TAG, "dead!");
            for(Weapon w : player.weapons){
                if(m.collisionCheck(w.bounding_box)){
                    m.hitByWeapon(w.damage);
                    if(m.hp <= 0.f)
                        trash_monsters.add(m);
                }
            }
        }
        monsters.removeAll(trash_monsters);
        trash_monsters.clear();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);
        for(Monster m : monsters)
            m.draw(canvas);
    }
}

