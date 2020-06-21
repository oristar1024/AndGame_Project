package kr.ac.kpu.oristar1024.granny_legend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    private int coins;
    private int level;

    private TextView coinText;
    private TextView levelText;
    private TextView priceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("coin", MODE_PRIVATE);

        coins = pref.getInt("coin", 0);
        level = pref.getInt("level", 0);

        coinText = findViewById(R.id.coinText);
        levelText = findViewById(R.id.levelText);
        priceText = findViewById(R.id.priceText);

        coinText.setText(""+coins);
        levelText.setText("[level : "+ level +"] \n Atk : "+(40+level*10));
        priceText.setText(""+(150+level*50));
    }


    public void upgradeLevel(View view) {
        if(coins >= 150+level*50){
            coins -= 150+level*50;
            level++;
            coinText.setText(""+coins);
            levelText.setText("[level : "+ level +"] \n Atk : "+(40+level*10));
            priceText.setText(""+(150+level*50));

            SharedPreferences.Editor edit = pref.edit();
            edit.clear();
            edit.putInt("coin", coins);
            edit.putInt("level", level);
            edit.commit();
        }
    }
}
