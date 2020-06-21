package kr.ac.kpu.oristar1024.granny_legend.view;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.oristar1024.granny_legend.R;
import kr.ac.kpu.oristar1024.granny_legend.classes.Generator;
import kr.ac.kpu.oristar1024.granny_legend.classes.Item;
import kr.ac.kpu.oristar1024.granny_legend.classes.Monster;
import kr.ac.kpu.oristar1024.granny_legend.classes.Player;
import kr.ac.kpu.oristar1024.granny_legend.classes.Weapon;

public class GameView extends View {
    private float dx;
    private float dy;
    private float tmpx;
    private float tmpy;
    private Player player;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> trash_monsters;
    private ArrayList<Monster> child_monsters;
    private ArrayList<Monster> bullets;
    private ArrayList<Item> items;
    private ArrayList<Item> trash_items;
    private Generator generator;
    private boolean running = true;
    private Bitmap result;
    private MediaPlayer stageBGM;
    private SoundPool soundPool;
    private int hitSound;
    private int itemSound;

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

        stageBGM = MediaPlayer.create(context, R.raw.stagebgm);
        stageBGM.start();

        soundPool = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
        itemSound = soundPool.load(context, R.raw.item, 1);
        hitSound = soundPool.load(context, R.raw.hit, 1);

        result = BitmapFactory.decodeResource(getResources(), R.drawable.result);

        WindowManager wm = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point size = new Point();
        assert wm != null;
        wm.getDefaultDisplay().getSize(size);
        screen_width = size.x;
        screen_height = size.y;

        pref = context.getSharedPreferences("coin", Context.MODE_PRIVATE);
        int weaponLevel = pref.getInt("level", 0);
        int stageLevel = pref.getInt("selection", 0);

        player = new Player(getResources(), (float)screen_width/2, (float)screen_height/2, weaponLevel);
        items = new ArrayList<>();
        trash_items = new ArrayList<>();
        trash_monsters = new ArrayList<>();
        child_monsters = new ArrayList<>();
        bullets = new ArrayList<>();
        monsters = new ArrayList<>();
        generator = new Generator(getResources(), stageLevel);



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
        if(running) {
            float curTime = frameTimeNanos / 1000000000.0f;
            if (frameTime == 0) {
                frameTime = curTime;
                return;
            }
            float timeDiff = curTime - frameTime;
            frameTime = curTime;

            player.update(timeDiff);
            monsters.addAll(generator.genenateMonster(timeDiff));
            items.addAll(generator.generateItem(timeDiff));

            for (Item i : items) {
                i.update(timeDiff);
                i.updatedir(screen_width, screen_height);
                if (player.collisionCheck(i.bounding_box)) {
                    soundPool.play(itemSound, 1, 1, 0, 0, 1);
                    if (i.type == 0) {
                        player.rotSpeedItem();
                        for (Monster m : monsters)
                            m.rotSpeedItem();
                    } else if (i.type == 1)
                        player.shieldItem();
                    else if (i.type == 2)
                        player.rangeItem();
                    trash_items.add(i);
                }
            }
            for (Monster m : monsters) {
                m.update(timeDiff);
                m.updatedir(screen_width, screen_height);

                if (player.collisionCheck(m.bounding_box) && !player.shieldItemOn)
                    setDead();

                if (m.canShoot && m.type == 3) {
                    bullets.addAll(generator.generateBossBullet(m, player.x, player.y));
                    m.canShoot = false;
                }
                if (m.canShoot) {
                    bullets.addAll(generator.generateBullet(m, player.x, player.y));
                    m.canShoot = false;
                }
                if (m.type == 1 && m.birthTime > 8.0f)
                    trash_monsters.add(m);

                for (Weapon w : player.weapons) {
                    if (m.collisionCheck(w.bounding_box)) {
                        if (m.type != 1 && m.delayTime > m.hitDelay) {
                            player.damageInStage += w.damage;
                            soundPool.play(hitSound, 1, 1, 0, 0, 1);
                        }
                        m.hitByWeapon(w.damage);
                        if (m.hp <= 0.f) {
                            trash_monsters.add(m);
                            if (m.type == 2)
                                child_monsters.addAll(generator.generateChild(m));
                        }
                    }
                }
            }
            monsters.removeAll(trash_monsters);
            items.removeAll(trash_items);
            monsters.addAll(child_monsters);
            monsters.addAll(bullets);
            bullets.clear();
            child_monsters.clear();
            trash_monsters.clear();
            trash_items.clear();

            if (generator.isEnd() && monsters.isEmpty()) {
                setWin();
            }
        }
    }

    private void setDead(){
        running = false;
        int coins = pref.getInt("coin", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("coin", coins + (int)(player.damageInStage / 10));
        edit.commit();
        stageBGM.stop();
    }

    private void setWin(){
        running = false;
        int stage = pref.getInt("stage", 0);
        int coins = pref.getInt("coin", 0);

        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("stage", stage+1);
        edit.putInt("coin", coins + (int)(player.damageInStage / 10));
        edit.commit();
        stageBGM.stop();
    }

    private void drawResult(Canvas canvas){
        Paint textPaint = new Paint();
        textPaint.setTextSize(80);
        textPaint.setColor(0xFFFFFFFF);
        canvas.drawBitmap(result, null, new Rect(140, 320, 940, 820), null);
        canvas.drawText(""+(int)(player.damageInStage / 10),520, 590, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);
        for(Item i : items)
            i.draw(canvas);
        for(Monster m : monsters)
            m.draw(canvas);
        if(!running)
            drawResult(canvas);
    }
}

