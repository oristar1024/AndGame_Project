package kr.ac.kpu.oristar1024.granny_legend.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import kr.ac.kpu.oristar1024.granny_legend.classes.Player;

public class TitleView extends View {
    private Player player;

    int screen_width;
    int screen_height;
    private float frameTime;


    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    private void initResources() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point size = new Point();
        assert wm != null;
        wm.getDefaultDisplay().getSize(size);
        screen_width = size.x;
        screen_height = size.y;

        player = new Player(getResources(), (float)screen_width/2, (float)screen_height/2);

        postFrameCallback();
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

    public void update(long frameTimeNanos) {
        float curTime = frameTimeNanos / 1000000000.0f;
        if(frameTime == 0){
            frameTime = curTime;
            return;
        }
        float timeDiff = curTime - frameTime;
        frameTime = curTime;

        player.update(timeDiff);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        player.draw(canvas);
    }
}
