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

        if(stage < 4)
            maxMonsterWave = 5;
        else if(stage == 4)
            maxMonsterWave = 1;
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
            if(monsterWaveTime > 7){
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
                monsterWaveTime -= 7;
            }
        }

        else if(stage == 2){
            if(monsterWaveTime > 2 && monsterWave == 0) {
                monsters.add(new Monster_Charge(res, 140, -100, 0, 1, 2000));
                monsters.add(new Monster_Charge(res, 940, -100, 0, 1, 2000));
                monsterWaveTime -= 2;
                monsterWave++;
            }
            if(monsterWaveTime > 7){
                if(monsterWave == 1){
                    monsters.add(new Monster_Charge(res, 340, 1900, 0, -1, 2000));
                    monsters.add(new Monster_Charge(res, 540, 1900, 0, -1, 2000));
                    monsters.add(new Monster_Charge(res, 740, 1900, 0, -1, 2000));
                    monsterWaveTime -= 2;
                }
                else if(monsterWave == 2){
                    monsters.add(new Monster_Charge(res, 140, 1900, 0, -1, 2000));
                    monsters.add(new Monster_Charge(res, 940, 1900, 0, -1, 2000));
                }
                else if(monsterWave == 3){
                    monsters.add(new Monster_Charge(res, 340, -100, 0, 1, 2000));
                    monsters.add(new Monster_Charge(res, 540, -100, 0, 1, 2000));
                    monsters.add(new Monster_Charge(res, 740, -100, 0, 1, 2000));
                    monsterWaveTime -= 4;
                }
                else if(monsterWave == 4){
                    monsters.add(new Monster_Charge(res, 140, -100, 0, 1, 2000));
                    monsters.add(new Monster_Charge(res, 940, -100, 0, 1, 2000));
                }
                else if(monsterWave == 5){
                    monsters.add(new Monster_Charge(res, 340, 1900, 0, -1, 2000));
                    monsters.add(new Monster_Charge(res, 540, 1900, 0, -1, 2000));
                    monsters.add(new Monster_Charge(res, 740, 1900, 0, -1, 2000));
                }
                monsterWave++;
                monsterWaveTime -= 7;
            }
        }

        else if(stage == 3){
            if(monsterWaveTime > 2 && monsterWave == 0) {
                monsters.add(new Monster_Parent(res, 340, -100, 0, 1, 2100));
                monsters.add(new Monster_Parent(res, 740, -100, 0, 1, 2100));
                monsterWaveTime -= 2;
                monsterWave++;
            }
            if(monsterWaveTime > 7){
                if(monsterWave == 1){
                    monsters.add(new Monster_Parent(res, -100, 600, 1, 0, 2100));
                    monsters.add(new Monster_Parent(res, -100, 1000, 1, 0, 2100));
                    monsters.add(new Monster_Parent(res, -100, 1400, 1, 0, 2100));
                    monsterWaveTime -= 2;
                }
                else if(monsterWave == 2){
                    monsters.add(new Monster_Parent(res, -100, -100, 1, 1, 2100));
                    monsters.add(new Monster_Parent(res, -100, 200, 1, 1, 2100));
                }
                else if(monsterWave == 3){
                    monsters.add(new Monster_Parent(res, 1180, -100, -1, 1, 2100));
                    monsters.add(new Monster_Parent(res, 1180, 200, -1, 1, 2100));
                    monsterWaveTime -= 4;
                }
                else if(monsterWave == 4){
                    monsters.add(new Monster_Parent(res, 1180, 1900, -1, -3, 2100));
                    monsters.add(new Monster_Parent(res, 1180, 1600, -1, -3, 2100));
                }
                else if(monsterWave == 5){
                    monsters.add(new Monster_Parent(res, 1180, 400, -1, 0, 2100));
                    monsters.add(new Monster_Parent(res, 1180, 1000, -1, 0, 2100));
                    monsters.add(new Monster_Parent(res, 1180, 1600, -1, 0, 2100));
                }
                monsterWave++;
                monsterWaveTime -= 7;
            }
        }

        else if(stage == 4){
            if(monsterWaveTime > 2 && monsterWave == 0) {
                monsters.add(new Monster_Shooter(res, 300, 300, 0, 1, 3000));
                monsters.add(new Monster_Shooter(res, 780, 300, 0, 1, 3000));
                monsters.add(new Monster_Shooter(res, 300, 1600, 0, 1, 3000));
                monsters.add(new Monster_Shooter(res, 780, 1600, 0, 1, 3000));
                monsterWaveTime -= 2;
                monsterWave++;
            }
            else if(monsterWaveTime > 15 && monsterWave == 1) {
                monsters.add(new Monster_Shooter(res, 540, 300, 1, 0, 3000));
                monsters.add(new Monster_Shooter(res, 540, 1600, 1, 0, 3000));
                monsters.add(new Monster_Shooter(res, 300, 900, 1, 0, 3000));
                monsters.add(new Monster_Shooter(res, 780, 900, 0, 1, 3000));
                monsterWave++;
            }
        }
        return monsters;
    }

    public ArrayList<Monster> generateChild(Monster m){
        monsters.clear();
        float dx = random.nextInt(10);
        float dy = random.nextInt(10);
        monsters.add(new Monster_child(res, (int)m.x, (int)m.y, dx, dy, 300));

        dx = random.nextInt(10);
        dy = random.nextInt(10);
        monsters.add(new Monster_child(res, (int)m.x, (int)m.y, dx, dy, 300));

        dx = random.nextInt(10);
        dy = random.nextInt(10);
        monsters.add(new Monster_child(res, (int)m.x, (int)m.y, dx, dy, 300));

        return monsters;
    }

    public ArrayList<Monster> generateBullet(Monster m, float x, float y){
        monsters.clear();
        float dx = x - m.x;
        float dy = y - m.y;
        monsters.add(new Monster_Bullet(res, (int)m.x, (int)m.y, dx, dy, 1));

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
