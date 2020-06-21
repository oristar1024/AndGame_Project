package kr.ac.kpu.oristar1024.granny_legend.view;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
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

import kr.ac.kpu.oristar1024.granny_legend.classes.Generator;
import kr.ac.kpu.oristar1024.granny_legend.classes.Item;
import kr.ac.kpu.oristar1024.granny_legend.classes.Monster;
import kr.ac.kpu.oristar1024.granny_legend.classes.Player;
import kr.ac.kpu.oristar1024.granny_legend.classes.Weapon;

public class GameView extends View {
    private final static String TAG = GameView.class.getSimpleName();
    private float dx;
    private float dy;
    private float tmpx;
    private float tmpy;
    private Player player;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> trash_monsters;
    private ArrayList<Item> items;
    private ArrayList<Item> trash_items;
    private Generator generator;
    SharedPreferences pref;

    int screen_width;
    int screen_height;
    private float frameTime;

    public GameView(Context context){
        super(context);
        initResources(context);
    }


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources(context);
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

    private void initResources(Context context) {
        tmpx = 0;
        tmpy = 0;
        dx = 0;
        dy = 0;
        WindowManager wm = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point size = new Point();
        assert wm != null;
        wm.getDefaultDisplay().getSize(size);
        screen_width = size.x;
        screen_height = size.y;

        player = new Player(getResources(), (float)screen_width/2, (float)screen_height/2);
        items = new ArrayList<>();
        trash_items = new ArrayList<>();
        trash_monsters = new ArrayList<>();
        monsters = new ArrayList<>();
        generator = new Generator(getResources(), 1);

        pref = context.getSharedPreferences("coin", Context.MODE_PRIVATE);
        player.weaponLevel = pref.getInt("level", 0);
        player.coins = pref.getInt("coin", 0);

        postFrameCallback();
    }

    @SuppressLint("ClickableViewAccessibility")
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
        float timeDiff = curTime - frameTime;
        frameTime = curTime;

        player.update(timeDiff);
        monsters.addAll(generator.genenateMonster(timeDiff));
        items.addAll(generator.generateItem(timeDiff));

        for(Item i : items){
            i.update(timeDiff);
            i.updatedir(screen_width, screen_height);
            if(player.collisionCheck(i.bounding_box)){
                if(i.type == 0){
                    player.rotSpeedItem();
                    for(Monster m : monsters)
                        m.rotSpeedItem();
                }
                else if(i.type == 1)
                    player.shieldItem();
                else if(i.type == 2)
                    player.rangeItem();
                trash_items.add(i);
            }
        }
        for(Monster m : monsters){
            m.update(timeDiff);
            m.updatedir(screen_width, screen_height);
            if(player.collisionCheck(m.bounding_box) && !player.shieldItemOn)
                Log.d(TAG, "dead!");
            for(Weapon w : player.weapons){
                if(m.collisionCheck(w.bounding_box)){
                    m.hitByWeapon(w.damage);
                    player.damageInStage += w.damage;
                    if(m.hp <= 0.f)
                        trash_monsters.add(m);
                }
            }
        }
        monsters.removeAll(trash_monsters);
        items.removeAll(trash_items);
        trash_monsters.clear();
        trash_items.clear();

        if(generator.isEnd() && monsters.isEmpty()){
            Log.d(TAG, "Clear!");
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);
        for(Item i : items)
            i.draw(canvas);
        for(Monster m : monsters)
            m.draw(canvas);
    }
}

