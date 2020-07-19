package kr.ac.kpu.oristar1024.granny_legend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.ac.kpu.oristar1024.granny_legend.classes.CollisionDebugger;


public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    private int coins;
    private int level;
    private int openedStage;
    private MediaPlayer mainBGM;

    private int[] BUTTON_IDS = {R.id.buttonStage1, R.id.buttonStage2, R.id.buttonStage3,
                                R.id.buttonStage4, R.id.buttonStage5};
    private int[] IMAGE_RES_IDS = {R.drawable.stage1, R.drawable.stage2, R.drawable.stage3,
                                    R.drawable.stage4, R.drawable.boss_stage};

    private TextView coinText;
    private TextView levelText;
    private TextView priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        CollisionDebugger.showsArea = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBGM = MediaPlayer.create(this, R.raw.mainbgm);
        mainBGM.start();



        pref = getSharedPreferences("coin", MODE_PRIVATE);
        coins = pref.getInt("coin", 0);
        level = pref.getInt("level", 0);
        openedStage = pref.getInt("stage", 0);

        if(level < 1)
            level = 1;
        if(openedStage < 1)
            openedStage = 1;

//        openedStage = 5;

        for(int i = 0; i < 5; ++i){
            ImageButton button = findViewById(BUTTON_IDS[i]);
            if(i < openedStage) {
                button.setEnabled(true);
                button.setBackgroundResource(IMAGE_RES_IDS[i]);
            }
            else{
                button.setEnabled(false);
            }
        }

        coinText = findViewById(R.id.coinText);
        levelText = findViewById(R.id.levelText);
        priceText = findViewById(R.id.priceText);

        coinText.setText(""+coins);
        levelText.setText("[level : "+ level +"] \n Atk : "+(40+level*5));
        priceText.setText(""+(150+level*50));
    }

    @Override
    protected void onResume() {
        super.onResume();
        coins = pref.getInt("coin", 0);
        level = pref.getInt("level", 0);
        openedStage = pref.getInt("stage", 0);

        if(level < 1)
            level = 1;
        if(openedStage < 1)
            openedStage = 1;

        for(int i = 0; i < 5; ++i){
            ImageButton button = findViewById(BUTTON_IDS[i]);
            if(i < openedStage) {
                button.setEnabled(true);
                button.setBackgroundResource(IMAGE_RES_IDS[i]);
            }
            else{
                button.setEnabled(false);
            }
        }

        if(level < 1)
            level = 1;
        if(openedStage < 1)
            openedStage = 1;
        openedStage = 5;

        for(int i = 0; i < 5; ++i){
            ImageButton button = findViewById(BUTTON_IDS[i]);
            if(i < openedStage) {
                button.setEnabled(true);
                button.setBackgroundResource(IMAGE_RES_IDS[i]);
            }
            else{
                button.setEnabled(false);
            }
        }

        coinText.setText(""+coins);
        levelText.setText("[level : "+ level +"] \n Atk : "+(40+level*5));
        priceText.setText(""+(150+level*50));
    }

    public void upgradeLevel(View view) {
        if(coins >= 150+level*50){
            coins -= 150+level*50;
            level++;
            coinText.setText(""+coins);
            levelText.setText("[level : "+ level +"] \n Atk : "+(40+level*5));
            priceText.setText(""+(150+level*50));

            SharedPreferences.Editor edit = pref.edit();
            edit.clear();
            edit.putInt("coin", coins);
            edit.putInt("level", level);
            edit.putInt("stage", openedStage);
            edit.commit();
        }
    }

    public void chooseStage(View view) {
        int buttonID = view.getId();
        Log.d("Main", "chosen: " + buttonID);
        SharedPreferences.Editor edit = pref.edit();
        if(buttonID == R.id.buttonStage1){
            edit.putInt("selection", 1);
        }
        else if(buttonID == R.id.buttonStage2){
            edit.putInt("selection", 2);
        }
        else if(buttonID == R.id.buttonStage3){
            edit.putInt("selection", 3);
        }
        else if(buttonID == R.id.buttonStage4){
            edit.putInt("selection", 4);
        }
        else if(buttonID == R.id.buttonStage5){
            edit.putInt("selection", 5);
        }
        edit.commit();
        mainBGM.stop();
        startActivity(new Intent(this, StageActivity.class));
    }
}
