package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 * Created by dubois on 27/12/2017.
 */

public class Track {

    private int[][] data;
    private final String DIGITS ="0123456789ABCDEF";
    // 0123
    // 4567
    // 89AB
    // CDEF
    private final String[] def = {
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26616665",
            "26666665",
            "26666665",
            "26666665",
            "26664665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26616665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26646665",
            "26666165",
            "26666665",
            "26666665",
            "26666665",
            "26664665",
            "26666665",
            "26646665",
            "26666645",
            "26666665",
            "26666665",
            "26666665",
            "26616665",
            "26666665",
            "26661645",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26661665",
            "26666665",
            "26666665",
            "26664665",
            "26666665",
            "26666665",
            "26666665",
            "26646665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666465",
            "26666665",
            "26666665",
            "26664665",
            "26666665",
            "26666665",
            "26661665",
            "26616665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26166665",
            "26666665",
            "26666665",
            "26666165",
            "26666665",
            "26666465",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26646665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",
            "26666665",

            };

    private final int char2hex(char c){
        return DIGITS.indexOf(c);
    }

    private SpriteSheet sprite;

    public Track(String[] data, int sprite_id){
        sprite = SpriteSheet.get(sprite_id);
        if (data == null) data = def;
        this.data = new int[data.length][data[0].length()];
        for(int i = 0; i < data.length ; ++i){
            for(int j= 0; j< data[i].length();++j){
                this.data[i][j] =char2hex(data[i].charAt(j));
            }
        }
    }

    public int get(int i, int j){
        return data[j][i];
    }

    public int getSizeY(){
        return data.length;
    }
    public int getSizeX(){
        return data[0].length;
    }

    public int getWidth(){
        return getSizeX()*sprite.w;
    }
    public int getHeight(){
        return getSizeY()*sprite.h;
    }

    public int getTileWidth(){
        return sprite.w;
    }
    public int getTileHeight(){
        return sprite.h;
    }

    public void paint(Canvas canvas){
        for(int i = 0; i < data.length ; ++i) {
            for (int j = 0; j < data[i].length; ++j) {
                sprite.paint(canvas, data[i][j], j * sprite.w, i * sprite.h);
            }
        }
    }

    public boolean IsValid(float x, float y) {
        int i, j;
        i = (int) x; j = (int) y;

        if(j < 0 || i < 0) return false;
        if(j > getSizeY()) return false;

        if (get(i,j) == 1 ||get(i,j)  == 4 ||get(i,j)  == 2 ||get(i,j)  == 5){
                return false;
        }

        return true;
    }
}
