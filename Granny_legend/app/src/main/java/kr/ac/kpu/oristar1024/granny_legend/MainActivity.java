package kr.ac.kpu.oristar1024.granny_legend;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.CaseMap;
import android.os.Bundle;

import kr.ac.kpu.oristar1024.granny_legend.classes.Player;
import kr.ac.kpu.oristar1024.granny_legend.view.GameView;
import kr.ac.kpu.oristar1024.granny_legend.view.TitleView;

public class MainActivity extends AppCompatActivity {
    
    private static final long GAMEVIEW_UPDATE_INTERVAL_MSEC = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  //      gameView = findViewById(R.id.gameView);
  //      postUpdate();
    }

   // private void postUpdate() {
    //    gameView.postDelayed(new Runnable() {
    //        @Override
    //        public void run() {
     //           gameView.update();
     //           gameView.invalidate();
     //           postUpdate();
     //       }
     //   }, GAMEVIEW_UPDATE_INTERVAL_MSEC);
   // }
}
