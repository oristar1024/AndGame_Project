package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster implements GameObject, DistanceCollidable {
    float dx;
    float dy;
    float x;
    float y;
    float speed;
    public int hp, maxHp;
    int frame;
    int count;
    public float birthTime;
    public float hitDelay = 1.f / 6;
    public float delayTime = 0.f;
    float rotSpeedItemDurationTime = 5.0f;
    float rotSpeedItemTime = 0.f;
    boolean rotSpeedItemOn = false;
    public Rect bounding_box;
    Rect srcRect;
    public int type;
    public boolean canShoot;

    Bitmap bitmap;
    int frameWidth;

    Monster(Resources res, int x, int y, float dx, float dy, int hp){
        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_normal);
            frameWidth = bitmap.getWidth() / 2;
        }
        frame = 0;
        this.x = x;
        this.y = y;
        bounding_box = new Rect(x-100, y-100, x+100, y+100);
        Log.d("Monster", "Image size = " + bitmap.getWidth() + "," + bitmap.getHeight());
        srcRect = new Rect(0, 0, 200, 200);
        this.dx = dx;
        this.dy = dy;
        float dist = (float)(Math.sqrt(dx * dx + dy * dy));
        this.dx = this.dx / dist;
        this.dy = this.dy / dist;
        speed = 400;
        this.maxHp = hp;
        this.hp = hp;
        birthTime = 0.f;
        type = 0;
        canShoot = false;
    }

    void updateBB(){
        bounding_box.left = (int)(x-100);
        bounding_box.right = (int)(x+100);
        bounding_box.bottom = (int)(y+100);
        bounding_box.top = (int)(y-100);
    }

    void updateFrame(){
        count++;
        if(count == 10){
            frame = (frame+1)%2;
            count = 0;
        }
        srcRect.left = frame * frameWidth;
        srcRect.right = srcRect.left + frameWidth;
    }

    public void updatedir(int screen_width, int screen_height){
        if(birthTime < 2.0f)
            return;
        if( bounding_box.left < 0) {
            dx = -dx;
            x = 101;
            updateBB();
        }
        if(bounding_box.right> screen_width) {
            dx = -dx;
            x = screen_width - 101;
            updateBB();
        }
        if(bounding_box.top < 0) {
            dy = -dy;
            y = 101;
            updateBB();
        }
        if (bounding_box.bottom > screen_height){
            dy = -dy;
            y = screen_height - 101;
            updateBB();
        }
    }

    public void hitByWeapon(int damage){
        if(delayTime > hitDelay) {
            hp = hp - damage;
            delayTime = 0.f;
        }
    }

    public void rotSpeedItem(){
        hitDelay = hitDelay / 2;
        rotSpeedItemOn = true;
    }

    @Override
    public void update(float eTime) {
        delayTime += eTime;
        birthTime += eTime;
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

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, bounding_box, null);
        drawLife(canvas);
        updateFrame();

        CollisionDebugger.draw(canvas, this);
    }

    private Paint lifePaint, lifeGaugeFgPaint, lifeGaugeBgPaint;
    protected float lifeOffsetY;
    protected void drawLife(Canvas canvas) {
        if (lifePaint == null) {
            lifePaint = new Paint();
            lifePaint.setColor(0xFFCC0000);
            lifePaint.setTextSize(60);
            lifePaint.setTextAlign(Paint.Align.CENTER);

            lifeOffsetY = bounding_box.height() / 4;
//            Log.d("Monster", "LifeOffset=" + lifeOffsetY + " obj:" + this);

            lifeGaugeBgPaint = new Paint();
            lifeGaugeFgPaint = new Paint();
            lifeGaugeBgPaint.setColor(0x7F000000);
            lifeGaugeFgPaint.setColor(0x7FFFFFFF);
        }
        canvas.drawText(String.valueOf(hp), x, y - lifeOffsetY, lifePaint);

        final int HEIGHT = 20, BORDER = 5;
        float unit = getRadius() * 8 / 10;
        float width = (2 * (unit - BORDER)) * hp / maxHp;
        canvas.drawRect(x - unit, y + unit, x + unit, y + unit + HEIGHT, lifeGaugeBgPaint);
        float gx = x - unit + BORDER;
        canvas.drawRect(gx, y + unit + BORDER, gx + width, y + unit + HEIGHT - BORDER, lifeGaugeFgPaint);
    }


    @Override
    public boolean collisionCheck(Rect other) {
        if(bounding_box.left > other.right)
            return false;
        if (bounding_box.right < other.left)
            return false;
        if (bounding_box.top > other.bottom)
            return false;
        if (bounding_box.bottom < other.top)
            return false;
        return true;
    }

    @Override
    public float getLocationX() {
        return x;
    }

    @Override
    public float getLocationY() {
        return y;
    }

    @Override
    public float getRadius() {
        return bounding_box.width() / 2;
    }
}
