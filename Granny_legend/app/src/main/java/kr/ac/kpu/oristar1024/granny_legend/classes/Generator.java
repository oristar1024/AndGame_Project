package kr.ac.kpu.oristar1024.granny_legend.classes;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public ArrayList<Monster> monsters;
    public ArrayList<Item> items;
    private int stage;
    private float monsterWaveTime;
    private int monsterWave;
    private float itemTime;
    private float itemRegenerateTime;
    private int itemGenCount;
    private Random random;
    private Resources res;
    private String TAG = Generator.class.getSimpleName();
    private int maxMonsterWave;

    public Generator(Resources res, int stage) {
        this.stage = stage;
        monsters = new ArrayList<>();
        items = new ArrayList<>();
        monsterWaveTime = 0;
        monsterWave = 0;
        itemRegenerateTime = 15.0f;
        itemTime = 0;
        itemGenCount = 0;
        random = new Random();
        this.res = res;

        if(stage == 1)
            maxMonsterWave = 5;
    }

    public ArrayList<Monster> genenateMonster(float eTime){
        monsterWaveTime += eTime;
        monsters.clear();
        if(stage == 1){
            if(monsterWaveTime > 2 && monsterWave == 0) {
                monsters.add(new Monster(res, 140, -100, 0, 1, 2000));
                monsters.add(new Monster(res, 940, -100, 0, 1, 2000));
                monsterWaveTime -= 2;
                monsterWave++;
            }
            if(monsterWaveTime > 5){
                if(monsterWave == 1){
                    monsters.add(new Monster(res, 340, 1900, 0, -1, 2000));
                    monsters.add(new Monster(res, 540, 1900, 0, -1, 2000));
                    monsters.add(new Monster(res, 740, 1900, 0, -1, 2000));
                    monsterWaveTime -= 2;
                }
                else if(monsterWave == 2){
                    monsters.add(new Monster(res, 140, 1900, 0, -1, 2000));
                    monsters.add(new Monster(res, 940, 1900, 0, -1, 2000));
                }
                else if(monsterWave == 3){
                    monsters.add(new Monster(res, 340, -100, 0, 1, 2000));
                    monsters.add(new Monster(res, 540, -100, 0, 1, 2000));
                    monsters.add(new Monster(res, 740, -100, 0, 1, 2000));
                    monsterWaveTime -= 4;
                }
                else if(monsterWave == 4){
                    monsters.add(new Monster(res, 140, -100, 0, 1, 2000));
                    monsters.add(new Monster(res, 940, -100, 0, 1, 2000));
                }
                else if(monsterWave == 5){
                    monsters.add(new Monster(res, 340, 1900, 0, -1, 2000));
                    monsters.add(new Monster(res, 540, 1900, 0, -1, 2000));
                    monsters.add(new Monster(res, 740, 1900, 0, -1, 2000));
                }
                monsterWave++;
                monsterWaveTime -= 5;
            }
        }

        else if(stage == 2){

        }
        return monsters;
    }


    public ArrayList<Item> generateItem(float eTime){
        itemTime += eTime;
        items.clear();
        if(itemTime > itemRegenerateTime){
            int x = random.nextInt(1000) + 40;
            int y = random.nextInt(1700) + 40;
            float dx = random.nextInt(10);
            float dy = random.nextInt(10);

            if(x > 540)
                dx = -dx;
            if(y > 900)
                dy = -dy;

            items.add(new Item(res, x, y, dx, dy));
            Log.d(TAG, "generate Item");

            if(itemGenCount > 5){
                x = random.nextInt(1000) + 40;
                y = random.nextInt(1700) + 40;
                dx = random.nextInt(10);
                dy = random.nextInt(10);

                if(x > 540)
                    dx = -dx;
                if(y > 900)
                    dy = -dy;

                items.add(new Item(res, x, y, dx, dy));
                Log.d(TAG, "generate Item");
            }
            itemTime -= itemRegenerateTime;
            itemGenCount++;
        }
        return items;
    }

    public boolean isEnd(){
        return monsterWave > maxMonsterWave;
    }
}
