package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 * Created by dubois on 27/12/2017.
 */

public class Track {

    public static final int BLOQUE = 0;
    public static final int BRANCHE = 2;
    public static final int LIBRE = 1;
    public static final int ARRIVEE = 3;
    private int[][] data;
    private final String DIGITS ="0123456789ABCDEFGH";
    // 0123
    // 4567
    // 89AB
    // CDEF
    private final String[] def = {
            "2F8E6675",
            "259H1665",
            "252F888D",
            "25250000",
            "2GHGBA00",
            "C888E500",
            "00002500",
            "0009H500",
            "00024500",
            "00026GBA",
            "0002F8E5",
            "009H5025",
            "9BH65025",
            "276159H5",
            "2666GH75",
            "26F8EF8D",
            "21GBH500",
            "CE666500",
            "9H764GBA",
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




    public int IsValid(float x, float y) {
        /* 0 = bloqué
        * 1 = sans obstacle
        * 2 = branche
        * 3 = Arrivé
        * */
        int i, j;
        i = (int) x; j = (int) y;

        if(j < 0 || i < 0) return 0;
        if(j > getSizeY()) return 0;
        int c = get(i,j);

        x = x - ((int) x);
        y = y - ((int) y);

        return mask(x,y,c);

}

    private int mask(float x, float y, int c) {
        switch (c){
            case 1 :
                if (cercle(x,y,0.5f,0.5f,0.25f)) return BLOQUE;
                break;
            case 2 :
                if(x<0.4) return BLOQUE;
                break;
            case 4 :
                if(cercle(x,y,0.5f,0.5f,0.5f)) return BLOQUE;
                break;
            case 5 :
                if(x>0.6) return BLOQUE;
                break;
            case 7 :
                if(cercle(x,y,0.5f,0.5f,0.5f)) return BRANCHE;
                break;
            case 8 :
                if(y>0.6) return BLOQUE;
                break;
            case 9 :
                if(cercle(x,y,1,1,0.6f)) return LIBRE;
                return BLOQUE;
            case 10 :
                if(cercle(x,y,0,1,0.6f)) return LIBRE;
                return BLOQUE;
            case 11 :
                if(y<0.4) return BLOQUE;
                break;
            case 12 :
                if(cercle(x,y,1,0,0.6f)) return LIBRE;
                return BLOQUE;
            case 13 :
                if(cercle(x,y,0,0,0.6f)) return LIBRE;
                return BLOQUE;
            case 14 :
                if(cercle(x,y,0,1,0.4f)) return BLOQUE;
                break;
            case 15 :
                if(cercle(x,y,1,1,0.4f)) return BLOQUE;
                break;
            case 16 :
                if(cercle(x,y,1,0,0.4f)) return BLOQUE;
                break;
            case 17 :
                if(cercle(x,y,0,0,0.4f)) return BLOQUE;
                break;
            case 18: case 19:case 20 :
                return ARRIVEE;
        }
        return LIBRE;
    }

    private boolean cercle(float x, float y,float xc,float yc, float r) {
        return (x-xc)*(x-xc)+(y-yc)*(y-yc) <= r*r;
    }
}
