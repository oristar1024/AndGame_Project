package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.view.ViewDebug;

import kr.ac.kpu.oristar1024.granny_legend.MainActivity;
import kr.ac.kpu.oristar1024.granny_legend.R;

public class Monster_normal implements GameObject {

    private final static String TAG = MainActivity.class.getSimpleName();
    private float dx, dy;
    private float x, y;
    private float speed;
    private int hp;
    private int frame;
    private int count;
    public Rect bounding_box;
    private Rect srcRect;

    private Bitmap bitmap;

    public Monster_normal(Resources res, int x, int y, float dx, float dy, int hp){
        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(res, R.drawable.monster_normal);
        frame = 0;
        this.x = x;
        this.y = y;
        bounding_box = new Rect(x-100, y-100, x+100, y+100);
        srcRect = new Rect(0, 0, 200, 200);
        this.dx = dx;
        this.dy = dy;
        float dist = (float)(Math.sqrt(dx * dx + dy * dy));
        this.dx = this.dx / dist;
        this.dy = this.dy / dist;
        speed = 20;
        this.hp = hp;
    }

    public void updateBB(){
        bounding_box.left = (int)(x-100);
        bounding_box.right = (int)(x+100);
        bounding_box.bottom = (int)(y+100);
        bounding_box.top = (int)(y-100);
    }

    public void updateFrame(){
        count++;
        if(count == 10){
            frame = (frame+1)%2;
            count = 0;
        }
        srcRect.left = frame * 200;
        srcRect.right = srcRect.left + 200;
    }

    public void updatedir(int screen_width, int screen_height){
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

    @Override
    public void update() {
        x = x + dx * speed;
        y = y + dy * speed;
        updateBB();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint BBpaint = new Paint();
        Paint txtPaint = new Paint();
        txtPaint.setColor(255);
        canvas.drawRect(bounding_box, BBpaint);
        BBpaint.setTextSize(50);
        canvas.drawBitmap(bitmap, srcRect, bounding_box, null);
        canvas.drawText(" "+hp, x-70, y-40, BBpaint);
        updateFrame();
    }

}
